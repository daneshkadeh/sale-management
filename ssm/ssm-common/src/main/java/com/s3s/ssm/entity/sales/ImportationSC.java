package com.s3s.ssm.entity.sales;

import java.util.Date;
import java.util.HashSet;
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

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.store.ImportStoreForm;

/**
 * 1 sales contract has multi importationSCs. Each shipment of supplier will create an importationSC at THU.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_importation_sc")
public class ImportationSC extends AbstractCodeOLObject {

    /**
     * 
     */
    private static final long serialVersionUID = 178403100072465342L;
    private SalesContract salesContract;
    private Date createdDate;
    private Date shipmentDate;
    private ImportationSCStatus status = ImportationSCStatus.OPEN;
    private Set<ImportStoreForm> importStoreForms = new HashSet<>();

    private Set<AddedSCFee> addedSCFees = new HashSet<>();

    public enum ImportationSCStatus {
        OPEN, RECEIVED, CLOSED, CANCELLED
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salescon_id", nullable = false)
    @NotNull
    public SalesContract getSalesContract() {
        return salesContract;
    }

    public void setSalesContract(SalesContract salesContract) {
        this.salesContract = salesContract;
    }

    @Column(name = "created_date")
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "shipment_date")
    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public ImportationSCStatus getStatus() {
        return status;
    }

    public void setStatus(ImportationSCStatus status) {
        this.status = status;
    }

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_importationsc_importstore", joinColumns = { @JoinColumn(name = "import_sc_id") }, inverseJoinColumns = { @JoinColumn(name = "import_store_id") })
    public
            Set<ImportStoreForm> getImportStoreForms() {
        return importStoreForms;
    }

    public void setImportStoreForms(Set<ImportStoreForm> importStoreForms) {
        this.importStoreForms = importStoreForms;
    }

    @OneToMany(mappedBy = "importationSC")
    public Set<AddedSCFee> getAddedSCFees() {
        return addedSCFees;
    }

    public void setAddedSCFees(Set<AddedSCFee> addedSCFees) {
        this.addedSCFees = addedSCFees;
    }

    public void addAddedSCFee(AddedSCFee addedSCFee) {
        addedSCFee.setImportationSC(this);
        addedSCFees.add(addedSCFee);
    }

}
