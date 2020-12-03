package com.dataexa.feign;

import com.dataexa.feign.fallback.IndicatorFallback;
import com.dataexa.service.api.IndicatorApiService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Service
@FeignClient(value = "indicator-manager-service", fallbackFactory = IndicatorFallback.class)
public interface IndicatorFeign extends IndicatorApiService {
}
