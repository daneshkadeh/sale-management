/*
 * PackageLine
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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "ca_package_line")
public class PackageLine extends AbstractIdOLObject {
    private SPackage pack;
    private PackageLine parentPackageLine; // will be implement later
    private Product product;
    private Boolean isAllItem = false;
    private Set<Item> explicitLinkItems = new HashSet<>();
    private Boolean optional = false;
    private Integer minItemAmount = 1;
    private Integer maxItemAmount = 1;
    // PackageLineItemPrice is applied for this packageLine, use default ItemPrice to sell if not exist in this list
    private Set<PackageLineItemPrice> itemPrices = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id", nullable = false)
    @NotNull
    public SPackage getPackage() {
        return pack;
    }

    public void setPackage(SPackage pack) {
        this.pack = pack;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentpackline_id")
    public PackageLine getParentPackageLine() {
        return parentPackageLine;
    }

    public void setParentPackageLine(PackageLine parentPackageLine) {
        this.parentPackageLine = parentPackageLine;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_package_line_item", joinColumns = { @JoinColumn(name = "packline_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
    public
            Set<Item> getExplicitLinkItems() {
        return explicitLinkItems;
    }

    public void setExplicitLinkItems(Set<Item> explicitLinkItems) {
        this.explicitLinkItems = explicitLinkItems;
    }

    @Column(name = "optional", nullable = false)
    @NotNull
    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    @Column(name = "min_item_amount", nullable = false)
    @NotNull
    public Integer getMinItemAmount() {
        return minItemAmount;
    }

    public void setMinItemAmount(Integer minItemAmount) {
        this.minItemAmount = minItemAmount;
    }

    @Column(name = "max_item_amount", nullable = false)
    @NotNull
    public Integer getMaxItemAmount() {
        return maxItemAmount;
    }

    public void setMaxItemAmount(Integer maxItemAmount) {
        this.maxItemAmount = maxItemAmount;
    }

    @Column(name = "is_all_item", nullable = false)
    @NotNull
    public Boolean getIsAllItem() {
        return isAllItem;
    }

    public void setIsAllItem(Boolean linkAllItem) {
        this.isAllItem = linkAllItem;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "packageLine")
    public Set<PackageLineItemPrice> getItemPrices() {
        return itemPrices;
    }

    public void setItemPrices(Set<PackageLineItemPrice> itemPrices) {
        this.itemPrices = itemPrices;
    }

    public void addItemPrice(PackageLineItemPrice itemPrice) {
        itemPrice.setPackageLine(this);
        itemPrices.add(itemPrice);
    }

}
