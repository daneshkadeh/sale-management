package com.s3s.ssm.entity.param;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.sales.ItemPrice;

@Entity
@Table(name = "s_item")
public class Item extends AbstractIdOLObject {
    private Product product;
    private String sumUomName;
    private Double baseSellPrice;
    private CurrencyEnum currency;
    private List<UnitOfMeasure> listUom = new ArrayList<>();
    private Set<ItemPrice> listItemPrices = new HashSet<>();

    // @ManyToOne
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "sum_uom_name", length = 128)
    public String getSumUomName() {
        return sumUomName;
    }

    public void setSumUomName(String sumUomName) {
        this.sumUomName = sumUomName;
    }

    @Column(name = "base_sell_price", nullable = false)
    @NotNull
    public Double getBaseSellPrice() {
        return baseSellPrice;
    }

    public void setBaseSellPrice(Double baseSellPrice) {
        this.baseSellPrice = baseSellPrice;
    }

    @Column(name = "currency", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    // @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_item_uom", joinColumns = { @JoinColumn(name = "item_id") }, inverseJoinColumns = { @JoinColumn(name = "uom_id") })
    public
            List<UnitOfMeasure> getListUom() {
        return listUom;
    }

    public void setListUom(List<UnitOfMeasure> listUom) {
        this.listUom = listUom;
    }

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "item")
    public Set<ItemPrice> getListItemPrices() {
        return listItemPrices;
    }

    public void setListItemPrices(Set<ItemPrice> listItemPrices) {
        this.listItemPrices = listItemPrices;
    }

    public void addItemPrice(ItemPrice itemPrice) {
        itemPrice.setItem(this);
        listItemPrices.add(itemPrice);
    }

}
