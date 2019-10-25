package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.disk.disk.conf.NoAuth;
import com.aiyi.disk.disk.dao.ShareInfoDao;
import com.aiyi.disk.disk.entity.OrderPO;
import com.aiyi.disk.disk.entity.ShareInfoPO;
import com.aiyi.disk.disk.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-09 14:13
 * @Email 719348277@qq.com
 * @Description: 订单相关接口控制器
 */
@Controller
@RequestMapping("orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private ShareInfoDao shareInfoDao;

    /**
     * 获取订单详情
     * @param orderNo
     *      订单编号
     * @return
     */
    @NoAuth
    @GetMapping("{orderNo}/{fileId}")
    @ResponseBody
    public ResultBean getOrderInfo(@PathVariable String orderNo, @PathVariable String fileId, HttpServletRequest request){
        OrderPO order = orderService.getByOrderNo(orderNo);
        ShareInfoPO shareInfoPO = shareInfoDao.get(fileId);
        if (null == shareInfoPO){
            throw new ValidationException("用户已取消分享您要下载的文件");
        }
        if (shareInfoPO.getFileKey().equals(order.getFileKey())
                && shareInfoPO.getEndPoint().equals(order.getEndpoint())){
            // 当订单支付成功, 且订单的sessionId与当前访问的session一致(同一人), 且订单中的分享ID与当前要下载的文件一致时,添加付款编辑
            if ("TRADE_SUCCESS".equals(order.getStatus()) && request.getSession().getId().equals(order.getSessionId())){
                request.getSession().setAttribute("PAYD:" + order.getShareFileId(), "Y");
            }
        }
        order.setAccessKey(null);
        order.setAccessKeySecret(null);
        return ResultBean.success("订单信息获取成功").setResponseBody(order);
    }

}