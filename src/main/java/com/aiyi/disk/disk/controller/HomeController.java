package com.aiyi.disk.disk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gsk
 * @description: 主页
 * @date 2019/09/27
 * @email 719348277@qq.com
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public void index(HttpServletResponse response){
        try {
            response.sendRedirect("home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("home")
    public String home(){
        return "home/main";
    }

}
