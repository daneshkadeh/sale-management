package com.s3s.ssm.entity.contact;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * PartnerProfile shows that a partner is a customer, supplier, supportee, employee
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "co_partner_profile")
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, insertable = false, updatable = false))
public class PartnerProfile extends AbstractIdOLObject {
    /**
     * 
     */
    private static final long serialVersionUID = -7134266372616932852L;
    private Partner partner;
    private PartnerProfileTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Column(name = "profile_type")
    @NotBlank
    @Enumerated(EnumType.STRING)
    public PartnerProfileTypeEnum getType() {
        return type;
    }

    public void setType(PartnerProfileTypeEnum type) {
        this.type = type;
    }

}
