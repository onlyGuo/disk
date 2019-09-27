package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.disk.disk.conf.NoAuth;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.UserService;
import com.aliyun.oss.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.io.File;

/**
 * @author gsk
 * @description: 用户注册控制器
 * @date 2019/09/26
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("register")
public class RegisterController {

    @Resource
    private UserService userService;

    @GetMapping
    @NoAuth
    public String register(){
        return "register";
    }

    @PostMapping
    @ResponseBody
    @NoAuth
    public ResultBean register(@RequestBody UserPO user){
        user.check("username", "password", "email", "accessKey", "accessKeySecret", "bucket", "endPoint");
        if (!user.getEndPoint().startsWith("http://")){
            throw new IllegalArgumentException("请填写正确的OSS外网节点");
        }
        user = userService.createUser(user);
        return ResultBean.success("注册成功").setResponseBody(user);
    }

}
