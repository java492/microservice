package com.dataexa.filter;

import com.dataexa.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author 胡志成
 * @date 2020/6/24
 */
@RefreshScope
@Slf4j
//@Component
public class TokenFilter implements GlobalFilter, Ordered {

    private List<String> skipUrls;
    private static ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("校验token");
        String url = exchange.getRequest().getURI().getPath();
        //跳过不需要验证的路径
        if(!CollectionUtils.isEmpty(skipUrls) && skipUrls.contains(url)){
            return chain.filter(exchange);
        }
        HttpCookie token = exchange.getRequest().getCookies().getFirst("token");
        if (token == null || StringUtils.isBlank(token.getValue())) {
            log.error("获取到的token值为空");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        try {
            boolean flag = JwtUtil.checkToken(token.getValue(), objectMapper);
            if (!flag) {
                //todo 需要判断是否是过期或者认证失败
                log.error("获取到的token值为空");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
