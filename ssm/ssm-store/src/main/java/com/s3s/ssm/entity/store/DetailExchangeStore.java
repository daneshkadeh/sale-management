package com.s3s.ssm.entity.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;

@Entity
@Table(name = "s_detail_exchange_store")
public class DetailExchangeStore extends AbstractIdOLObject {
    private ExchangeStoreForm exchangeStoreForm;
    private Item item;
    private Integer amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exchangestore_id", nullable = false)
    @NotNull
    public ExchangeStoreForm getExchangeStoreForm() {
        return exchangeStoreForm;
    }

    public void setExchangeStoreForm(ExchangeStoreForm exchangeStoreForm) {
        this.exchangeStoreForm = exchangeStoreForm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "amount", nullable = false)
    @NotNull
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
