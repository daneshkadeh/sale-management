package com.s3s.ssm.entity.store;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;

public class DetailCheckStore extends AbstractIdOLObject {
    private CheckStore checkStore;
    private Item item;
    private Integer changeAmount;
    private CheckStoreChangeType changeType;
    private String remark;

    @ManyToOne
    @JoinColumn(name = "checkstore_id", nullable = false)
    @NotNull
    public CheckStore getCheckStore() {
        return checkStore;
    }

    public void setCheckStore(CheckStore checkStore) {
        this.checkStore = checkStore;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "change_amount", nullable = false)
    @NotNull
    public Integer getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Integer changeAmount) {
        this.changeAmount = changeAmount;
    }

    @Column(name = "change_type", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public CheckStoreChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(CheckStoreChangeType changeType) {
        this.changeType = changeType;
    }

    @Column(name = "remark", length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
