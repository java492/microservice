package com.dataexa.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "db_user")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '账号名称'")
    private String userName;

    @Column(nullable = false, columnDefinition = "tinyint(1) COMMENT '年龄'")
    private Integer age;
}
