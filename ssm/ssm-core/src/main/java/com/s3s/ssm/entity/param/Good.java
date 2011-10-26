package com.s3s.ssm.entity.param;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "s_good")
public class Good extends AbstractIdOLObject {
    private Item item;
    private String barcode;
    private Date firstMaintainDate;
    private Date secondMaintainDate;
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "barcode", length = 32)
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "first_maintain_date")
    public Date getFirstMaintainDate() {
        return firstMaintainDate;
    }

    public void setFirstMaintainDate(Date firstMaintainDate) {
        this.firstMaintainDate = firstMaintainDate;
    }

    @Column(name = "second_maintain_date")
    public Date getSecondMaintainDate() {
        return secondMaintainDate;
    }

    public void setSecondMaintainDate(Date secondMaintainDate) {
        this.secondMaintainDate = secondMaintainDate;
    }

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
