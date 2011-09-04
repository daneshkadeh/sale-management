package com.hbsoft.ssm.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "tbl_auth_principal")
public class Principal extends AbstractBaseIdObject {
	private String name;
	private String clazz;
	private Set<User> users = new HashSet<User>();
	private Set<Permission> permissions = new HashSet<Permission>();
	@Column(name = "name", nullable = false)
    @NotNull
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "class", nullable = false)
    @NotNull
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tbl_auth_user_principal", joinColumns = {@JoinColumn(name="principal_id")}, inverseJoinColumns = {@JoinColumn(name="user_id")})
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tbl_auth_principal_permission", joinColumns = {@JoinColumn(name="principal_id")}, inverseJoinColumns = {@JoinColumn(name="permission_id")})
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}
