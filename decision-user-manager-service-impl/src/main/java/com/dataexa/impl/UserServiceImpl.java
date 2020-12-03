package com.dataexa.impl;

import com.dataexa.domain.User;
import com.dataexa.dto.UserCreateDTO;
import com.dataexa.feign.IndicatorFeign;
import com.dataexa.repository.UserRepository;
import com.dataexa.service.UserService;
import com.dataexa.utils.BeanCopyUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository repository;

    @Override
    public User findOne(Long id) {
        return repository.findByIdAndDeletedFlag(id, false);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void createUser(UserCreateDTO dto) {
        User user = BeanCopyUtil.copyObj(dto, User.class);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        repository.save(user);
    }
}
