package com.dataexa.result;

/**
 * @author 胡志成
 * @date 2020/6/17
 */
public class RpcRespResult {
    public static final Integer SUCCESSFUL =  200;
    public static final Integer FAIL =  500;

    public static <T> RpcResult<T> success(T data) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setCode(SUCCESSFUL);
        rpcResult.setData(data);
        return rpcResult;
    }

    public static <T> RpcResult<T> fail(String msg) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setCode(FAIL);
        rpcResult.setMsg(msg);
        return rpcResult;
    }
}
