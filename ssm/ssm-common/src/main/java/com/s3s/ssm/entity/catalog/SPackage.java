/*
 * SPackage
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "ca_package")
public class SPackage extends AbstractCodeOLObject {
    private String name;
    private Set<PackageLine> packageLines = new HashSet<>();

    @Column(name = "name", length = 128, nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "package")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<PackageLine> getPackageLines() {
        return packageLines;
    }

    public void setPackageLines(Set<PackageLine> packageLines) {
        this.packageLines = packageLines;
    }
}
