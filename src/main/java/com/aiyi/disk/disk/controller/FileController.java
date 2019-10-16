package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.core.util.thread.ThreadUtil;
import com.aiyi.disk.disk.entity.FileItem;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.ShareInfoService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author gsk
 * @description: 文件相关控制类
 * @date 2019/09/27
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("files")
public class FileController {

    private static final DecimalFormat DF = new DecimalFormat("#.00");

    @Resource
    private ShareInfoService shareInfoService;

    @GetMapping("list/**")
    public String fileList(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String path = requestURI.substring(requestURI.indexOf("files/list") + 10);
        try {
            request.setAttribute("pathName", URLDecoder.decode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "home/files";
    }

    /**
     * 列出文件列表
     * @param fileItem
     *      文件列表检索信息
     * @param request
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    public Map<String, Object> listFiles(@RequestBody FileItem fileItem, HttpServletRequest request){
        Map<String, Object> res = new HashMap<>();
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(user.getEndPoint(), user.getAccessKey(), user.getAccessKeySecret());

        // 设置最大个数。
        final int maxKeys = 1000;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(user.getBucket())
                .withPrefix(fileItem.getDir().substring(1))
                .withMarker(fileItem.getMake())
                .withDelimiter("/").withMaxKeys(maxKeys));

        List<FileItem> result = new ArrayList<>();

        for (String prefix: objectListing.getCommonPrefixes()){
            FileItem build = FileItem.newBuilder()
                    .dir(fileItem.getDir())
                    .keywork(fileItem.getKeywork())
                    .make(objectListing.getNextMarker())
                    .name(prefix)
                    .ossKey(prefix)
                    .size("-")
                    .type(0).build();

            String name = prefix.substring(0, prefix.length() - 1);
            int i = name.lastIndexOf("/");
            if (i != -1){
                name = name.substring(i);
            }
            if (name.startsWith("/")){
                name = name.substring(1);
            }
            build.setName(name);

            result.add(build);
        }

        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary obj : sums) {
            if (fileItem.getDir().substring(1).equals(obj.getKey())){
                continue;
            }
            FileItem build = FileItem.newBuilder()
                    .type(1)
                    .ossKey(obj.getKey())
                    .name(obj.getKey().substring(obj.getKey().lastIndexOf("/") + 1))
                    .make(objectListing.getNextMarker())
                    .keywork(fileItem.getKeywork())
                    .dir(fileItem.getDir())
                    .updateTime(obj.getLastModified())
                    .size(getFileSize2Str(obj.getSize())).build();
            result.add(build);
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        res.put("list", result);
        res.put("make", objectListing.getNextMarker());
        return res;
    }

    /**
     * 创建新文件夹
     * @param fileItem
     *      文件夹信息
     * @return
     */
    @PostMapping("list/**/createFolder")
    @ResponseBody
    public ResultBean createFolder(@RequestBody FileItem fileItem, HttpServletRequest request){
        if (null == fileItem || StringUtils.isEmpty(fileItem.getName())){
            throw new ValidationException("目录名称不能为空");
        }
        if (fileItem.getName().contains("+")){
            throw new ValidationException("目录名称中不能含有+号");
        }
        String requestURI = request.getRequestURI();
        String path = requestURI.substring(requestURI.indexOf("files/list") + 10);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        path = path.substring(1, path.lastIndexOf("/") + 1);
        path += fileItem.getName() + "/";

        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        // 创建OSSClient实例
        OSS client = new OSSClientBuilder().build(user.getEndPoint(), user.getAccessKey(), user.getAccessKeySecret());
        InputStream stream = new ByteArrayInputStream(new byte[0]);
        client.putObject(user.getBucket(), path, stream);
        client.shutdown();
        return ResultBean.success("文件夹创建成功");
    }

    @GetMapping("list/**/upload")
    public String uploadPage(HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        String requestURI = request.getRequestURI();
        String path = requestURI.substring(requestURI.indexOf("files/list") + 10);
        try {
            path = URLDecoder.decode(path, "UTF-8").substring(1);
            path = path.substring(0, path.lastIndexOf("upload"));
            request.setAttribute("pathName", path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setAttribute("uploadUrl",
                "http://" + user.getBucket() + "." + user.getEndPoint().replace("http://", ""));
        return "home/upload";
    }


    @PostMapping("delete")
    @ResponseBody
    public ResultBean delete(@RequestBody FileItem fileItem, HttpServletRequest request){
        if (fileItem.getName().startsWith(".系统文件请勿删除")){
            return ResultBean.success("文件删除成功");
        }
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        OSS client = new OSSClientBuilder().build(user.getEndPoint(), user.getAccessKey(), user.getAccessKeySecret());

        final int maxKeys = 200;
        String nextMarker = null;
        ObjectListing objectListing;

        do {
            objectListing = client.listObjects(new ListObjectsRequest(user.getBucket()).withMarker(nextMarker)
                    .withPrefix(fileItem.getName())
                    .withMaxKeys(maxKeys));
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                client.deleteObject(user.getBucket(), s.getKey());
            }
            nextMarker = objectListing.getNextMarker();

        } while (objectListing.isTruncated());

        client.deleteObject(user.getBucket(), fileItem.getName());

        shareInfoService.deleteByFileKey(fileItem.getName(), user.getBucket());
        return ResultBean.success("文件删除成功");
    }

    /**
     * 后台直接下载文件
     * @param request
     * @param response
     */
    @GetMapping("download/**")
    public void download(HttpServletRequest request, HttpServletResponse response){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        String requestURI = request.getRequestURI();
        String path = requestURI.substring(requestURI.indexOf("files/download") + 15);
        path = path.replace("+", "%2B");

        OSS client = new OSSClientBuilder().build(user.getEndPoint(), user.getAccessKey(), user.getAccessKeySecret());
        URL url = null;
        try {
            url = client.generatePresignedUrl(
                    user.getBucket(), URLDecoder.decode(path, "UTF-8"), new Date(System.currentTimeMillis() + 1000 * 60 * 60));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect(url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.shutdown();
    }

    /**
     * 批量删除文件或文件夹
     * @param ossKeyLists
     *      要删除的OSS对象列表
     * @param request
     * @return
     */
    @PostMapping("deletes")
    @ResponseBody
    public ResultBean deleteList(@RequestBody List<String> ossKeyLists, HttpServletRequest request){
        for (String key: ossKeyLists){
            FileItem build = FileItem.newBuilder().name(key).build();
            delete(build, request);
        }
        return ResultBean.success("批量删除成功");
    }

    /**
     * 创建分享连接页面
     * @param request
     * @return
     */
    @GetMapping("share/**")
    public String sharePage(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String path = requestURI.substring(requestURI.indexOf("files/share") + 12);
        path = path.replace("+", "%2B");
        request.setAttribute("fileKey", path);
        return "home/share";
    }

    /**
     * 我的分享页面
     * @param request
     * @return
     */
    @GetMapping("myshare")
    public String myShare(HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        request.setAttribute("pathName", "/");
        request.setAttribute("files", shareInfoService.list(user.getId()));
        return "home/myshare";
    }


    /**
     * 获取文本格式的文件大小
     * @param size
     *      文件大小
     * @return
     */
    public static String getFileSize2Str(double size){
        String[] dws = {"b", "k", "M", "G"};
        String dw = null;
        for (int i = 0; i < dws.length; i ++){
            dw = dws[i];
            if (size < 1024){
                break;
            }
            size = size / 1024D;
        }
        String s = null;
        if (size % 1 == 0){
            s = new BigDecimal(size).toString();
        }else{
            s = DF.format(size);
        }
        return s + " " + dw;
    }

}
