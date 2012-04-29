/*
 * Role
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
package com.s3s.ssm.entity.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.acls.sid.Sid;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;

@Entity
@Table(name = "security_role")
public class Role extends AbstractActiveCodeOLObject implements Serializable, GrantedAuthority, Sid {
    private static final long serialVersionUID = 1L;
    private String name;
    private Set<User> users = new HashSet<User>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Column(name = "name", unique = true)
    @Max(value = 250)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @Override
    public String getAuthority() {
        return name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Transient
    @Override
    public int compareTo(Object o) {
        if (o instanceof Role) {
            Role role = (Role) o;
            if (((Role) o).getName().equals(this.name)) {
                return 0;
            } else {
                return 1;
            }
        }
        throw new IllegalArgumentException();
    }

    @Transient
    @Override
    public String toString() {
        return name;
    }
}
