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
@Table(name = "s_detail_export_store")
public class DetailExportStore extends AbstractIdOLObject {
    private ExportStoreForm exportStoreForm;
    private Item item;
    private Integer amount;
    private DetailExportStoreStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exportstore_id", nullable = false)
    @NotNull
    public ExportStoreForm getExportStoreForm() {
        return exportStoreForm;
    }

    public void setExportStoreForm(ExportStoreForm exportStoreForm) {
        this.exportStoreForm = exportStoreForm;
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
