package com.aiyi.disk.disk.service;

import com.aiyi.disk.disk.entity.OrderPO;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import sun.nio.ch.ThreadPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

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

    @Value("${alipay.alipay-public-pay}")
    private String appPublicKey;

    private String appPrivateKey;

    @Value("${alipay.app-private-key-path}")
    private String appPrivateKeyPath;

    @Value("${alipay.service}")
    private String service;

    @Resource
    private OrderService orderService;


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

        // 初始化支付结果轮询线程池
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("order-status-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(()-> {
            while (true){
                List<OrderPO> orderPOS = orderService.listWaitOrder();
                if (orderPOS.isEmpty()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                LinkedList<OrderPO> list = new LinkedList<>(orderPOS);

                // 初始化支付结果轮询线程池
                ThreadFactory queueFactory = new ThreadFactoryBuilder()
                        .setNameFormat("order-queue-pool-%d").build();
                ExecutorService queueThreadPool = new ThreadPoolExecutor(10, 10,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>(1024), queueFactory, new ThreadPoolExecutor.AbortPolicy());

                queueThreadPool.execute(() -> {
                    while (!list.isEmpty()){
                        OrderPO order = null;
                        try {
                            synchronized (list){
                                order = list.removeFirst();
                            }
                        }catch (Exception e){
                            break;
                        }
                        if (null == order){
                            break;
                        }
                        AlipayTradeQueryResponse orderStatus = null;
                        try {
                            orderStatus = getOrderStatus(order.getOrderNo());
                            if ("40004".equals(orderStatus.getCode())){
                                continue;
                            }
                            if ("10000".equals(orderStatus.getCode())){
                                order.setStatus(orderStatus.getTradeStatus());
                                order.setPayAccount(orderStatus.getBuyerLogonId());
                                order.setPayTime(new Date());
                                order.setPayType(0);
                            }
                        }catch (Exception e){
                            order.setStatus("TRADE_FAIL");
                        }
                        orderService.update(order);
                    }
                });
                queueThreadPool.shutdown();
                try {
                    queueThreadPool.awaitTermination(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        singleThreadPool.shutdown();


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
                "    \"notify_url\":\"%s\"," +
                "    \"timeout_express\":\"90m\"}", orderNo, amount, subject, "http://5q2k4n.natappfree.cc/alipay/collback"));
        AlipayTradePrecreateResponse response = null;
        try {
            response = client.execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException("订单拉取失败:" + e.getMessage(), e);
        }
        return response;
    }

    public AlipayTradeQueryResponse getOrderStatus(String orderNo){
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderNo + "\"}");
        AlipayTradeQueryResponse response = null;
        try {
            response = client.execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException("订单查询失败:" + e.getMessage(), e);
        }
        return response;
    }

    public String publicKey(){
        return appPublicKey;
    }

    public String privateKey(){
        return appPrivateKey;
    }

}
