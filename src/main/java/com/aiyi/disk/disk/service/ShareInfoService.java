package com.aiyi.disk.disk.service;

import com.aiyi.disk.disk.entity.ShareInfoPO;

import java.util.List;

/**
 * @author gsk
 * @description: 分享相关业务
 * @date 2019/09/29
 * @email 719348277@qq.com
 */
public interface ShareInfoService {

    /**
     * 创建分享
     * @param shareInfoPO
     *      分享详情
     * @return
     */
    ShareInfoPO create(ShareInfoPO shareInfoPO);

    /**
     * 通过ID 获取分享详情
     * @param id
     *      ID
     * @return
     */
    ShareInfoPO getById(String id);

    /**
     * 删除包含指定OSSKey的分享
     * @param fileKey
     *      文件OSSKey
     */
    void deleteByFileKey(String fileKey, String bucket);

    /**
     * 列出某个用户的全部分享
     * @param uid
     *      用户ID
     * @return
     */
    List<ShareInfoPO> list(Long uid);

    /**
     * 通过分享文件ID来删除分享
     * @param fileId
     *      文件ID
     */
    void deleteById(String fileId, Long uid);

}
