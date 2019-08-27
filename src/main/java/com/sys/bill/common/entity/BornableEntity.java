package com.sys.bill.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Title: BornableEntity
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 10:55
 */
@MappedSuperclass
public abstract class BornableEntity<ID extends Serializable> extends BaseEntity<ID> {

    @Getter
    @Setter
    @JsonIgnore
    @Column(name = "create_time")
    protected LocalDateTime createTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

}
