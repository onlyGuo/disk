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
            request.getSession().setAttribute("PAYD:" + fileId, "Y");
        }
        return ResultBean.success("订单信息获取成功").setResponseBody(order);
    }

}