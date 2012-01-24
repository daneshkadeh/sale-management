/*
 * Customer
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.entity.contact;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "s_customer")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends Partner {
    private String fixPhone;
    private String mobilePhone;
    private String fax;
    private String email;
    private String address;
    private String taxCode;
    private Set<ContactShop> listShops = new HashSet<>();

    private BankAccount bankAccount;

    @Column(name = "fix_phone", length = 20)
    public String getFixPhone() {
        return fixPhone;
    }

    public void setFixPhone(String fixPhone) {
        this.fixPhone = fixPhone;
    }

    @Column(name = "mobile_phone", length = 20)
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "fax", length = 20)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "email", length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "address", length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "tax_code", length = 50)
    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    //
    @Transient
    public Bank getBank() {
        return bankAccount != null ? bankAccount.getBank() : null;
    }

    @Transient
    public void setBank(Bank bank) {
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }
        bankAccount.setBank(bank);
    }

    @Transient
    public String getAccountNumber() {
        return bankAccount != null ? bankAccount.getAccountNumber() : null;
    }

    @Transient
    public void setAccountNumber(String accountNumber) {
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }
        bankAccount.setAccountNumber(accountNumber);
    }

    @Transient
    public String getAccountName() {
        return bankAccount != null ? bankAccount.getAccountName() : null;
    }

    @Transient
    public void setAccountName(String accountName) {
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }
        bankAccount.setAccountName(accountName);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    public Set<ContactShop> getListShops() {
        return listShops;
    }

    public void setListShops(Set<ContactShop> listShops) {
        this.listShops = listShops;
    }

    // public void addShop(ContactShop contactShop) {
    // contactShop.setContact(this);
    // listShops.add(contactShop);
    // }
}
