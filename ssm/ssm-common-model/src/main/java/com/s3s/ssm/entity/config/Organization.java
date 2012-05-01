/*
 * Organization
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

package com.s3s.ssm.entity.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.s3s.ssm.entity.finance.PaymentMode;
import com.s3s.ssm.entity.operator.Stall;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "config_organization")
public class Organization extends AbstractActiveCodeOLObject {
    private Institution institution;
    private String name;
    private String address;
    // information of bank
    private BankAccount usdBankAcct;
    private BankAccount vndBankAcct;
    private String beneficeName;
    // general parameter
    private SCurrency defCurrency; // default currency
    private Integer defDetailInvNum = 10; // number of rows on a invoice
    private Integer defPageRowNum = 10; // number of rows on a page
    private PaymentMode defPaymentMethod;
    private Stall defStall;
    private Integer enableChangeInvDate; // 0: not accept, 1: accept when inserting, 0: accept when creating
    // sell on credit
    private Integer sellOnCredit; // 0: cho phep ban am, 1: hoi neu ban am, 2: ko cho phep ban am
    // setting decimal format
    private Integer digitAfterQuan = 5;
    private Integer digitAfterUnitPrice = 5;
    private Integer digitAfterRate = 2;
    private Integer digitAfterAmt = 2;
    private String thousandsSeparator = ".";
    private String oddSeparator = ",";
    private Boolean isDefault = false;

    private Set<SalesChannel> responsibleSalesChannels = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institution_id")
    @NotNull
    @Cascade({ CascadeType.SAVE_UPDATE })
    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    @Column(name = "name", length = 100)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address", length = 250)
    @NotBlank
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne
    @JoinColumn(name = "usd_bank_acct_id")
    public BankAccount getUsdBankAcct() {
        return usdBankAcct;
    }

    public void setUsdBankAcct(BankAccount usdBankAcct) {
        this.usdBankAcct = usdBankAcct;
    }

    @ManyToOne
    @JoinColumn(name = "vnd_bank_acct_id")
    public BankAccount getVndBankAcct() {
        return vndBankAcct;
    }

    public void setVndBankAcct(BankAccount vndBankAcct) {
        this.vndBankAcct = vndBankAcct;
    }

    @Column(name = "benefice_name", length = 256)
    public String getBeneficeName() {
        return beneficeName;
    }

    public void setBeneficeName(String beneficeName) {
        this.beneficeName = beneficeName;
    }

    @ManyToOne
    @JoinColumn(name = "def_currency_id")
    @NotNull
    public SCurrency getDefCurrency() {
        return defCurrency;
    }

    public void setDefCurrency(SCurrency defCurrency) {
        this.defCurrency = defCurrency;
    }

    @Column(name = "def_detail_inv_num", length = 3)
    @NotNull
    @Digits(fraction = 0, integer = 3)
    public Integer getDefDetailInvNum() {
        return defDetailInvNum;
    }

    public void setDefDetailInvNum(Integer defDetailInvNum) {
        this.defDetailInvNum = defDetailInvNum;
    }

    @Column(name = "def_page_row_num", length = 3)
    @NotNull
    @Digits(fraction = 0, integer = 3)
    public Integer getDefPageRowNum() {
        return defPageRowNum;
    }

    public void setDefPageRowNum(Integer defPageRowNum) {
        this.defPageRowNum = defPageRowNum;
    }

    @Column(name = "def_payment_method")
    @Enumerated(EnumType.STRING)
    @NotNull
    public PaymentMode getDefPaymentMethod() {
        return defPaymentMethod;
    }

    public void setDefPaymentMethod(PaymentMode defPaymentMethod) {
        this.defPaymentMethod = defPaymentMethod;
    }

    @ManyToOne
    @JoinColumn(name = "def_stall_id")
    public Stall getDefStall() {
        return defStall;
    }

    public void setDefStall(Stall defStall) {
        this.defStall = defStall;
    }

    @Column(name = "enable_chg_inv_date", length = 1)
    @NotNull
    @Digits(fraction = 0, integer = 1)
    public Integer getEnableChangeInvDate() {
        return enableChangeInvDate;
    }

    public void setEnableChangeInvDate(Integer enableChangeInvDate) {
        this.enableChangeInvDate = enableChangeInvDate;
    }

    @Column(name = "sell_on_credit", length = 1)
    @Digits(fraction = 0, integer = 1)
    public Integer getSellOnCredit() {
        return sellOnCredit;
    }

    public void setSellOnCredit(Integer sellOnCredit) {
        this.sellOnCredit = sellOnCredit;
    }

    @Column(name = "digit_after_quan", length = 2)
    @Max(value = 10)
    public Integer getDigitAfterQuan() {
        return digitAfterQuan;
    }

    public void setDigitAfterQuan(Integer digitAfterQuan) {
        this.digitAfterQuan = digitAfterQuan;
    }

    @Column(name = "digit_after_unit_price", length = 2)
    @Max(value = 10)
    public Integer getDigitAfterUnitPrice() {
        return digitAfterUnitPrice;
    }

    public void setDigitAfterUnitPrice(Integer digitAfterUnitPrice) {
        this.digitAfterUnitPrice = digitAfterUnitPrice;
    }

    @Column(name = "digit_after_rate", length = 2)
    @Max(value = 10)
    public Integer getDigitAfterRate() {
        return digitAfterRate;
    }

    public void setDigitAfterRate(Integer digitAfterRate) {
        this.digitAfterRate = digitAfterRate;
    }

    @Column(name = "digit_after_amt", length = 2)
    @Max(value = 10)
    public Integer getDigitAfterAmt() {
        return digitAfterAmt;
    }

    public void setDigitAfterAmt(Integer digitAfterAmt) {
        this.digitAfterAmt = digitAfterAmt;
    }

    @Column(name = "thousands_Separator", length = 1)
    @NotBlank
    @Size(max = 1)
    public String getThousandsSeparator() {
        return thousandsSeparator;
    }

    public void setThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    @Column(name = "odd_Separator", length = 1)
    @NotBlank
    @Size(max = 1)
    public String getOddSeparator() {
        return oddSeparator;
    }

    public void setOddSeparator(String oddSeparator) {
        this.oddSeparator = oddSeparator;
    }

    @Column(name = "is_default", length = 1)
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    @OneToMany(mappedBy = "organization")
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade({ CascadeType.ALL })
    public Set<SalesChannel> getResponsibleSalesChannels() {
        return responsibleSalesChannels;
    }

    public void setResponsibleSalesChannels(Set<SalesChannel> responsibleSalesChannels) {
        this.responsibleSalesChannels = responsibleSalesChannels;
    }
}