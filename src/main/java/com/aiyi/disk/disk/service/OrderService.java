package com.aiyi.disk.disk.service;

import com.aiyi.disk.disk.entity.OrderPO;
import com.aiyi.disk.disk.entity.ShareInfoPO;

import java.util.List;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-08 16:49
 * @Email 719348277@qq.com
 * @Description: 订单相关业务类
 */
public interface OrderService {

    /**
     * 根据分享文件信息创建订单
     * @param shareInfoPO
     *      分享信息
     * @return
     */
    OrderPO createOrder(ShareInfoPO shareInfoPO);

    /**
     * 列出等待付款订单列表
     * @return
     */
    List<OrderPO> listWaitOrder();

    /**
     * 更新订单信息
     * @param orderPO
     *      订单实体
     */
    void update(OrderPO orderPO);

    /**
     * 通过订单编号获取订单详情
     * @param orderNo
     *      订单编号
     * @return
     *      订单详情
     */
    OrderPO getByOrderNo(String orderNo);

}
