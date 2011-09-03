package com.hbsoft.ssm.security;

import java.security.Principal;

public class SalesPrincipal implements Principal {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SalesPrincipal(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (!(obj instanceof SalesPrincipal)) {
            return false;
        }
        SalesPrincipal other = (SalesPrincipal) obj;
        return getName().equals(other.getName()) && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getName().hashCode() * 27 + getId().hashCode() * 27;
    }
}
