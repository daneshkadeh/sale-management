/*
 * Stall
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
package com.s3s.ssm.entity.operator;

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

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

/**
 * This entity is Point Of Sales (POS)? 1 sales channel has multi-POS.
 * 
 */
@Entity
@Table(name = "operator_stall")
public class Stall extends AbstractActiveCodeOLObject {
    private String name;
    private Operator manager;
    private Set<Operator> staffs = new HashSet<Operator>();
    private Set<SaleTarget> salesTarget = new HashSet<SaleTarget>();

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id", nullable = false)
    public Operator getManager() {
        return manager;
    }

    public void setManager(Operator manager) {
        this.manager = manager;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "o_stall_user", joinColumns = { @JoinColumn(name = "id_stall") }, inverseJoinColumns = { @JoinColumn(name = "id_user") })
    public
            Set<Operator> getStaffs() {
        return staffs;
    }

    public void setStaffs(Set<Operator> staffs) {
        this.staffs = staffs;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stall", fetch = FetchType.EAGER)
    public Set<SaleTarget> getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(Set<SaleTarget> salesTarget) {
        this.salesTarget = salesTarget;
    }

    public String toString() {
        return name;
    }
}
