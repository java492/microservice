package com.dataexa.service.api;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dataexa.result.RpcResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
public interface IndicatorApiService {
    /**
     * 查找指标
     * @param id    指标id
     * @return
     */
    @GetMapping("/indicator-api/findIndicator")
    RpcResult findIndicator(@RequestParam(value = "id") Long id);

    /**
     * 创建指标
     * @param name
     * @param code
     * @return
     */
    @PostMapping("/indicator-api/createIndicator")
    RpcResult createIndicator(@RequestParam(value = "name") String name, @RequestParam(value = "code") String code);
}
