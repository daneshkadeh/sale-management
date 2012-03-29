package com.s3s.ssm.entity.catalog;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.s3s.ssm.entity.config.UnitOfMeasure;

@Entity
@Table(name = "s_goods")
@PrimaryKeyJoinColumn(name = "goods_id")
public class Goods extends Product {
    private Manufacturer manufacturer;
    private String model;
    private UnitOfMeasure mainUom;
    private Set<ProductProperty> properties = new HashSet<>();
    private Long alertQuantity;

    @Column(name = "alert_quantity")
    public Long getAlertQuantity() {
        return alertQuantity;
    }

    public void setAlertQuantity(Long alertQuantity) {
        this.alertQuantity = alertQuantity;
    }

    @Column(name = "model", length = 32)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // TODO: move to goods product.
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @ManyToOne
    @JoinColumn(name = "main_uom_id", nullable = false)
    public UnitOfMeasure getMainUom() {
        return mainUom;
    }

    public void setMainUom(UnitOfMeasure mainUom) {
        this.mainUom = mainUom;
    }

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_product_property", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "property_id") })
    public
            Set<ProductProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<ProductProperty> properties) {
        this.properties = properties;
    }

    public void addProperty(ProductProperty property) {
        properties.add(property);
    }
}
