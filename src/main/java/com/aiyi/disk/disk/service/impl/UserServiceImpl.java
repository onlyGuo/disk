package com.aiyi.disk.disk.service.impl;

import com.aiyi.core.beans.Method;
import com.aiyi.core.sql.where.C;
import com.aiyi.core.util.MD5;
import com.aiyi.disk.disk.dao.UserDao;
import com.aiyi.disk.disk.entity.UserPO;
import com.aiyi.disk.disk.service.UserService;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author gsk
 * @description: 用户相关业务实现
 * @date 2019/09/27
 * @email 719348277@qq.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public UserPO createUser(UserPO user) {
        user = userDao.get(Method.where("username", C.EQ, user.getUsername()));
//        if (userDao.isExist(Method.where("username", C.EQ, user.getUsername()))){
//            throw new ValidationException("用户名[" + user.getUsername() + "]已存在，请更换其他用户名");
//        }
//        if (userDao.isExist(Method.where("email", C.EQ, user.getEmail()))){
//            throw new ValidationException("电子邮箱已经被使用");
//        }
//
//        user.setPassword(MD5.getMd5(user.getPassword()));
//        user.setNickerName(user.getUsername());
//        user.setCreateTime(new Date());
//        userDao.add(user);
//
        OSS build = new OSSClientBuilder().build(user.getEndPoint(), user.getAccessKey(), user.getAccessKeySecret());
//
//        try {
//            build.createBucket(user.getBucket());
//        }catch (ClientException e){
//            if (e.getErrorCode().equals("UnknownHost")){
//                throw new RuntimeException("未能连接到节点[" + user.getEndPoint() + "], 请检查节点设置是否正确。");
//            }
//            throw e;
//        }catch (OSSException e){
//            if (e.getErrorCode().equals("InvalidAccessKeyId")){
//                throw new RuntimeException("您提供的AccessKeyId可能存在错误,请核实");
//            }
//            if (e.getErrorCode().equals("SignatureDoesNotMatch")){
//                throw new RuntimeException("创建bucket失败, 原因:[SignatureDoesNotMatch], 请检查AccessKeySecret");
//            }
//            if (e.getErrorCode().equals("BucketAlreadyExists")){
//                throw new ValidationException("该Bucket[" + user.getBucket() + "]已存在，请更换其他bucketName");
//            }
//            if (e.getErrorCode().equals("InvalidBucketName")){
//                throw new ValidationException("该BucketName无效，不能存在下划线等特殊字符");
//            }
//            throw e;
//        }

        // 上传头像
        try (InputStream inputStream = new ClassPathResource("static/images/user.png").getInputStream();){
            build.putObject(user.getBucket(), ".系统文件请勿删除/avatar.png", inputStream);
        } catch (IOException e) {
            throw new RuntimeException("头像文件上传失败");
        }
        URL url = build.generatePresignedUrl(user.getBucket(), ".系统文件请勿删除/avatar.png", new Date(System.currentTimeMillis() +
                1000 * 60 * 60 * 24 * 360 * 10));
        user.setAvatar(url.toString());
        userDao.update(user);
        user.setPassword(null);
        build.shutdown();
        return user;
    }

    @Override
    public UserPO loginByUserName(String username, String password) {
        UserPO user = userDao.get(Method.where("username", C.EQ, username));
        if (null == user){
            throw new ValidationException("用户不存在");
        }
        if (!user.getPassword().equals(MD5.getMd5(password))){
            throw new ValidationException("用户名或密码错误");
        }
        return user;
    }
}
