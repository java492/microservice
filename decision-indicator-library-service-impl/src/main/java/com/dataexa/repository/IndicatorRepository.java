package com.dataexa.repository;

import com.dataexa.domain.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Long>, JpaSpecificationExecutor<Indicator> {

    Indicator findByIdAndDeletedFlag(Long id, Boolean DeleteFlag);
}
