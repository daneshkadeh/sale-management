package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_uom_category")
public class UomCategory extends AbstractCodeOLObject {
    private String name;
    private UomCategory parentUomCategory;

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentUomCategory_id")
    public UomCategory getParentUomCategory() {
        return parentUomCategory;
    }

    public void setParentUomCategory(UomCategory parentUomCategory) {
        this.parentUomCategory = parentUomCategory;
    }

    @Override
    public String toString() {
        return name;
    }

}