package com.aiyi.disk.disk.service;

import com.aiyi.disk.disk.entity.ShareInfoPO;

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

}
