package com.dataexa.controller;

import com.dataexa.feign.IndicatorFeign;
import com.dataexa.vo.UserDetailVO;
import com.dataexa.domain.User;
import com.dataexa.dto.UserCreateDTO;
import com.dataexa.result.RespResult;
import com.dataexa.result.Result;
import com.dataexa.service.UserService;
import com.dataexa.utils.BeanCopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author 胡志成
 * @Date 2020/6/2
 */
@RefreshScope
@Api(description = "账号管理", tags = "UserManager")
@RequestMapping("/user-manager/")
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private IndicatorFeign indicatorFeign;
    @Value("${server.port}")
    private Integer port;

    @ApiOperation(value = "创建用户", nickname = "userCreate")
    @PostMapping("userCreate")
    public Result crate(@Valid @RequestBody UserCreateDTO dto) {
        userService.createUser(dto);
        return RespResult.success("账号创建成功");
    }

    @ApiOperation(value = "查找用户", nickname = "userFindOne")
    @GetMapping("userFindOne")
    public Result<UserDetailVO> findOne(@ApiParam(value = "id") @RequestParam Long id) {
        User user = userService.findOne(id);
        UserDetailVO userDetailVO = BeanCopyUtil.copyObj(user, UserDetailVO.class);
        System.out.println(port);
        return RespResult.success(userDetailVO);
    }


    @ApiOperation(value = "测试1", nickname = "test1")
    @GetMapping("test1")
    public Result<Integer> test1() {
        indicatorFeign.findIndicator(1L);
        return RespResult.success();
    }

    @ApiOperation(value = "测试2", nickname = "test2")
    @GetMapping("test2")
    public Result<Integer> test2() {
        int i = 1/0;
        return RespResult.success();
    }
}
