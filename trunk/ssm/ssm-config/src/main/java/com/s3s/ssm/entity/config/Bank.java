package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_bank")
public class Bank extends AbstractCodeOLObject {
    private static final long serialVersionUID = -1834997390961013651L;
    private String bankName;

    @Column(name = "bank_name")
    @Size(max = 128, message = "bank.length.error")
    public String getName() {
        return bankName;
    }

    public void setName(String bankName) {
        this.bankName = bankName;
    }
}
