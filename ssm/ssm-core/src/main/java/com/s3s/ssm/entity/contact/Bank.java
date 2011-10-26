package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_bank")
public class Bank extends AbstractCodeOLObject {
    private String bankName;

    @Column(name = "bank_name", length = 128)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
