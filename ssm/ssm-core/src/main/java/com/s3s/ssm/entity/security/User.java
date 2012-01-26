package com.s3s.ssm.entity.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "au_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AbstractCodeOLObject implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    Set<Role> roles = new HashSet<Role>();

    private Boolean isAccountNonExpired = true;

    private Boolean isAccountNonLocked = true;

    private Boolean isCredentialsNonExpired = true;

    private Boolean isEnabled = true;

    public void setIsAccountNonLocked(Boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    @Column(name = "isAccountNonLocked", nullable = true)
    public Boolean getIsAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    @Column(name = "isCredentialsNonExpired", nullable = true)
    public Boolean getIsCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Column(name = "isEnabled", nullable = true)
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    @Column(name = "password", nullable = true, length = 32)
    public String getPassword() {
        return password;
    }

    @Column(name = "username", nullable = false, length = 32, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAccountNonExpired(Boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    @Column(name = "isAccountNonExpired", nullable = true)
    public Boolean getIsAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return isAccountNonExpired;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return isAccountNonLocked;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return isCredentialsNonExpired;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "au_user_role", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = { @JoinColumn(name = "id_role") })
    public
            Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    @Override
    public GrantedAuthority[] getAuthorities() {
        return (Role[]) roles.toArray(new Role[roles.size()]);
    }

    @Transient
    @Override
    public String toString() {
        return username;
    }

}
