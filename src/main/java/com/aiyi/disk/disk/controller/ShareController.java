package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.disk.disk.entity.ShareInfoPO;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.ShareInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
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

    @PostMapping
    @ResponseBody
    public ResultBean share(@RequestBody ShareInfoPO shareInfoPO, HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        shareInfoPO.check("fileKey");
        String fileKey = shareInfoPO.getFileKey();

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

}
