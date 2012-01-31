package com.s3s.ssm.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * An element of a property. Eg. property COLOR has following elements: GREEN, BLUE, RED, YELLOW
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_productproperty_element")
public class ProductPropertyElement extends AbstractIdOLObject {
    private ProductProperty property;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id", nullable = false)
    public ProductProperty getProperty() {
        return property;
    }

    public void setProperty(ProductProperty property) {
        this.property = property;
    }

    @Column(name = "element_value", nullable = false, length = 128)
    @NotNull
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
