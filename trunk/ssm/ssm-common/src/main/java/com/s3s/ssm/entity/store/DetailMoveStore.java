/*
 * DetailExchangeStore
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
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
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.config.UnitOfMeasure;

@Entity
@Table(name = "store_detail_move_store")
public class DetailMoveStore extends AbstractIdOLObject {
    private static final long serialVersionUID = 6554328977277943730L;
    private MoveStoreForm moveForm;
    private Product product;
    private Item item;
    private UnitOfMeasure uom;
    private Integer exportQty;
    private Integer importQty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "move_form_id")
    @NotNull
    public MoveStoreForm getMoveForm() {
        return moveForm;
    }

    public void setMoveForm(MoveStoreForm moveForm) {
        this.moveForm = moveForm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_id")
    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }

    @Column(name = "export_qty")
    public Integer getExportQty() {
        return exportQty;
    }

    public void setExportQty(Integer exportQty) {
        this.exportQty = exportQty;
    }

    @Column(name = "import_qty")
    public Integer getImportQty() {
        return importQty;
    }

    public void setImportQty(Integer importQty) {
        this.importQty = importQty;
    }

}
