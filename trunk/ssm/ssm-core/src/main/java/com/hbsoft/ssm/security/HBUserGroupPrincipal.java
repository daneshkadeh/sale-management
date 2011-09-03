package com.hbsoft.ssm.security;

import java.security.Principal;

public class HBUserGroupPrincipal implements Principal {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HBUserGroupPrincipal(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

   
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (!(obj instanceof HBUserGroupPrincipal)) {
            return false;
        }
        HBUserGroupPrincipal other = (HBUserGroupPrincipal) obj;
        return getName().equals(other.getName()) && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getName().hashCode() * 27 + getId().hashCode() * 27;
    }

}
