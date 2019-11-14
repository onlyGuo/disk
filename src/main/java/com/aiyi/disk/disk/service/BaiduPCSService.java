package com.aiyi.disk.disk.service;

import com.aiyi.disk.disk.utils.CMDUtil;
import com.aiyi.disk.disk.utils.ReaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 郭胜凯
 * @Date: 2019-10-26 16:12
 * @Email 719348277@qq.com
 * @Description: 百度网盘客户端业务实现类
 */
@Service
public class BaiduPCSService {

    @Value("${baidu-pcs.path}")
    private String baiduPcsLibPath;

    @Value("${baidu-pcs.bduss}")
    private String baiduAccountbduss;

    @PostConstruct
    public void login(){
        String bduss = ReaderUtil.readToString(baiduAccountbduss);
        String result = CMDUtil.excuse(baiduPcsLibPath, "./BaiduPCS-Go", "login", "-bduss=" + bduss);
        if (!result.contains("登录成功")){
            throw new RuntimeException("百度网盘客户端服务初始化失败:" + result);
        }
    }

    private String execute(String... params){
        String result = CMDUtil.excuse(baiduPcsLibPath, "./BaiduPCS-Go", params);

        if (result.contains("错误") && result.contains("31045")){
            // 可能是登录状态过期
            login();
            result = CMDUtil.excuse(baiduPcsLibPath, "./BaiduPCS-Go", params);
        }
        // 如果还存在错误
        if (result.contains("错误")){
            throw new RuntimeException(result);
        }
        return result;
    }

    /**
     * 创建百度网盘内的某个文件的直链地址
     * @param filePath
     *      文件路径
     * @return
     */
    public List<String> createDirectLink(String filePath){
        filePath = filePath.trim();
        if (!filePath.startsWith("/")){
            filePath = "/" + filePath;
        }
        String result = execute("locate", filePath);
        result.replace("\r\n", "\n");
        String[] split = result.split("\n");

        if (split.length < 2){
            throw new RuntimeException("未能生成直链地址:" + result);
        }
        List<String> linkList = new ArrayList<>();
        for (int i = 1; i < split.length; i++){
            String line = split[i].trim();
            line = line.replaceAll("\\s+", " ");
            String[] lineItems = line.split(" ");
            if (lineItems.length != 2){
                continue;
            }
            try {
                int testNumber = Integer.valueOf(lineItems[0]);
            }catch (Exception e){
                continue;
            }
            linkList.add(lineItems[1]);
        }
        if (linkList.isEmpty()){
            throw new RuntimeException("未能生成直链地址:" + result);
        }
        return linkList;
    }

}