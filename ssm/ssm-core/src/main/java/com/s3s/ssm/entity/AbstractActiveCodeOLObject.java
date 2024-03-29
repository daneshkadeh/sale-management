package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractActiveCodeOLObject extends AbstractCodeOLObject implements IActiveObject {
    private Boolean active = true;

    @Override
    @Column(name = "active")
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean isActive) {
        active = isActive;
    }
}
