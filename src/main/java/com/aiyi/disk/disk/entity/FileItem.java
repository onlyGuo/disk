package com.aiyi.disk.disk.entity;

import java.util.Date;

/**
 * @author gsk
 * @description: 文件实体
 * @date 2019/09/27
 * @email 719348277@qq.com
 */
public class FileItem {

    private String name;

    private String ossKey;

    private String size;

    private String make;

    private String dir;

    private String keywork;

    private Date updateTime;

    /**
     * 0 = 目录， 1 = 文件
     */
    private int type;

    public FileItem(){super();}

    private FileItem(Builder builder) {
        setName(builder.name);
        setOssKey(builder.ossKey);
        setSize(builder.size);
        setMake(builder.make);
        setDir(builder.dir);
        setKeywork(builder.keywork);
        setUpdateTime(builder.updateTime);
        setType(builder.type);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getKeywork() {
        return keywork;
    }

    public void setKeywork(String keywork) {
        this.keywork = keywork;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOssKey() {
        return ossKey;
    }

    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public static final class Builder {
        private String name;
        private String ossKey;
        private String size;
        private String make;
        private String dir;
        private String keywork;
        private Date updateTime;
        private int type;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder ossKey(String val) {
            ossKey = val;
            return this;
        }

        public Builder size(String val) {
            size = val;
            return this;
        }

        public Builder make(String val) {
            make = val;
            return this;
        }

        public Builder dir(String val) {
            dir = val;
            return this;
        }

        public Builder keywork(String val) {
            keywork = val;
            return this;
        }

        public Builder updateTime(Date val) {
            updateTime = val;
            return this;
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public FileItem build() {
            return new FileItem(this);
        }
    }
}
