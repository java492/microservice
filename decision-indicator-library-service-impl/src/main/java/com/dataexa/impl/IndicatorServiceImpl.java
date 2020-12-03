package com.dataexa.impl;

import com.alibaba.fastjson.JSON;
import com.dataexa.domain.Indicator;
import com.dataexa.repository.IndicatorRepository;
import com.dataexa.service.IndicatorService;
import com.dataexa.service.dto.IndicatorCreateDTO;
import com.dataexa.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {
    @Resource
    private IndicatorRepository repository;

    @Override
    public void createIndicator(IndicatorCreateDTO dto) {
        Indicator indicator = BeanCopyUtil.copyObj(dto, Indicator.class);
        indicator.setCreateTime(new Date());
        indicator.setUpdateTime(new Date());
        repository.save(indicator);
    }

    @Override
    public Indicator findOne(Long id) {
        Indicator indicator = repository.findByIdAndDeletedFlag(id, false);
        System.out.println(JSON.toJSONString(indicator));
        return indicator;
    }
}
