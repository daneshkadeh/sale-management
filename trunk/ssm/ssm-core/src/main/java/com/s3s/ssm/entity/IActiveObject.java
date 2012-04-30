package com.s3s.ssm.entity;

/**
 * Interface for active object. An inactive instance should not be used to create new data.
 * 
 * @author phamcongbang
 * 
 */
public interface IActiveObject {
    public Boolean isActive();

    public void setActive(Boolean isActive);
}
