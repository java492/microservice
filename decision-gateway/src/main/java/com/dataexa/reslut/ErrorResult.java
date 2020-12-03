package com.dataexa.reslut;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author 胡志成
 * @Date 2020/8/25
 */
@Accessors(chain = true)
@Data
public class ErrorResult {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;
}
