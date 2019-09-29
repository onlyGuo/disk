package com.aiyi.disk.disk.service.impl;

import com.aiyi.disk.disk.dao.ShareInfoDao;
import com.aiyi.disk.disk.entity.ShareInfoPO;
import com.aiyi.disk.disk.service.ShareInfoService;
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

    @Override
    public ShareInfoPO create(ShareInfoPO shareInfoPO) {
        shareInfoDao.add(shareInfoPO);
        return shareInfoPO;
    }
}
