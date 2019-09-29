package com.aiyi.disk.disk.controller;

import com.aiyi.core.beans.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gsk
 * @description: 分享相关
 * @date 2019/09/29
 * @email 719348277@qq.com
 */
@Controller
@RequestMapping("share")
public class ShareController {

    @GetMapping
    @ResponseBody
    public ResultBean share(@RequestBody Object o){
        return null;
    }

}
