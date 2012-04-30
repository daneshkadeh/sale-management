/*
 * ContractPayment
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

package com.s3s.ssm.entity.finance;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.s3s.ssm.entity.sales.Invoice;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "finance_invoice_payment")
@PrimaryKeyJoinColumn(name = "invoice_payment_id")
public class InvoicePayment extends Payment {
    private static final long serialVersionUID = -1695893616913760376L;
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "invoice_id")
    @NotNull
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

}
