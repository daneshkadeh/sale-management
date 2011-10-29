package com.s3s.ssm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * This class for object with id primary key.
 * 
 * @author phamcongbang
 */

@MappedSuperclass
public abstract class AbstractBaseIdObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Transient
    public Boolean isPersisted() {
        return (id != null) && id > -1;
    }

}
