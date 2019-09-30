package com.aiyi.disk.disk.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.math.BigDecimal;

/**
 * @author gsk
 * @description: 支付宝相关业务
 * @date 2019/09/30
 * @email 719348277@qq.com
 */
@Component
public class AlipayService {

    private AlipayClient client = null;

    @Value("${alipay.appid}")
    private String appId;

    @Value("${alipay.app-public-key}")
    private String appPublicKey;

    private String appPrivateKey;

    @Value("${alipay.app-private-key-path}")
    private String appPrivateKeyPath;

    @Value("${alipay.service}")
    private String service;


    /**
     * 初始化客户端
     */
    @PostConstruct
    public void init(){
        // 读取私钥
        ByteArrayOutputStream bufferOut = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        try (InputStream in = new FileInputStream(new File(appPrivateKeyPath))){
            for (int i = in.read(buffer); i > 0; i = in.read(buffer)){
                bufferOut.write(buffer, 0, i);
            }
        }catch (IOException e){
            throw new RuntimeException("读取支付宝应用私钥失败:" + e.getMessage(), e);
        }
        try {
            appPrivateKey = bufferOut.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("读取支付宝应用私钥失败:" + e.getMessage(), e);
        }

        // 实例化SDK客户端
        client = new DefaultAlipayClient(service, appId, appPrivateKey, "json", "UTF-8",
                appPublicKey, "RSA2");

    }

    /**
     * 拉取支付二维码
     * @param orderNo
     *      订单编号
     * @param amount
     *      订单金额
     * @param subject
     *      商品名称
     * @return
     */
    public AlipayTradePrecreateResponse getPayCode(String orderNo, BigDecimal amount, String subject){
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(String.format("{" +
                "    \"out_trade_no\":\"%s\"," +
                "    \"total_amount\":\"%s\"," +
                "    \"subject\":\"%s\"," +
                "    \"timeout_express\":\"90m\"}", orderNo, amount, subject));
        AlipayTradePrecreateResponse response = null;
        try {
            response = client.execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException("订单拉取失败:" + e.getMessage(), e);
        }
        return response;
    }


}
