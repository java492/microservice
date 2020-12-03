package com.dataexa.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Data
@ApiModel(description = "创建指标")
public class IndicatorCreateDTO {
    @ApiModelProperty(value = "指标名称", required = true)
    @NotBlank(message = "指标名称不能为空")
    private String name;

    @ApiModelProperty(value = "编码", required = true)
    @NotNull(message = "编码不能为空")
    private String code;
}
