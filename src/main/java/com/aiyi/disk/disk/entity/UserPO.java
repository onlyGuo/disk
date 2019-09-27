package com.aiyi.disk.disk.entity;

import com.aiyi.core.annotation.po.ID;
import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.annotation.po.vali.Validation;
import com.aiyi.core.annotation.po.vali.enums.ValidationType;
import com.aiyi.core.beans.PO;

import java.util.Date;

/**
 * 用户表实体
 * @author gsk
 */
@TableName(name = "uc_user")
public class UserPO extends PO {

    @ID
    private long id;

    @Validation(name = "用户名", maxLength = 50, minLength = 3)
    private String username;

    @Validation(name = "用户密码")
    private String password;

    @Validation(name = "电子邮箱", minLength = 3, maxLength = 100)
    private String email;

    @Validation(name = "用户昵称", minLength = 1, maxLength = 50)
    private String nickerName;

    @Validation(name = "用户手机号", value = ValidationType.Phone)
    private String phone;

    @Validation(name = "创建时间")
    private Date createTime;

    @Validation(name = "最后登录时间")
    private Date lastLoginTime;

    @Validation(name = "阿里云Access Key", maxLength = 50)
    private String accessKey;

    @Validation(name = "阿里云Access Key Secret", maxLength = 100)
    private String accessKeySecret;

    @Validation(name = "阿里云OSS Bucket", maxLength = 100)
    private String bucket;

    @Validation(name = "阿里云OSS外网节点", maxLength = 255)
    private String endPoint;

    @Validation(name = "用户头像")
    private String avatar;

    public UserPO(){super();}

    private UserPO(Builder builder) {
        setId(builder.id);
        setUsername(builder.username);
        setPassword(builder.password);
        setEmail(builder.email);
        setNickerName(builder.nickerName);
        setPhone(builder.phone);
        setCreateTime(builder.createTime);
        setLastLoginTime(builder.lastLoginTime);
        setAccessKey(builder.accessKey);
        setAccessKeySecret(builder.accessKeySecret);
        setBucket(builder.bucket);
        setEndPoint(builder.endPoint);
        setAvatar(builder.avatar);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getId() {
        return id;
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

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickerName() {
        return nickerName;
    }

    public void setNickerName(String nickerName) {
        this.nickerName = nickerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public static final class Builder {
        private long id;
        private String username;
        private String password;
        private String email;
        private String nickerName;
        private String phone;
        private Date createTime;
        private Date lastLoginTime;
        private String accessKey;
        private String accessKeySecret;
        private String bucket;
        private String endPoint;
        private String avatar;

        private Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder nickerName(String val) {
            nickerName = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder lastLoginTime(Date val) {
            lastLoginTime = val;
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

        public Builder bucket(String val) {
            bucket = val;
            return this;
        }

        public Builder endPoint(String val) {
            endPoint = val;
            return this;
        }

        public Builder avatar(String val) {
            avatar = val;
            return this;
        }

        public UserPO build() {
            return new UserPO(this);
        }
    }
}
