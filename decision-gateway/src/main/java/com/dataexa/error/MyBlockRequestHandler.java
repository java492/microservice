package com.dataexa.error;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.dataexa.reslut.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * @Author 胡志成
 * @Date 2020/8/27
 */
@Slf4j
public class MyBlockRequestHandler implements BlockRequestHandler {
    private static final String DEFAULT_BLOCK_MSG = "服务器正忙,请稍后重试";

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(buildErrorResult(ex)));
    }

    private ErrorResult buildErrorResult(Throwable ex) {
        return new ErrorResult().setCode(HttpStatus.TOO_MANY_REQUESTS.value()).setMsg(DEFAULT_BLOCK_MSG);
    }
}
