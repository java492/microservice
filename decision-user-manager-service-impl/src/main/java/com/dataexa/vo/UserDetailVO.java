package com.dataexa.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 胡志成
 * @Date 2020/6/2
 */
@Data
@ApiModel(description = "用户明细")
public class UserDetailVO {
    @ApiModelProperty(value = "用户id", required = true)
    private Long id;

    @ApiModelProperty(value = "用户名称", required = true)
    private String name;

    @ApiModelProperty(value = "用户年龄", required = true)
    private Integer age;
}
