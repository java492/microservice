package com.dataexa.impl.api;

import com.dataexa.domain.Indicator;
import com.dataexa.repository.IndicatorRepository;
import com.dataexa.result.RpcRespResult;
import com.dataexa.result.RpcResult;
import com.dataexa.service.api.IndicatorApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Slf4j
@RestController
public class IndicatorApiServiceImpl implements IndicatorApiService {
    @Resource
    private IndicatorRepository repository;

    @Override
    public RpcResult findIndicator(Long id) {
        System.out.println("调用到指标服务");
        Indicator indicator = repository.findByIdAndDeletedFlag(id, false);
        int i = 1/0;
        return RpcRespResult.success(indicator);
    }


    @Override
    public RpcResult createIndicator(String name, String code) {
        Indicator indicator = new Indicator();
        indicator.setName(name);
        indicator.setCode(code);
        indicator.setCreateTime(new Date());
        indicator.setUpdateTime(new Date());
        log.info("开始插入指标信息");
        Indicator save = repository.save(indicator);
        log.info("指标信息插入成功");
        return RpcRespResult.success(save);
    }
}
