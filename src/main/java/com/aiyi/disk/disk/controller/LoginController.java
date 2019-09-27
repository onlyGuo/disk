package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.disk.disk.conf.NoAuth;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author gsk
 * @description: 登录控制器
 * @date 2019/09/26
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping
    @NoAuth
    public String loginPage(){
        return "login";
    }

    @PostMapping
    @ResponseBody
    @NoAuth
    public ResultBean login(@RequestBody UserPO user, HttpServletRequest request){
        user.check("username", "password");
        UserPO userPO = userService.loginByUserName(user.getUsername(), user.getPassword());
        request.getSession().setAttribute("LOGIN_USER", userPO);
        return ResultBean.success("登录成功");
    }

    @RequestMapping("logout")
    public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request){
        session.setAttribute("LOGIN_USER", null);
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
