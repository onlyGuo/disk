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

    private Date createTime;

    private Date lastLoginTime;

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
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public long getId() {
        return id;
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

        public UserPO build() {
            return new UserPO(this);
        }
    }
}
