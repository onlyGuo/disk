package com.aiyi.disk.disk.service.impl;

import com.aiyi.core.beans.Method;
import com.aiyi.core.beans.ResultPage;
import com.aiyi.core.sql.where.C;
import com.aiyi.disk.disk.dao.BaiduDiskLinkDao;
import com.aiyi.disk.disk.entity.BaiduDiskLinkPO;
import com.aiyi.disk.disk.service.BaiduDiskLinkService;
import com.aiyi.disk.disk.service.BaiduPCSService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-26 13:59
 * @Email 719348277@qq.com
 * @Description: 百度网盘直线相关业务实现
 */
@Service
public class BaiduDiskLinkServiceImpl implements BaiduDiskLinkService {

    @Resource
    private BaiduDiskLinkDao baiduDiskLinkDao;

    @Resource
    private BaiduPCSService baiduPCSService;


    @Override
    public ResultPage<BaiduDiskLinkPO> list(int start, int length, long uid) {
        List<BaiduDiskLinkPO> list = baiduDiskLinkDao.list(Method.where("uid", C.EQ, uid)
                .limit(start, length).orderBy("createTime"));

        ResultPage<BaiduDiskLinkPO> result = new ResultPage<>(baiduDiskLinkDao.count(Method.where("uid", C.EQ, uid)),
                start / length + 1, length, list);

        return result;
    }

    @Override
    public BaiduDiskLinkPO create(BaiduDiskLinkPO baiduDiskLinkPO) {
        baiduDiskLinkPO.check("name", "filePath");
        if (StringUtils.isEmpty(baiduDiskLinkPO.getForm())){
            baiduDiskLinkPO.setForm("*");
        }

        List<String> directLink = baiduPCSService.createDirectLink(baiduDiskLinkPO.getFilePath());
        baiduDiskLinkPO.setLink(directLink.get(0));
        baiduDiskLinkPO.setCreateTime(new Date());
        baiduDiskLinkPO.setUpdateTime(new Date());

        baiduDiskLinkDao.add(baiduDiskLinkPO);
        return baiduDiskLinkPO;
    }

    @Override
    public String getLinkById(String linkId) {
        BaiduDiskLinkPO baiduDiskLinkPO = baiduDiskLinkDao.get(linkId);
        if (null == baiduDiskLinkPO){
            throw new RuntimeException("文件不存在或该直链已被作者删除");
        }
        if (baiduDiskLinkPO.getUpdateTime().getTime() + 1000L * 60 * 60 * 7 > System.currentTimeMillis()){
            return baiduDiskLinkPO.getLink();
        }
        List<String> directLink = baiduPCSService.createDirectLink(baiduDiskLinkPO.getFilePath());
        baiduDiskLinkPO.setUpdateTime(new Date());
        baiduDiskLinkPO.setLink(directLink.get(0));
        baiduDiskLinkDao.update(baiduDiskLinkPO);
        return directLink.get(0);
    }
}