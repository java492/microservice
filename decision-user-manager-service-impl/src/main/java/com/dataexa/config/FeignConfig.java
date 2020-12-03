package com.dataexa.config;

import com.alibaba.fastjson.JSONObject;
import com.dataexa.exception.BusinessException;
import com.dataexa.dto.FeignExceptionInfo;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static feign.FeignException.errorStatus;

/**
 * @Author 胡志成
 * @Date 2020/4/19
 */
@Slf4j
@Configuration
public class FeignConfig extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        String body;
        Exception exception = null;
        try {
            body = Util.toString(response.body().asReader());
            if (StringUtils.isBlank(body)) {
                FeignExceptionInfo feignExceptionInfo = JSONObject.parseObject(body, FeignExceptionInfo.class);
                return new BusinessException(feignExceptionInfo.getMessage());
            }
        } catch (Exception e) {
            log.error("feign捕获异常时错误:{}" + e.getMessage());
            exception = e;
        }
        if (exception == null) {
            return errorStatus(methodKey, response);
        }
        return exception;
    }

    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }
}
