package com.dataexa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    /**
     * 帐号
     */
    private String userName;

    /**
     * 状态 0停用，1启用
     */
    private Integer status;

    /**
     * 角色id
     */
    private String roleIds;
}
