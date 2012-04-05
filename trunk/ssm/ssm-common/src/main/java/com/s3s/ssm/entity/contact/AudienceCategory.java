package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

/**
 * AudienceCategory helps to define the price of customers for each item.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "co_audience_category")
public class AudienceCategory extends AbstractCodeOLObject {
    /**
     * 
     */
    private static final long serialVersionUID = 180788890437745181L;
    private String name;

    @Column(name = "name", length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
