package com.aiyi.disk.disk.service.impl;

import com.aiyi.core.beans.Method;
import com.aiyi.core.beans.Script;
import com.aiyi.core.sql.where.C;
import com.aiyi.disk.disk.dao.OrderDao;
import com.aiyi.disk.disk.entity.OrderPO;
import com.aiyi.disk.disk.entity.ShareInfoPO;
import com.aiyi.disk.disk.service.AlipayService;
import com.aiyi.disk.disk.service.OrderService;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-08 16:52
 * @Email 719348277@qq.com
 * @Description: 订单相关业务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private AlipayService alipayService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderPO createOrder(ShareInfoPO shareInfoPO, String sessionId) {
        if (null == shareInfoPO.getAmount() || shareInfoPO.getAmount().doubleValue() <= 0){
            throw new ValidationException("免费文件不能创建订单");
        }

        // 入库
        OrderPO build = OrderPO.newBuilder()
                .uid(shareInfoPO.getUid())
                .accessKey(shareInfoPO.getAccessKey())
                .accessKeySecret(shareInfoPO.getAccessKeySecret())
                .amount(shareInfoPO.getAmount())
                .createTime(new Date())
                .endpoint(shareInfoPO.getEndPoint())
                .fileKey(shareInfoPO.getFileKey())
                .orderNo(UUID.randomUUID().toString().toUpperCase())
                .subject("文件下载[" + shareInfoPO.getName() + "]")
                .shareFileId(shareInfoPO.getId())
                .sessionId(sessionId)
                .build();
        orderDao.add(build);

        // 生成支付二维码
        AlipayTradePrecreateResponse payCode =
                alipayService.getPayCode(build.getOrderNo(), build.getAmount(), build.getSubject());

        build.setRqCodeContent(payCode.getQrCode());
        build.setAccessKeySecret(null);
        build.setAccessKey(null);
        return build;
    }

    @Override
    public List<OrderPO> listWaitOrder() {
        List<OrderPO> list = orderDao.list(orderDao.selectSql() + " WHERE (`status` IS NULL OR `status` = ?) AND createTime > ?",
                "WAIT_BUYER_PAY", new Date(System.currentTimeMillis() - 1000 * 60 * 30));
        return list;
    }

    @Override
    public void update(OrderPO orderPO) {
        orderDao.update(orderPO);
    }

    @Override
    public OrderPO getByOrderNo(String orderNo) {
        return orderDao.get(Method.where("orderNo", C.EQ, orderNo));
    }
}