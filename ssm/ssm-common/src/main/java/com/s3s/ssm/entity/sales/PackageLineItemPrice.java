/*
 * PackageLineItemPrice
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
package com.s3s.ssm.entity.sales;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.contact.PartnerCategory;

@Entity
@Table(name = "s_package_line_item_price")
public class PackageLineItemPrice extends AbstractIdOLObject {
    private PackageLine packageLine;
    private PartnerCategory partnerCategory;
    private Double sellItemPrice;
    private String currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_line_id", nullable = false)
    @NotNull
    public PackageLine getPackageLine() {
        return packageLine;
    }

    public void setPackageLine(PackageLine packageLine) {
        this.packageLine = packageLine;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_type_id", nullable = false)
    @NotNull
    public PartnerCategory getPartnerCategory() {
        return partnerCategory;
    }

    public void setPartnerCategory(PartnerCategory contactType) {
        this.partnerCategory = contactType;
    }

    @Column(name = "sell_price", nullable = false)
    @NotNull
    public Double getSellItemPrice() {
        return sellItemPrice;
    }

    public void setSellItemPrice(Double sellItemPrice) {
        this.sellItemPrice = sellItemPrice;
    }

    @Column(name = "currency", nullable = false)
    @NotNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
