package com.s3s.ssm.entity;

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
import javax.validation.constraints.NotNull;

import com.s3s.ssm.security.BasePrincipal;

@Entity
@Table(name = "tbl_auth_principal")
public class PrincipalEntity extends AbstractBaseIdObject {
    private String name;
    private String clazz;
    private Set<User> users = new HashSet<User>();
    private Set<PermissionEntity> permissions = new HashSet<PermissionEntity>();

    public PrincipalEntity(BasePrincipal basePrincipal) {
        this.name = basePrincipal.getName();
        this.clazz = basePrincipal.getClass().getName();
    }

    @Column(name = "name", nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrincipalEntity() {
        super();
    }

    @Column(name = "class", nullable = false)
    @NotNull
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_auth_user_principal", joinColumns = { @JoinColumn(name = "principal_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    public
            Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_auth_principal_permission", joinColumns = { @JoinColumn(name = "principal_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
    public
            Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
