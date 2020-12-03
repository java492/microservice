package com.dataexa.result;

import lombok.Data;

/**
 * @author 胡志成
 * @date 2020/6/17
 */
@Data
public class RpcResult<T> {
    private Integer code;
    private String msg;
    private T data;
}
