package com.s3s.ssm.entity.catalog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * 1 time customer sends the product to be maintained, create 1 detailWarranty.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "ca_detail_warranty")
public class DetailWarranty extends AbstractIdOLObject {
    private static final long serialVersionUID = 1L;
    private WarrantyForm warrantyForm;
    private Date warrantyDate;
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "warranty_form", nullable = false)
    public WarrantyForm getWarrantyForm() {
        return warrantyForm;
    }

    public void setWarrantyForm(WarrantyForm warrantyForm) {
        this.warrantyForm = warrantyForm;
    }

    @Column(name = "warranty_date")
    public Date getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(Date warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    @Column(name = "description", length = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
