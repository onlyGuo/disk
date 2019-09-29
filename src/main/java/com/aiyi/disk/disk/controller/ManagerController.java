package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.core.util.DateUtil;
import com.aiyi.disk.disk.entity.UserPO;
import com.aliyun.oss.common.auth.ServiceSignature;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author gsk
 * @description: 相关管理控制器
 * @date 2019/09/29
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("manager")
public class ManagerController {


    @GetMapping("sign")
    @ResponseBody
    public ResultBean getSign(String encryptText, HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");

        String ecc = "{\"expiration\":\"" + DateUtil.format(System.currentTimeMillis() + DateUtil.DAY_TIME)
                + "T12:00:00.000Z\",\"conditions\":[[\"content-length-range\", 0, 1048576000]]}";
        String policyBase64 = Base64.encodeBase64String(ecc.getBytes(StandardCharsets.UTF_8));
        String ossSign = ServiceSignature.create().computeSignature(user.getAccessKeySecret(), policyBase64);
        return ResultBean.success("签名成功")
                .putResponseBody("accessId", user.getAccessKey())
                .putResponseBody("sign", ossSign)
                .putResponseBody("policyBase64", policyBase64);
    }

}
