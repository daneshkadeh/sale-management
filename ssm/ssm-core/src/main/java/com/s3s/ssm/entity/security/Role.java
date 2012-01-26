package com.s3s.ssm.entity.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.acls.sid.Sid;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "au_role")
public class Role extends AbstractCodeOLObject implements Serializable, GrantedAuthority, Sid {
    private static final long serialVersionUID = 1L;
    private String name;
    private Boolean isEnable = true;
    private Set<User> users = new HashSet<User>();

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    @Column(name = "name", nullable = false, length = 32, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "isEnable")
    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
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
