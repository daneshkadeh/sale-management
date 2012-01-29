/*
 * PartnerCategory
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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;

/**
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "s_partner_category")
public class PartnerCategory extends AbstractCodeOLObject implements Serializable {
    private String name; // category name
    private PartnerCategory parentCategory;// Parent Category
    private Boolean isActive = true;
    private Set<PartnerCategory> subPartnerCates = new HashSet<PartnerCategory>();

    // private List<Partner> partnerSet = new ArrayList<Partner>();

    @Column(name = "name", length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    public PartnerCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(PartnerCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Column(name = "is_active", nullable = false)
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // @ManyToMany(mappedBy = "partnerCateSet")
    // public List<Partner> getPartnerSet() {
    // return partnerSet;
    // }
    //
    // public void setPartnerSet(List<Partner> partnerSet) {
    // this.partnerSet = partnerSet;
    // }

    @OneToMany(mappedBy = "parentCategory")
    public Set<PartnerCategory> getSubPartnerCates() {
        return subPartnerCates;
    }

    public void setSubPartnerCates(Set<PartnerCategory> subPartnerCates) {
        this.subPartnerCates = subPartnerCates;
    }

    @Override
    public String toString() {
        String fullname = name;
        // get all name of previous parents
        PartnerCategory parent = parentCategory;
        while (parent != null) {
            fullname = parent.name + " / " + fullname;
            parent = parent.parentCategory;
        }
        return fullname;
    }
}
