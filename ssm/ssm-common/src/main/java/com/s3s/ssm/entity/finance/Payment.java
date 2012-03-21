/*
 * Payment
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
package com.s3s.ssm.entity.finance;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "s_payment")
public class Payment extends AbstractCodeOLObject {
    private static final long serialVersionUID = 1420800624547633129L;
    private PaymentContent paymentContent;
    private Date paymentDate;
    private String partnerCode;
    private String partnerName;
    private String staffCode;
    private String staffName;
    private PaymentMode paymentMode;
    private Money money;
    private Integer rate;

    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_content_id")
    public PaymentContent getPaymentContent() {
        return paymentContent;
    }

    public void setPaymentContent(PaymentContent paymentContent) {
        this.paymentContent = paymentContent;
    }

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    @NotBlank
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Column(name = "partner_code")
    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partner_code) {
        this.partnerCode = partner_code;
    }

    @Column(name = "partner_name")
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partner_name) {
        this.partnerName = partner_name;
    }

    @Column(name = "staff_code")
    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staff_code) {
        this.staffCode = staff_code;
    }

    @Column(name = "staff_name")
    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staff_name) {
        this.staffName = staff_name;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "money")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    @Column(name = "payment_mode")
    @NotBlank
    @Enumerated(EnumType.STRING)
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Column(name = "rate")
    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
