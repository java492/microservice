package com.dataexa.feign.fallback;

import com.dataexa.feign.IndicatorFeign;
import com.dataexa.result.RpcRespResult;
import com.dataexa.result.RpcResult;
import feign.hystrix.FallbackFactory;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.TransactionManagerHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Component
@Slf4j
public class IndicatorFallback implements FallbackFactory<IndicatorFeign> {

    @Override
    public IndicatorFeign create(Throwable throwable) {
        log.error("接口被熔断了,原因:", throwable);
        return new IndicatorFeign() {
            @Override
            public RpcResult findIndicator(Long id) {
                return RpcRespResult.fail(throwable.getMessage());
            }

            @Override
            public RpcResult createIndicator(String name, String code) {
                try {
                    TransactionManagerHolder.get().rollback(RootContext.getXID());
                } catch (TransactionException e) {
                    log.error(e.getMessage(),e);
                }
                return RpcRespResult.fail(throwable.getMessage());
            }
        };
    }
}
