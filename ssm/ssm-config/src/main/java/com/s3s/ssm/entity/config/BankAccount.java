/*
 * BankAccount
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "s_bank_account")
public class BankAccount extends AbstractIdOLObject {
    private static final long serialVersionUID = 3551288465254593923L;
    private Bank bank;
    private String accountNumber;
    private String accountName;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    @NotBlank
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Column(name = "account_number", nullable = false, length = 32)
    @Size(max = 32)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "account_name", length = 128)
    @Size(max = 128)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return getBank() != null ? getBank().getCode() + " - " + getAccountNumber() : null;
    }
}
