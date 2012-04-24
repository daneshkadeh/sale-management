package com.s3s.ssm.entity.contact;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.config.Address;

/**
 * Link an address to a partner
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "co_partner_address_link")
public class PartnerAddressLink extends AbstractBaseIdObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1362302176719848939L;
    private Partner partner;
    private Address address;
    private Boolean isMain = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", nullable = false)
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "is_main")
    public Boolean isMain() {
        return isMain;
    }

    public void setMain(Boolean isMain) {
        this.isMain = isMain;
    }

}
