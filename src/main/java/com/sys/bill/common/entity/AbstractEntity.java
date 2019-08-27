package com.sys.bill.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Title: AbstractEntity
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 10:42
 */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {

    /**
     * 设置Entity的ID
     *
     * @param id
     */
    public abstract void setId(final ID id);

    @JsonIgnore
    @Override
    public boolean isNew() {
        Serializable id = getId();
        return id == null || StringUtils.isBlank(String.valueOf(id));
    }

}
