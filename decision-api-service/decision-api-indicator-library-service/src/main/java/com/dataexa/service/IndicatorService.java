package com.dataexa.service;

import com.dataexa.domain.Indicator;
import com.dataexa.service.dto.IndicatorCreateDTO;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
public interface IndicatorService {

    /**
     * 创建指标
     * @param dto
     */
    void createIndicator(IndicatorCreateDTO dto);

    /**
     *
     * @param id
     * @return
     */
    Indicator findOne(Long id);
}
