package com.dataexa.controller;

import com.dataexa.domain.Indicator;
import com.dataexa.result.RespResult;
import com.dataexa.result.Result;
import com.dataexa.service.IndicatorService;
import com.dataexa.service.dto.IndicatorCreateDTO;
import com.dataexa.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@RefreshScope
@Api(description = "指标管理", tags = "IndicatorManager")
@RequestMapping("/indicator-manager/")
@RestController
public class IndicatorController {
    @Resource
    private IndicatorService indicatorService;
    @Resource
    private RedisUtil redisUtil;

    @ApiOperation(value = "创建指标", nickname = "indicatorCreate")
    @PostMapping("indicatorCreate")
    public Result crate(@Valid @RequestBody IndicatorCreateDTO dto) {
        indicatorService.createIndicator(dto);
        return RespResult.success("指标创建成功");
    }

    @ApiOperation(value = "查找指标", nickname = "indicatorFindOne")
    @GetMapping("indicatorFindOne")
    public Result<Indicator> findOne(@ApiParam(value = "id") @RequestParam Long id) {
        Indicator indicator = indicatorService.findOne(id);
        return RespResult.success(indicator);
    }

    @ApiOperation(value = "测试redis", nickname = "testRedis")
    @GetMapping("testRedis")
    public Result testRedis() {
        redisUtil.set("test", "单机");
        return RespResult.success();
    }
}
