/*
 * Store
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
package com.s3s.ssm.entity.store;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Operator;

@Entity
@Table(name = "s_store")
public class Store extends AbstractCodeOLObject implements Serializable {
    private static final long serialVersionUID = 4672724391118673824L;
    private String name;
    private String address;
    private String storedAddress;
    private String importAddress;
    private String exportAddress;
    private Operator manager;

    @Column(name = "store_name", nullable = false, length = 256)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address")
    @NotBlank
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "stored_address")
    @NotBlank
    public String getStoredAddress() {
        return storedAddress;
    }

    public void setStoredAddress(String storedAddress) {
        this.storedAddress = storedAddress;
    }

    @Column(name = "import_address")
    @NotBlank
    public String getImportAddress() {
        return importAddress;
    }

    public void setImportAddress(String importAddress) {
        this.importAddress = importAddress;
    }

    @Column(name = "export_address")
    @NotNull
    public String getExportAddress() {
        return exportAddress;
    }

    public void setExportAddress(String exportAddress) {
        this.exportAddress = exportAddress;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    public Operator getManager() {
        return manager;
    }

    public void setManager(Operator manager) {
        this.manager = manager;
    }

}
