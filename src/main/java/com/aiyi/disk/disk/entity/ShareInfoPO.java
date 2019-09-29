package com.aiyi.disk.disk.entity;

import com.aiyi.core.annotation.po.ID;
import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.annotation.po.TempField;
import com.aiyi.core.beans.PO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gsk
 * @description: 分享详情实体
 * @date 2019/09/29
 * @email 719348277@qq.com
 */
@TableName(name = "file_share")
public class ShareInfoPO extends PO {

    @ID
    private String id;

    private String name;

    private String fileKey;

    private String password;

    private BigDecimal amount;

    private BigDecimal speed;

    private Long uid;

    private Date createTime;

    private long downloadCount;

    @TempField
    private String nickerName;

    @TempField
    private String username;

    @TempField
    private String avatar;

    public ShareInfoPO(){super();}

    private ShareInfoPO(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setFileKey(builder.fileKey);
        setPassword(builder.password);
        setAmount(builder.amount);
        setSpeed(builder.speed);
        setUid(builder.uid);
        setCreateTime(builder.createTime);
        setDownloadCount(builder.downloadCount);
        setNickerName(builder.nickerName);
        setUsername(builder.username);
        setAvatar(builder.avatar);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getNickerName() {
        return nickerName;
    }

    public void setNickerName(String nickerName) {
        this.nickerName = nickerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String fileKey;
        private String password;
        private BigDecimal amount;
        private BigDecimal speed;
        private Long uid;
        private Date createTime;
        private long downloadCount;
        private String nickerName;
        private String username;
        private String avatar;

        private Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder fileKey(String val) {
            fileKey = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder amount(BigDecimal val) {
            amount = val;
            return this;
        }

        public Builder speed(BigDecimal val) {
            speed = val;
            return this;
        }

        public Builder uid(Long val) {
            uid = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder downloadCount(long val) {
            downloadCount = val;
            return this;
        }

        public Builder nickerName(String val) {
            nickerName = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder avatar(String val) {
            avatar = val;
            return this;
        }

        public ShareInfoPO build() {
            return new ShareInfoPO(this);
        }
    }
}
