package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import com.aiyi.core.beans.ResultPage;
import com.aiyi.disk.disk.conf.NoAuth;
import com.aiyi.disk.disk.entity.BaiduDiskLinkPO;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.BaiduDiskLinkService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-26 13:42
 * @Email 719348277@qq.com
 * @Description: 百度网盘直连页面
 */
@Controller
@RequestMapping("baidu")
public class BaiduDiskLinkController {

    @Resource
    private BaiduDiskLinkService baiduDiskLinkService;

    /**
     * 获取用户已生成的直链列表
     * @param start
     *      开始位置
     * @param length
     *      列表长度
     * @param request
     * @return
     */
    @GetMapping("/list")
    public String list(Integer start, Integer length, HttpServletRequest request){
        if (null == start){
            start = 0;
        }
        if (null == length){
            length = 30;
        }
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        ResultPage<BaiduDiskLinkPO> list = baiduDiskLinkService.list(start, length, user.getId());

        request.setAttribute("list", list);
        return "/home/baidu/list";
    }

    @GetMapping("create")
    public String create(){
        return "/home/baidu/create";
    }

    /**
     * 创建直链连接
     * @param linkPO
     *      直链信息
     * @param request
     * @return
     */
    @PostMapping("create")
    @ResponseBody
    public ResultBean create(@RequestBody BaiduDiskLinkPO linkPO, HttpServletRequest request){
        UserPO user = (UserPO) request.getSession().getAttribute("LOGIN_USER");
        linkPO.setUid(user.getId());
        BaiduDiskLinkPO baiduDiskLinkPO = baiduDiskLinkService.create(linkPO);
        return ResultBean.success("直链创建成功").putResponseBody("link", baiduDiskLinkPO.getId());
    }

    /**
     * 访问直链(重定向到百度网盘资源真实地址)
     * @param linkId
     *      直链ID
     */
    @GetMapping("link/{linkId}")
    @NoAuth
    public void goToLink(@PathVariable String linkId, HttpServletRequest request, HttpServletResponse response){
        String link = (String)request.getSession().getAttribute("LINK:" + linkId);
        if (StringUtils.isEmpty(link)){
            link = baiduDiskLinkService.getLinkById(linkId);
        }
        request.getSession().setAttribute("LINK:" + linkId, link);
        try {
            response.sendRedirect(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}