package com.aiyi.disk.disk.entity;

import com.aiyi.core.annotation.po.ID;
import com.aiyi.core.annotation.po.TableName;
import com.aiyi.core.annotation.po.vali.Validation;
import com.aiyi.core.beans.PO;

import java.util.Date;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-26 13:45
 * @Email 719348277@qq.com
 * @Description:
 */
@TableName(name = "baidu_disk_link")
public class BaiduDiskLinkPO extends PO {

    @ID
    private String id;

    private long uid;

    @Validation(name = "直链名称", maxLength = 50, minLength = 1)
    private String name;

    @Validation(name = "文件所在百度网盘路径", maxLength = 255, minLength = 1)
    private String filePath;

    private String link;

    private String form;

    private Date createTime;

    private Date updateTime;

    public BaiduDiskLinkPO(){super();}

    private BaiduDiskLinkPO(Builder builder) {
        setId(builder.id);
        setUid(builder.uid);
        setName(builder.name);
        setFilePath(builder.filePath);
        setLink(builder.link);
        setForm(builder.form);
        setCreateTime(builder.createTime);
        setUpdateTime(builder.updateTime);
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

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static final class Builder {
        private String id;
        private long uid;
        private String name;
        private String filePath;
        private String link;
        private String form;
        private Date createTime;
        private Date updateTime;

        private Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder uid(long val) {
            uid = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder filePath(String val) {
            filePath = val;
            return this;
        }

        public Builder link(String val) {
            link = val;
            return this;
        }

        public Builder form(String val) {
            form = val;
            return this;
        }

        public Builder createTime(Date val) {
            createTime = val;
            return this;
        }

        public Builder updateTime(Date val) {
            updateTime = val;
            return this;
        }

        public BaiduDiskLinkPO build() {
            return new BaiduDiskLinkPO(this);
        }
    }
}