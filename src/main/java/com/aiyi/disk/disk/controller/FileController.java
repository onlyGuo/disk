package com.aiyi.disk.disk.controller;

import com.aiyi.core.util.thread.ThreadUtil;
import com.aiyi.disk.disk.entity.FileItem;
import com.aiyi.disk.disk.entity.UserPO;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final int maxKeys = 10;
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
            FileItem build = FileItem.newBuilder()
                    .type(1)
                    .ossKey(obj.getKey())
                    .name(obj.getKey().substring(obj.getKey().lastIndexOf("/") + 1))
                    .make(objectListing.getNextMarker())
                    .keywork(fileItem.getKeywork())
                    .dir(fileItem.getDir())
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
     * 获取文本格式的文件大小
     * @param size
     *      文件大小
     * @return
     */
    private String getFileSize2Str(double size){
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
