package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Entity with code 32 characters
 * 
 * @author phamcongbang
 * 
 */
@MappedSuperclass
public abstract class AbstractCodeOLObject extends AbstractIdOLObject {
    private String code;

    @Column(name = "code", nullable = false, length = 32)
    @NotNull
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
