package com.dataexa.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Indicator extends BaseDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '指标名称'")
    private String name;
    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '指标code'")
    private String code;
}
