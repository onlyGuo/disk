package com.aiyi.disk.disk.controller;

import com.aiyi.disk.disk.conf.NoAuth;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author gsk
 * @description: 支付宝回调相关接口
 * @date 2019/09/30
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("alipay")
public class AlipayController {

    /**
     * 支付宝用户扫码回调
     * @param request
     *      请求信息
     * @param response
     *      响应信息
     */
    @NoAuth
    @RequestMapping("collback ")
    public void collback(HttpServletRequest request, HttpServletResponse response){
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(JSON.toJSONString(parameterMap));
        response.setStatus(200);
    }

}
