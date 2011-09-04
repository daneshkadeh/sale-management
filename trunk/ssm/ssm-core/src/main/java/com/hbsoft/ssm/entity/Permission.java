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
@Table(name = "tbl_auth_permission")
public class Permission extends AbstractBaseIdObject {
	private String name;
	private String permissionClass;
	private String actions;
	
	private Set<Principal> principals = new HashSet<Principal>();

	@Column(name = "name", nullable = false)
    @NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "permissionClass", nullable = false)
    @NotNull
	public String getPermissionClass() {
		return permissionClass;
	}

	public void setPermissionClass(String permissionClass) {
		this.permissionClass = permissionClass;
	}

	@Column(name = "actions", nullable = false)
    @NotNull
	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tbl_auth_principal_permission", joinColumns = {@JoinColumn(name="permission_id")}, inverseJoinColumns = {@JoinColumn(name="principal_id")})
	public Set<Principal> getPrincipals() {
		return principals;
	}

	public void setPrincipals(Set<Principal> principals) {
		this.principals = principals;
	}
}
