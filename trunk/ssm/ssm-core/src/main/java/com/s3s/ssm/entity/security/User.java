package com.s3s.ssm.entity.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "au_user")
public class User extends AbstractIdOLObject implements Serializable,
		UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	Set<Role> roles = new HashSet<Role>(0);

	private boolean isAccountNonExpired = true;

	private boolean isAccountNonLocked = true;

	private boolean isCredentialsNonExpired = true;

	private boolean isEnabled = true;

	public void setIsAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	@Column(name = "isAccountNonLocked", nullable = true)
	public boolean getIsAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setIsCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	@Column(name = "isCredentialsNonExpired", nullable = true)
	public boolean getIsCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "isEnabled", nullable = true)
	public boolean getIsEnabled() {
		return isEnabled;
	}

	@Column(name = "password", nullable = true, length = 32)
	public String getPassword() {
		return password;
	}

	@Column(name = "username", nullable = false, length = 32)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIsAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	@Column(name = "isAccountNonExpired", nullable = true)
	public boolean getIsAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Transient
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		GrantedAuthority admin = new Role("ADMIN");
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		roles.add(admin);
		return roles;
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

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "au_user_role", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = { @JoinColumn(name = "id_role") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
