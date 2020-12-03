package com.dataexa.dto;

import lombok.Data;

/**
 * @Author 胡志成
 * @Date 2020/4/19
 */
@Data
public class FeignExceptionInfo {
    /**
     * {
     *  "timestamp": "2017-12-27 15:01:53",
     *   "status": 500,
     *   "error": "Internal Server Error",
     *   "exception": "com.keruyun.loyalty.commons.master.exception.BusinessException",
     *   "message": "Request processing failed; nested exception is {\"code\":1000, \"message\":\"test Exception\"}",
     *   "path": "/coupon/cloud/commercial/8469"
     *   }
     */

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 异常类
     */
    private Throwable exception;

    /**
     * 消息json
     */
    private String message;

    /**
     * 请求url
     */
    private String path;

    /**
     * 错误信息
     */
    private String error;
}
