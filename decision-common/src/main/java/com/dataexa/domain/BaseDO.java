package com.dataexa.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author yeyangtao created at 10:37 2020/8/12
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public abstract class BaseDO {

    @CreatedDate
    @Column(columnDefinition = "DATETIME NULL DEFAULT NULL COMMENT '创建日期'", updatable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(columnDefinition = "DATETIME NULL DEFAULT NULL COMMENT '最后修改日期'")
    private Date updateTime;

    @Column(columnDefinition = "BIT(1) NULL DEFAULT 0 COMMENT '删除标识'", insertable = false)
    private Boolean deletedFlag = false;
}
