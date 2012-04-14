/*
 * ExchangeStoreForm
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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.shipment.TransportationType;

@Entity
@Table(name = "store_move_store_form")
public class MoveStoreForm extends AbstractCodeOLObject {
    private static final long serialVersionUID = -7806755594701480610L;
    private MoveStoreOrder moveStoreOrder;
    private Date createdDate = new Date();
    private Date receivedDate = new Date();
    private Date sentDate = new Date();
    private String fromStoreman;
    private String destStoreman;
    private String fromAddress;
    private String destAddress;
    private TransportationType transType;
    private Operator staff;
    private String transporter;
    private Integer exportQtyTotal = 0;
    private Integer importQtyTotal = 0;
    private MoveStoreStatus status = MoveStoreStatus.NEW;
    private Set<DetailMoveStore> detailSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "move_store_order_id")
    @NotNull
    public MoveStoreOrder getMoveStoreOrder() {
        return moveStoreOrder;
    }

    public void setMoveStoreOrder(MoveStoreOrder moveStoreOrder) {
        this.moveStoreOrder = moveStoreOrder;
    }

    @Column(name = "from_address")
    @NotBlank
    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    @Column(name = "dest_address")
    @NotBlank
    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trans_type_id")
    public TransportationType getTransType() {
        return transType;
    }

    public void setTransType(TransportationType transType) {
        this.transType = transType;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    public Operator getStaff() {
        return staff;
    }

    public void setStaff(Operator staff) {
        this.staff = staff;
    }

    @Column(name = "transporter")
    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    @Column(name = "export_qty_total")
    public Integer getExportQtyTotal() {
        return exportQtyTotal;
    }

    public void setExportQtyTotal(Integer exportQtyTotal) {
        this.exportQtyTotal = exportQtyTotal;
    }

    @Column(name = "import_qty_total")
    public Integer getImportQtyTotal() {
        return importQtyTotal;
    }

    public void setImportQtyTotal(Integer importQtyTotal) {
        this.importQtyTotal = importQtyTotal;
    }

    @Column(name = "from_storeman")
    public String getFromStoreman() {
        return fromStoreman;
    }

    public void setFromStoreman(String fromStoreman) {
        this.fromStoreman = fromStoreman;
    }

    @Column(name = "dest_storeman")
    public String getDestStoreman() {
        return destStoreman;
    }

    public void setDestStoreman(String destStoreman) {
        this.destStoreman = destStoreman;
    }

    @Column(name = "created_date", nullable = false)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "sent_date")
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Column(name = "received_date")
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public MoveStoreStatus getStatus() {
        return status;
    }

    public void setStatus(MoveStoreStatus status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "moveForm", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
    public Set<DetailMoveStore> getDetailSet() {
        return detailSet;
    }

    public void setDetailSet(Set<DetailMoveStore> detailSet) {
        this.detailSet = detailSet;
    }

}
