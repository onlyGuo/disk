package com.aiyi.disk.disk.controller;

import com.aiyi.disk.disk.conf.NoAuth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gsk
 * @description: 登录控制器
 * @date 2019/09/26
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    @NoAuth
    public String loginPage(){
        return "login";
    }


}
