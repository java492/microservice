package com.dataexa.service;

import com.dataexa.domain.User;
import com.dataexa.dto.UserCreateDTO;

/**
 * @Author 胡志成
 * @Date 2020/6/2
 */
public interface UserService {

    /**
     * 根据id查找用户
     * @param id    用户id
     * @return
     */
    User findOne(Long id);

    /**
     * 创建用户
     * @param dto
     */
    void createUser(UserCreateDTO dto);
}
