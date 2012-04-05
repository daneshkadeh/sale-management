package com.s3s.ssm.entity.contact;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Profile of a supplier.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "co_supplier_profile")
@PrimaryKeyJoinColumn(name = "profile_id")
public class SupplierProfile extends PartnerProfile {
    /**
     * 
     */
    private static final long serialVersionUID = 9203377259650899258L;

    public SupplierProfile() {
        this.setType(PartnerProfileTypeEnum.SUPPLIER);
    }
}
