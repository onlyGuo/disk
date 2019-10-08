package com.aiyi.disk.disk.service;

import com.aiyi.disk.disk.entity.OrderPO;
import com.aiyi.disk.disk.entity.ShareInfoPO;

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

}
