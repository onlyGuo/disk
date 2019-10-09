package com.aiyi.disk.disk.controller;

import com.aiyi.disk.disk.conf.NoAuth;
import com.aiyi.disk.disk.service.AlipayService;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author gsk
 * @description: 支付宝回调相关接口
 * @date 2019/09/30
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("alipay")
public class AlipayController {

    @Resource
    private AlipayService alipayService;

    /**
     * 支付宝用户扫码回调
     * @param request
     *      请求信息
     * @param response
     *      响应信息
     */
    @NoAuth
    @RequestMapping("collback")
    public void collback(HttpServletRequest request, HttpServletResponse response){
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String> parameterMap = new HashMap<>();
        while (parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            parameterMap.put(key, request.getParameter(key));
        }

        boolean signCheck = false;
        try {
            signCheck =  AlipaySignature.rsaCheckV2(parameterMap, alipayService.publicKey(), "UTF-8");
        } catch (AlipayApiException e) {
            throw new RuntimeException("验签不通过:" + e.getErrMsg(), e);
        }

        if (!signCheck){
            throw new RuntimeException("验签不通过");
        }

        // 处理业务
        System.out.println(JSON.toJSONString(parameterMap));

        response.setStatus(200);
        try (PrintWriter writer = response.getWriter();){
            writer.write("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
