package com.aiyi.disk.disk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMDUtil {

    private static Logger logger = LoggerFactory.getLogger(CMDUtil.class);

    public static String excuse(String src) {
        logger.info("Do excust CMD: " + src);
        return excuse(src, null);
    }

    public static String excuse(String repertory, String src, String... params) {
        logger.info("Do excust CMD: " + src + ", AND repertory=" + repertory);
        String msg = "";
        String encode = "UTF-8";
        try {
            String osName = System.getProperties().getProperty("os.name");
            String exc = "";
            if (osName.indexOf("Windows") != -1 || osName.indexOf("windows") != -1) {
                exc += "cmd /c ";
                encode = "GBK";
            } else {
                if (new File(repertory, "bin").exists()) {
                    src = repertory + "/bin/" + src;
                    repertory = null;
                }else if (new File(repertory, "sbin").exists()) {
                    src = repertory + "/sbin/" + src;
                    repertory = null;
                }

            }
            Runtime runtime = Runtime.getRuntime();
            Process pro = null;
            if (null != repertory) {
                List<String> strings = new ArrayList<>();
                strings.add(exc + src.trim());
                for (String s: params){
                    strings.add(s);
                }
                pro = runtime.exec(strings.toArray(new String[0]), null, new File(repertory));
            } else {
                pro = runtime.exec(exc + src.trim());
            }

            //获得结果
            byte[] buff = new byte[2018];

            InputStream inputStream = pro.getInputStream();
            for (int i = inputStream.read(buff); i > 0; i = inputStream.read(buff)) {
                msg += new String(buff, 0, i, encode);
            }

            InputStream errorStream = pro.getErrorStream();
            for (int i = errorStream.read(buff); i > 0; i = errorStream.read(buff)) {
                msg += new String(buff, 0, i, encode);
            }

        } catch (IOException exception) {
            throw new RuntimeException("终端命令执行异常", exception);
        }
        logger.info("CMD Collback MSG = " + msg);
        return msg;
    }

    public static void main(String[] args) {
        excuse("/Users/gsk/Desktop/BaiduPCS-Go/", "./BaiduPCS-Go", "locate", "/我的资源/006 Installing Android Studio On Mac Os X.mp4");

    }
}
