package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_bank")
public class Bank extends AbstractCodeOLObject {
    private static final long serialVersionUID = -1834997390961013651L;
    private String name;
    private String address;

    @Column(name = "bank_name", length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address", length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
