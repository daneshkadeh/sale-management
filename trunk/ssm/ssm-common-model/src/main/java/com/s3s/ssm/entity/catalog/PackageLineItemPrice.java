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
package com.s3s.ssm.entity.catalog;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "s_package_line_item_price")
public class PackageLineItemPrice extends AbstractIdOLObject {
    private Item item;
    private AudienceCategory audienceCategory;
    private Money sellPrice;
    private PackageLine packageLine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "audience_cate_id", nullable = false)
    @NotNull
    public AudienceCategory getAudienceCategory() {
        return audienceCategory;
    }

    public void setAudienceCategory(AudienceCategory partnerCategory) {
        this.audienceCategory = partnerCategory;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "sell_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    @NotNull
    public Money getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Money sellPrice) {
        this.sellPrice = sellPrice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_line_id", nullable = false)
    @NotNull
    public PackageLine getPackageLine() {
        return packageLine;
    }

    public void setPackageLine(PackageLine packageLine) {
        this.packageLine = packageLine;
    }

}
