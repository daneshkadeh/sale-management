package com.s3s.ssm.security;

import java.security.Principal;

public abstract class BasePrincipal implements Principal, Comparable {
	private String name;
	
	public BasePrincipal(String name) {
		if (name == null) {
			throw new NullPointerException("Name may not be null");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object obj) {
		 BasePrincipal other = (BasePrincipal) obj; 
		 int classComp = getClass().getName().compareTo(other.getClass().getName()); 
		 if (classComp == 0) { 
			 return getName().compareTo(other.getName()); 
		 } else { 
			 return classComp; 
		 }
	}
	
	@Override
	public boolean equals(Object obj) {
		 if (this == obj) { 
		      return true; 
		    } 
		 
		    if (!getClass().equals(obj.getClass())) { 
		      return false; 
		    } 
		 
		    BasePrincipal other = (BasePrincipal) obj; 
		 
		    if (!getName().equals(other.getName())) { 
		      return false; 
		    } 
		 
		    return true;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode() * 19 + getClass().hashCode() * 19;
	}
	
	@Override
	public String toString() {
		 StringBuffer buf = new StringBuffer(); 
		 buf.append("("); 
		 buf.append(getClass().getName()); 
		 buf.append(": name="); 
		 buf.append(getName());
		 buf.append(")"); 
		 return buf.toString(); 
	}

}