package com.sys.bill.common.entity;

import com.sys.bill.common.entity.AbstractEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Title: BaseEntity
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 10:48
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> {


    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "native")
    private ID id;

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public ID getId() {
        return id;
    }
}
