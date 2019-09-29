package com.aiyi.disk.disk.service.impl;

import com.aiyi.core.beans.Method;
import com.aiyi.core.sql.where.C;
import com.aiyi.disk.disk.controller.FileController;
import com.aiyi.disk.disk.dao.ShareInfoDao;
import com.aiyi.disk.disk.dao.UserDao;
import com.aiyi.disk.disk.entity.ShareInfoPO;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.ShareInfoService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gsk
 * @description: TODO
 * @date 2019/09/29
 * @email 719348277@qq.com
 */
@Service
public class ShareInfoServiceImpl implements ShareInfoService {

    @Resource
    private ShareInfoDao shareInfoDao;

    @Resource
    private UserDao userDao;

    @Override
    public ShareInfoPO create(ShareInfoPO shareInfoPO) {
        shareInfoDao.add(shareInfoPO);
        return shareInfoPO;
    }

    @Override
    public ShareInfoPO getById(String id) {
        ShareInfoPO shareInfoPO = shareInfoDao.get(id);
        if (null == shareInfoPO){
            return null;
        }
        UserPO userPO = userDao.get(shareInfoPO.getUid());

        shareInfoPO.setUsername(userPO.getUsername());
        shareInfoPO.setNickerName(userPO.getNickerName());
        shareInfoPO.setAvatar(userPO.getAvatar());

        OSS client = new OSSClientBuilder().build(shareInfoPO.getEndPoint(), shareInfoPO.getAccessKey(),
                shareInfoPO.getAccessKeySecret());

        SimplifiedObjectMeta meta = null;
        try {
            meta = client.getSimplifiedObjectMeta(shareInfoPO.getBucketName(), shareInfoPO.getFileKey());
        }catch (Exception e){
            return null;
        }
        if (null == meta){
            return null;
        }
        long size = meta.getSize();
        shareInfoPO.setSize(FileController.getFileSize2Str(size));

        if (meta.getLastModified().getTime() > shareInfoPO.getCreateTime().getTime()){
            shareInfoPO.setCreateTime(meta.getLastModified());
        }

        return shareInfoPO;
    }

    @Override
    public void deleteByFileKey(String fileKey, String bucket) {
        String sql = "DELETE FROM %s WHERE fileKey LIKE ? AND bucketName = ?";
        shareInfoDao.execute(String.format(sql, shareInfoDao.tableName()), fileKey + "%", bucket);
    }
}
