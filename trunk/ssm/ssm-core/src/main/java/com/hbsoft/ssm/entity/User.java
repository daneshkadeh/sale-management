package com.hbsoft.ssm.entity;

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

@Entity
@Table(name = "tbl_auth_user")
public class User extends AbstractBaseIdObject {
	private String username;
	private String password;
	
	private Set<Principal> principals = new HashSet<Principal>();
	
	@Column(name = "username", nullable = false)
    @NotNull
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "password", nullable = false)
    @NotNull
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_auth_user_principal", joinColumns = {@JoinColumn(name="user_id")}, inverseJoinColumns = {@JoinColumn(name="principal_id")})
	public Set<Principal> getPrincipals() {
		return principals;
	}
	
	public void setPrincipals(Set<Principal> principals) {
		this.principals = principals;
	}
}
