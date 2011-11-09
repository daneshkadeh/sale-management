package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_customer")
public class CustomerTest extends AbstractBaseIdObject {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false)
    @NotNull
    public String getName() {
        return name;
    }
}
