package com.aiyi.disk.disk.service;

import com.aiyi.core.beans.ResultPage;
import com.aiyi.disk.disk.entity.BaiduDiskLinkPO;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-26 13:58
 * @Email 719348277@qq.com
 * @Description: 百度网盘直线相关业务
 */
public interface BaiduDiskLinkService {

    /**
     * 列出百度网盘列表
     * @param start
     *      开始位置
     * @param length
     *      长度
     * @return
     */
    ResultPage<BaiduDiskLinkPO> list(int start, int length, long uid);

    /**
     * 创建百度网盘直链
     * @param baiduDiskLinkPO
     *      百度网盘直链信息
     * @return
     */
    BaiduDiskLinkPO create(BaiduDiskLinkPO baiduDiskLinkPO);

    /**
     * 通过直链ID获取有效的真实文件地址
     * @param linkId
     * @return
     */
    String getLinkById(String linkId);

}