/*
 * Advantage
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
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "ca_advantage")
public class Advantage extends AbstractCodeOLObject {
    private String name;
    private Integer discountPercent = 0;
    private Set<SPackage> listBuyPackage = new HashSet<>();
    private Set<Item> listBuyItem = new HashSet<>();
    private Set<SPackage> listGiftPackage = new HashSet<>();
    private Set<Item> listGiftItem = new HashSet<>();

    @Column(name = "name", length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "discount_percent")
    @NotNull
    @Max(100)
    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_advantage_buypackage", joinColumns = { @JoinColumn(name = "advantage_id") }, inverseJoinColumns = { @JoinColumn(name = "package_id") })
    public
            Set<SPackage> getListBuyPackage() {
        return listBuyPackage;
    }

    public void setListBuyPackage(Set<SPackage> listBuyPackage) {
        this.listBuyPackage = listBuyPackage;
    }

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_advantage_buyitem", joinColumns = { @JoinColumn(name = "advantage_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
    public
            Set<Item> getListBuyItem() {
        return listBuyItem;
    }

    public void setListBuyItem(Set<Item> listBuyItem) {
        this.listBuyItem = listBuyItem;
    }

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_advantage_giftpackage", joinColumns = { @JoinColumn(name = "advantage_id") }, inverseJoinColumns = { @JoinColumn(name = "package_id") })
    public
            Set<SPackage> getListGiftPackage() {
        return listGiftPackage;
    }

    public void setListGiftPackage(Set<SPackage> listGiftPackage) {
        this.listGiftPackage = listGiftPackage;
    }

    // TODO: do not cascadeType.ALL for this attributes because all items will be deleted
    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_advantage_giftitem", joinColumns = { @JoinColumn(name = "advantage_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
    public
            Set<Item> getListGiftItem() {
        return listGiftItem;
    }

    public void setListGiftItem(Set<Item> listGiftItem) {
        this.listGiftItem = listGiftItem;
    }
}
