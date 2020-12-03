package com.dataexa.error;

import com.alibaba.fastjson.JSON;
import com.dataexa.enumeration.ExceptionResultEnum;
import com.dataexa.reslut.ErrorResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 胡志成
 * @date 2020/6/28
 */
@Slf4j
@Order(-1)
@Configuration
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ErrorResult fail = new ErrorResult();
        if (ex instanceof ResponseStatusException) {
            HttpStatus status = ((ResponseStatusException) ex).getStatus();
            response.setStatusCode(status);
            int value = status.value();
            String exceptionMsg = ExceptionResultEnum.getExceptionMsg(value);
            if (StringUtils.isBlank(exceptionMsg)) {
                fail.setMsg(ex.getMessage());
            } else {
                fail.setMsg(exceptionMsg);
            }
            fail.setCode(value);
        } else {
            fail.setMsg(ex.getMessage());
            fail.setCode(ExceptionResultEnum.BUSINESS_EXCEPTION.getCode());
        }
        return response
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    try {
                        log.error("异常信息:{}", JSON.toJSONString(fail));
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(fail));
                    } catch (JsonProcessingException e) {
                        log.error("解析异常;{}", ex.getMessage());
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }
}
