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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Stall;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "s_organization")
public class Organization extends AbstractCodeOLObject {
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
    // rule of code generation
    private String orderInvCodeRule;
    private String salesInvCodeRule;
    private String salesRefundInvCodeRule;
    private String purInvCodeRule;
    private String purRefundInvCodeRule;
    private String sponContractCodeRule;
    private String movementInvCodeRule;
    private String exportInvCodeRule;
    private String importInvCodeRule;
    private String paymentBillCodeRule;
    private String receiptsCodeRule;
    private String promotionCodeRule;
    // sell on credit
    private Integer sellOnCredit; // 0: cho phep ban am, 1: hoi neu ban am, 2: ko cho phep ban am
    // setting decimal format
    private Integer digitAfterQuan = 5;
    private Integer digitAfterUnitPrice = 5;
    private Integer digitAfterRate = 2;
    private Integer digitAfterAmt = 2;
    private String thousandsSeparator = ".";
    private String oddSeparator = ",";

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull
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
    @Size(max = 256)
    public String getBeneficeName() {
        return beneficeName;
    }

    public void setBeneficeName(String beneficeName) {
        this.beneficeName = beneficeName;
    }

    @ManyToOne
    @JoinColumn(name = "def_currency_id", nullable = false)
    @NotBlank
    public SCurrency getDefCurrency() {
        return defCurrency;
    }

    public void setDefCurrency(SCurrency defCurrency) {
        this.defCurrency = defCurrency;
    }

    @Column(name = "def_detail_inv_num", length = 3)
    @NotBlank
    @Digits(fraction = 0, integer = 3)
    public Integer getDefDetailInvNum() {
        return defDetailInvNum;
    }

    public void setDefDetailInvNum(Integer defDetailInvNum) {
        this.defDetailInvNum = defDetailInvNum;
    }

    @Column(name = "def_page_row_num", length = 3)
    @NotBlank
    @Digits(fraction = 0, integer = 3)
    public Integer getDefPageRowNum() {
        return defPageRowNum;
    }

    public void setDefPageRowNum(Integer defPageRowNum) {
        this.defPageRowNum = defPageRowNum;
    }

    @Column(name = "def_payment_method", length = 3)
    @Enumerated(EnumType.STRING)
    @NotBlank
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
    @NotBlank
    @Digits(fraction = 0, integer = 1)
    public Integer getEnableChangeInvDate() {
        return enableChangeInvDate;
    }

    public void setEnableChangeInvDate(Integer enableChangeInvDate) {
        this.enableChangeInvDate = enableChangeInvDate;
    }

    @Column(name = "order_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getOrderInvCodeRule() {
        return orderInvCodeRule;
    }

    public void setOrderInvCodeRule(String orderInvCodeRule) {
        this.orderInvCodeRule = orderInvCodeRule;
    }

    @Column(name = "sales_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getSalesInvCodeRule() {
        return salesInvCodeRule;
    }

    public void setSalesInvCodeRule(String salesInvCodeRule) {
        this.salesInvCodeRule = salesInvCodeRule;
    }

    @Column(name = "sales_refund_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getSalesRefundInvCodeRule() {
        return salesRefundInvCodeRule;
    }

    public void setSalesRefundInvCodeRule(String salesRefundInvCodeRule) {
        this.salesRefundInvCodeRule = salesRefundInvCodeRule;
    }

    @Column(name = "pur_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getPurInvCodeRule() {
        return purInvCodeRule;
    }

    public void setPurInvCodeRule(String purInvCodeRule) {
        this.purInvCodeRule = purInvCodeRule;
    }

    @Column(name = "pur_refund_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getPurRefundInvCodeRule() {
        return purRefundInvCodeRule;
    }

    public void setPurRefundInvCodeRule(String purRefundInvCodeRule) {
        this.purRefundInvCodeRule = purRefundInvCodeRule;
    }

    @Column(name = "spon_contract_code_rule", length = 50)
    @Size(max = 50)
    public String getSponContractCodeRule() {
        return sponContractCodeRule;
    }

    public void setSponContractCodeRule(String sponContractCodeRule) {
        this.sponContractCodeRule = sponContractCodeRule;
    }

    @Column(name = "movement_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getMovementInvCodeRule() {
        return movementInvCodeRule;
    }

    public void setMovementInvCodeRule(String movementInvCodeRule) {
        this.movementInvCodeRule = movementInvCodeRule;
    }

    @Column(name = "export_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getExportInvCodeRule() {
        return exportInvCodeRule;
    }

    public void setExportInvCodeRule(String exportInvCodeRule) {
        this.exportInvCodeRule = exportInvCodeRule;
    }

    @Column(name = "import_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getImportInvCodeRule() {
        return importInvCodeRule;
    }

    public void setImportInvCodeRule(String importInvCodeRule) {
        this.importInvCodeRule = importInvCodeRule;
    }

    @Column(name = "payment_bill_code_rule", length = 50)
    @Size(max = 50)
    public String getPaymentBillCodeRule() {
        return paymentBillCodeRule;
    }

    public void setPaymentBillCodeRule(String paymentBillCodeRule) {
        this.paymentBillCodeRule = paymentBillCodeRule;
    }

    @Column(name = "receipt_code_rule", length = 50)
    @Size(max = 50)
    public String getReceiptsCodeRule() {
        return receiptsCodeRule;
    }

    public void setReceiptsCodeRule(String receiptsCodeRule) {
        this.receiptsCodeRule = receiptsCodeRule;
    }

    @Column(name = "promotion_code_rule", length = 50)
    @Size(max = 50)
    public String getPromotionCodeRule() {
        return promotionCodeRule;
    }

    public void setPromotionCodeRule(String promotionCodeRule) {
        this.promotionCodeRule = promotionCodeRule;
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
}
