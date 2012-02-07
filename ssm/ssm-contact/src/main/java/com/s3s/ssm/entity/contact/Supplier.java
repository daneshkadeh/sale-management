/*
 * Supplier
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
package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "s_supplier")
@PrimaryKeyJoinColumn(name = "supplier_id")
public class Supplier extends Partner {
    private static final long serialVersionUID = 4797277568461280316L;
    private String representer;
    private Boolean sex;
    private String position;
    private String address;
    private String phone;
    private String fax;
    private String email;

    @Column(name = "representer", length = 256)
    public String getRepresenter() {
        return representer;
    }

    public void setRepresenter(String representer) {
        this.representer = representer;
    }

    @Column(name = "sex", length = 1)
    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Column(name = "position", length = 256)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "address", length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone", length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "fax", length = 20)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "email")
    @Email(message = "{Supplier.email.invalid}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
