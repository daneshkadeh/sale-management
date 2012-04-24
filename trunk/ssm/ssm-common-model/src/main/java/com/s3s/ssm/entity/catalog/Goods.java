package com.s3s.ssm.entity.catalog;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.Money;

/**
 * Goods is product from supplier, used by customer: shoes, t-shirt
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "ca_goods")
@PrimaryKeyJoinColumn(name = "goods_id")
public class Goods extends Product {
    private Manufacturer manufacturer;
    private String model;
    private UnitOfMeasure mainUom;
    private Set<ProductProperty> properties = new HashSet<>();
    private Money originPrice; // price of product is overriden by price of item
    private Money baseSellPrice;
    private Long maintainPeriod; // Thoi han bao hanh
    private Integer minNumberOfStoredProduct; // Luong ton toi thieu, tinh theo mainUOM
    private Long mustSoldPeriod; // thoi gian toi thieu phai ban duoc 1 item
    private Integer minNumberSoldInMonth; // So luong ban toi thieu trong 1 thang, tinh theo mainUOM
    private Integer effectiveSoldInMonth; // So luong ban vuot muc

    // private Set<Partner> suppliers; //THU only have 1 supplier for 1 product (is also manufactorer), decide add this
    // field later

    @Column(name = "model", length = 32)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // TODO: move to goods product.
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
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

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "origin_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_origin")) })
    public Money getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Money originPrice) {
        this.originPrice = originPrice;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "base_sell_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_sell")) })
    public Money getBaseSellPrice() {
        return baseSellPrice;
    }

    public void setBaseSellPrice(Money baseSellPrice) {
        this.baseSellPrice = baseSellPrice;
    }

    @Column(name = "maintain_period")
    public Long getMaintainPeriod() {
        return maintainPeriod;
    }

    public void setMaintainPeriod(Long maintainPeriod) {
        this.maintainPeriod = maintainPeriod;
    }

    @Column(name = "min_number_stored_product")
    public Integer getMinNumberOfStoredProduct() {
        return minNumberOfStoredProduct;
    }

    public void setMinNumberOfStoredProduct(Integer minNumberOfStoredProduct) {
        this.minNumberOfStoredProduct = minNumberOfStoredProduct;
    }

    @Column(name = "must_sold_period")
    public Long getMustSoldPeriod() {
        return mustSoldPeriod;
    }

    public void setMustSoldPeriod(Long mustSoldPeriod) {
        this.mustSoldPeriod = mustSoldPeriod;
    }

    @Column(name = "min_number_sold_month")
    public Integer getMinNumberSoldInMonth() {
        return minNumberSoldInMonth;
    }

    public void setMinNumberSoldInMonth(Integer minNumberSoldInMonth) {
        this.minNumberSoldInMonth = minNumberSoldInMonth;
    }

    @Column(name = "effective_sold_month")
    public Integer getEffectiveSoldInMonth() {
        return effectiveSoldInMonth;
    }

    public void setEffectiveSoldInMonth(Integer effectiveSoldInMonth) {
        this.effectiveSoldInMonth = effectiveSoldInMonth;
    }
}
