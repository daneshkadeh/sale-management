package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_product_type")
public class ProductType extends AbstractCodeOLObject {
    private ProductFamilyType productFamilyType;
    private String name;

    @Column(name = "product_family_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public ProductFamilyType getProductFamilyType() {
        return productFamilyType;
    }

    public void setProductFamilyType(ProductFamilyType productFamilyType) {
        this.productFamilyType = productFamilyType;
    }

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
