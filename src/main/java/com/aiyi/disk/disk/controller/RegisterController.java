package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.disk.disk.conf.NoAuth;
import com.aiyi.disk.disk.entity.UserPO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author gsk
 * @description: 用户注册控制器
 * @date 2019/09/26
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("register")
public class RegisterController {

    @GetMapping
    @NoAuth
    public String register(){
        return "register";
    }

    @PostMapping
    @ResponseBody
    public ResultBean register(@RequestBody UserPO user){
        return ResultBean.success("注册成功");
    }

}
