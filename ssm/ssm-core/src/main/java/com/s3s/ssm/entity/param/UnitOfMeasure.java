package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_unit_of_measure")
public class UnitOfMeasure extends AbstractCodeOLObject {
    private UomCategory uomCategory;
    private String name;
    private Boolean isBaseMeasure = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_category_id", nullable = false)
    public UomCategory getUomCategory() {
        return uomCategory;
    }

    public void setUomCategory(UomCategory uomCategory) {
        this.uomCategory = uomCategory;
    }

    @Column(name = "uom_name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_base_measure", nullable = false)
    @NotNull
    public Boolean getIsBaseMeasure() {
        return isBaseMeasure;
    }

    public void setIsBaseMeasure(Boolean isBaseMeasure) {
        this.isBaseMeasure = isBaseMeasure;
    }
}
