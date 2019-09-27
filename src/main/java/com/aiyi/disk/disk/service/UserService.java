package com.aiyi.disk.disk.service;

import com.aiyi.disk.disk.entity.UserPO;

/**
 * 用户相关业务类
 * @author gsk
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     *      用户实体
     * @return
     */
    UserPO createUser(UserPO user);

}
