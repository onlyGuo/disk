package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.core.util.MD5;
import com.aiyi.disk.disk.conf.NoAuth;
import com.aiyi.disk.disk.entity.OrderPO;
import com.aiyi.disk.disk.entity.ShareInfoPO;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.OrderService;
import com.aiyi.disk.disk.service.ShareInfoService;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @author gsk
 * @description: 分享相关
 * @date 2019/09/29
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("share")
public class ShareController {

    @Resource
    private ShareInfoService shareInfoService;

    @Resource
    private OrderService orderService;

    /**
     * 创建分享连接
     * @param shareInfoPO
     *      分享详情
     * @param request
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResultBean share(@RequestBody ShareInfoPO shareInfoPO, HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        shareInfoPO.check("fileKey");
        String fileKey = null;
        try {
            fileKey = URLDecoder.decode(shareInfoPO.getFileKey(), "UTF-8");
            shareInfoPO.setFileKey(fileKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (fileKey.endsWith("/")){
            throw new ValidationException("暂不支持文件夹分享");
        }

        if (fileKey.contains("/")){
            shareInfoPO.setName(fileKey.substring(fileKey.lastIndexOf("/") + 1));
        }else{
            shareInfoPO.setName(fileKey);
        }

        shareInfoPO.setAccessKey(user.getAccessKey());
        shareInfoPO.setAccessKeySecret(user.getAccessKeySecret());
        shareInfoPO.setBucketName(user.getBucket());
        shareInfoPO.setCreateTime(new Date());
        shareInfoPO.setEndPoint(user.getEndPoint());
        shareInfoPO.setUid(user.getId());
        ShareInfoPO res = shareInfoService.create(shareInfoPO);
        return ResultBean.success("分享链接创建成功").putResponseBody("link", res.getId());
    }

    @GetMapping("{shareId}")
    @NoAuth
    public String share(@PathVariable String shareId, HttpServletRequest request){
        ShareInfoPO infoPO = shareInfoService.getById(shareId);
        if (null == infoPO){
            return "noShare";
        }
        if (!StringUtils.isEmpty(infoPO.getPassword())){
            String pswd = (String)request.getSession().getAttribute("PSWD:" + infoPO.getId());
            if (!StringUtils.isEmpty(pswd)){
                if (pswd.equals(infoPO.getPassword())){
                    infoPO.setPassword(null);
                }
            }
        }
        request.setAttribute("share", infoPO);
        return "share";
    }

    @PostMapping("deleteShare/{fileId}")
    @ResponseBody
    public ResultBean deleteShare(@PathVariable String fileId, HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        shareInfoService.deleteById(fileId, user.getId());
        return ResultBean.success("已取消分享");
    }

    @PostMapping("inputPswd")
    @ResponseBody
    @NoAuth
    public ResultBean inputPassword(@RequestBody ShareInfoPO shareInfoPO, HttpServletRequest request){
        if (StringUtils.isEmpty(shareInfoPO.getPassword())){
            throw new ValidationException("请输入访问密码");
        }
        ShareInfoPO info = shareInfoService.getById(shareInfoPO.getId());
        if (!shareInfoPO.getPassword().equals(info.getPassword())){
            throw new ValidationException("访问密码不正确");
        }
        request.getSession().setAttribute("PSWD:" + shareInfoPO.getId(), shareInfoPO.getPassword());
        return ResultBean.success("验证通过，将为你在30分钟内保持访问权限");
    }

    /**
     * 下载(获得下载连接)
     * @param fileId
     * @param request
     * @return
     */
    @PostMapping("{fileId}/download")
    @ResponseBody
    @NoAuth
    public ResultBean download(@PathVariable String fileId, HttpServletRequest request){
        ShareInfoPO info = shareInfoService.getById(fileId);
        HttpSession session = request.getSession();

        // 密码校验
        if (!StringUtils.isEmpty(info.getPassword())){
            String pswd = (String)session.getAttribute("PSWD:" + fileId);
            if (StringUtils.isEmpty(pswd)){
                throw new ValidationException("访问授权已过期， 请重新输入访问密码");
            }
            if (!info.getPassword().equals(pswd)){
                throw new ValidationException("访问密码不正确");
            }
        }

        // 支付校验
        if (null != info.getAmount() && info.getAmount().doubleValue() > 0){
            String payd = (String)request.getSession().getAttribute("PAYD:" + fileId);
            if (null == payd || !payd.equals("Y")){
                OrderPO order = orderService.createOrder(info, session.getId());
                return ResultBean.error("需要付款").setResponseBody(order);
            }
        }

        OSS client = new OSSClientBuilder().build(info.getEndPoint(), info.getAccessKey(), info.getAccessKeySecret());

        // 创建限速下载的url, 有效期10m。
        Date date = new Date();
        date.setTime(date.getTime() + 60 * 1000 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(info.getBucketName(), info.getFileKey(), HttpMethod.GET);
        req.setExpiration(date);

        if (info.getSpeed() != null){
            int limitSpeed = info.getSpeed().intValue() * 1024 * 8;
            req.setTrafficLimit(limitSpeed);
        }
        String signedUrl = client.generatePresignedUrl(req).toString();
        client.shutdown();

        // 记录下载次数
        info.setDownloadCount(info.getDownloadCount() + 1);
        shareInfoService.update(info);

        return ResultBean.success("下载连接生成成功").putResponseBody("link", signedUrl);
    }

}
