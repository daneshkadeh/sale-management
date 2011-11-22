package com.s3s.ssm.entity.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "au_role")
public class Role extends AbstractIdOLObject implements Serializable,
		GrantedAuthority {
	private static final long serialVersionUID = 1L;
	private String name;
	private Boolean isEnable = true;

	public Role(String name) {
		super();
		this.name = name;
	}

	@Column(name = "name", nullable = false, length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name = "isEnable")
	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Transient
	@Override
	public String getAuthority() {
		return name;
	}

}
