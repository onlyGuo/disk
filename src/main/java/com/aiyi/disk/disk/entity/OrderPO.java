package com.aiyi.disk.disk.entity;

import com.aiyi.core.annotation.po.ID;
import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.annotation.po.TempField;
import com.aiyi.core.beans.PO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gsk
 * @description: 订单数据库实体
 * @date 2019/09/30
 * @email 719348277@qq.com
 */
@TableName(name = "`order`")
public class OrderPO extends PO {


    @ID
    private Long id;

    /**
     * 订单所属用户ID
     */
    private Long uid;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 支付（回调通知）时间
     */
    private Date payTime;

    /**
     * 0 == 支付宝（线下扫码）， 目前只支持支付宝
     */
    private int payType;

    /**
     * 付款账号
     */
    private String payAccount;

    /**
     * 文件OSSKey
     */
    private String fileKey;

    /**
     * 文件所在节点
     */
    private String endpoint;

    /**
     * OSS AccessKey
     */
    private String accessKey;

    /**
     * OSS AccessKeySecret
     */
    private String accessKeySecret;

    /**
     * 商品（文件）名称
     */
    private String subject;

    /**
     * 二维码内容
     */
    @TempField
    private String rqCodeContent;

    public OrderPO(){super();}

    private OrderPO(Builder builder) {
        setId(builder.id);
        setUid(builder.uid);
        setOrderNo(builder.orderNo);
        setStatus(builder.status);
        setAmount(builder.amount);
        setCreateTime(builder.createTime);
        setPayTime(builder.payTime);
        setPayType(builder.payType);
        setPayAccount(builder.payAccount);
        setFileKey(builder.fileKey);
        setEndpoint(builder.endpoint);
        setAccessKey(builder.accessKey);
        setAccessKeySecret(builder.accessKeySecret);
        setSubject(builder.subject);
        setRqCodeContent(builder.rqCodeContent);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getRqCodeContent() {
        return rqCodeContent;
    }

    public void setRqCodeContent(String rqCodeContent) {
        this.rqCodeContent = rqCodeContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static final class Builder {
        private Long id;
        private Long uid;
        private String orderNo;
        private String status;
        private BigDecimal amount;
        private Date createTime;
        private Date payTime;
        private int payType;
        private String payAccount;
        private String fileKey;
        private String endpoint;
        private String accessKey;
        private String accessKeySecret;
        private String subject;
        private String rqCodeContent;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder uid(Long val) {
            uid = val;
            return this;
        }

        public Builder orderNo(String val) {
            orderNo = val;
            return this;
        }

        public Builder status(String val) {
            status = val;
            return this;
        }

        public Builder amount(BigDecimal val) {
            amount = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder payTime(Date val) {
            payTime = val;
            return this;
        }

        public Builder payType(int val) {
            payType = val;
            return this;
        }

        public Builder payAccount(String val) {
            payAccount = val;
            return this;
        }

        public Builder fileKey(String val) {
            fileKey = val;
            return this;
        }

        public Builder endpoint(String val) {
            endpoint = val;
            return this;
        }

        public Builder accessKey(String val) {
            accessKey = val;
            return this;
        }

        public Builder accessKeySecret(String val) {
            accessKeySecret = val;
            return this;
        }

        public Builder subject(String val) {
            subject = val;
            return this;
        }

        public Builder rqCodeContent(String val) {
            rqCodeContent = val;
            return this;
        }

        public OrderPO build() {
            return new OrderPO(this);
        }
    }
}
