/*
 * Product
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

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.config.UploadFile;

@Entity
@Table(name = "ca_product")
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, insertable = false, updatable = false))
public class Product extends AbstractCodeOLObject {
    private static final long serialVersionUID = 242255088169346711L;
    private String name;
    // TODO: we should have a method to get and set id directly (with AOP approach)
    // private Long manufacturerId;

    private ProductType type;

    private String description;

    private UploadFile uploadFile;

    private Set<Item> listItems = new HashSet<>();

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 128)
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "producttype_id", nullable = false)
    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "uploadfile_id")
    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    // Can not use FetchType.EAGER. Refer to
    // http://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Item> getListItems() {
        return listItems;
    }

    public void setListItems(Set<Item> listItems) {
        this.listItems = listItems;
    }

}
