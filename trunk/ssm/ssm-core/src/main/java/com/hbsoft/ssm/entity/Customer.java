package com.hbsoft.ssm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_customer")
public class Customer extends BaseIdObject {
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
