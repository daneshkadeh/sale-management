package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "s_bank_account")
public class BankAccount extends AbstractIdOLObject {
    private Bank bank;
    private String accountNumber;
    private String accountName;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    @NotNull
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Column(name = "account_number", nullable = false, length = 32)
    @NotNull
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "account_name", length = 128)
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
