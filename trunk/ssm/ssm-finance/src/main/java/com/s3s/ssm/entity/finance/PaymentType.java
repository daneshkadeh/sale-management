/*
 * PaymentType
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_payment_type")
public class PaymentType extends AbstractCodeOLObject {

    private String name;
    private PaymentContentType contentType;
    private Boolean isReceived;

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "content_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public PaymentContentType getContentType() {
        return contentType;
    }

    public void setContentType(PaymentContentType contentType) {
        this.contentType = contentType;
    }

    @Column(name = "is_received", nullable = false)
    @NotNull
    public Boolean getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Boolean isReceived) {
        this.isReceived = isReceived;
    }

}
