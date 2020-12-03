package com.dataexa.repository;

import com.dataexa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据id查找未删除的用户
     * @param id            用户id
     * @param deleteFlag    删除标识
     * @return
     */
    User findByIdAndDeletedFlag(Long id, Boolean deleteFlag);
}
