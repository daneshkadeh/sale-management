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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * When THU requests a set of products, supplier will send a sales confirm about the quantity of products which they can
 * provide, and how many sent sales contracts.
 * 
 * @author phamcongbang
 * 
 */
// TODO: Mistake of spec team, we does not ask about the form of sales confirm. How many attributes there are in a
// salesConfirm.
@Entity
@Table(name = "s_sales_confirm")
public class SalesConfirm extends AbstractCodeOLObject {

    /**
     * 
     */
    private static final long serialVersionUID = -7669728977211958558L;

    private Partner supplier;

    // TODO: we should shown SalesConfirm in a red line if it's not done, in a green life if it has finished.
    // expected quantity of sales contract. Status of SalesConfirm is based on salesContracts.
    private Integer expectedQtySC = 0;
    private Date createdDate;
    private String description;
    private SalesConfirmStatus status = SalesConfirmStatus.OPEN;
    private Set<SalesContract> salesContracts = new HashSet<SalesContract>();

    /**
     * OPEN: when salesConfirm is sent from supplier. </br> PROCESSING: when salesContracts are created. </br> CLOSED:
     * when all salesContract are closed. </br> CANCELLED: The salesConfirm is cancelled, no product is import.
     * 
     * @author phamcongbang
     * 
     */
    public enum SalesConfirmStatus {
        OPEN, PROCESSING, CLOSED, CANCELLED;
        @Override
        public String toString() {
            return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
        }
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false)
    public Partner getSupplier() {
        return supplier;
    }

    public void setSupplier(Partner supplier) {
        this.supplier = supplier;
    }

    @Column(name = "expected_qty_sc", nullable = false)
    @NotNull
    public Integer getExpectedQtySC() {
        return expectedQtySC;
    }

    public void setExpectedQtySC(Integer expectedQtySC) {
        this.expectedQtySC = expectedQtySC;
    }

    @Column(name = "created_date", nullable = false)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "salesConfirm")
    public Set<SalesContract> getSalesContracts() {
        return salesContracts;
    }

    public void setSalesContracts(Set<SalesContract> salesContracts) {
        this.salesContracts = salesContracts;
    }

    @Column(name = "description", length = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public SalesConfirmStatus getStatus() {
        return status;
    }

    public void setStatus(SalesConfirmStatus status) {
        this.status = status;
    }

}
