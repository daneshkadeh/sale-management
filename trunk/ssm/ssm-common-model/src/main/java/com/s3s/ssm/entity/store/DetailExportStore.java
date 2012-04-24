/*
 * DetailExportStore
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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.config.UnitOfMeasure;

@Entity
@Table(name = "store_detail_export")
public class DetailExportStore extends AbstractIdOLObject {
    private static final long serialVersionUID = 6567804203919738639L;
    private Integer lineNo;
    private ExportStoreForm exportForm;
    private Product product = new Product();
    private String productName;
    private Item item = new Item();
    private UnitOfMeasure uom = new UnitOfMeasure();
    private UnitOfMeasure baseUom = new UnitOfMeasure();
    private Integer reqQuan = 0;
    private Integer realQuan = 0;
    private Integer remainQuan = 0;

    @Column(name = "line_no")
    @DecimalMin(value = "1")
    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @NotNull
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "product_name")
    @NotBlank
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "export_form_id")
    @NotNull
    public ExportStoreForm getExportForm() {
        return exportForm;
    }

    public void setExportForm(ExportStoreForm exportForm) {
        this.exportForm = exportForm;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_id")
    @NotNull
    public UnitOfMeasure getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasure uom) {
        this.uom = uom;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "base_uom_id")
    @NotNull
    public UnitOfMeasure getBaseUom() {
        return baseUom;
    }

    public void setBaseUom(UnitOfMeasure baseUom) {
        this.baseUom = baseUom;
    }

    @Column(name = "req_quan")
    @DecimalMin(value = "0")
    public Integer getReqQuan() {
        return reqQuan;
    }

    public void setReqQuan(Integer reqQuan) {
        this.reqQuan = reqQuan;
    }

    @Column(name = "real_quan")
    @DecimalMin(value = "0")
    public Integer getRealQuan() {
        return realQuan;
    }

    public void setRealQuan(Integer realQuan) {
        this.realQuan = realQuan;
        updateRemainQuan();
    }

    @Column(name = "remain_quan")
    @DecimalMin(value = "0")
    public Integer getRemainQuan() {
        return remainQuan;
    }

    public void setRemainQuan(Integer remainQuan) {
        this.remainQuan = remainQuan;
    }

    private void updateRemainQuan() {
        remainQuan = reqQuan - realQuan;
    }
}
