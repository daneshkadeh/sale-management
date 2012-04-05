package com.s3s.ssm.entity.contact;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Profile of customer. Can be used to define price of items.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_customer_profile")
@PrimaryKeyJoinColumn(name = "profile_id")
public class CustomerProfile extends PartnerProfile {
    /**
     * 
     */
    private static final long serialVersionUID = 7826247385363131306L;
    private Set<AudienceCategory> audienceCates = new HashSet<>();

    public CustomerProfile() {
        this.setType(PartnerProfileTypeEnum.CUSTOMER);
    }

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(name = "at_partnerprofile_audienceCate", joinColumns = { @JoinColumn(name = "profile_id") }, inverseJoinColumns = { @JoinColumn(name = "audience_cate_id") })
    public
            Set<AudienceCategory> getAudienceCates() {
        return audienceCates;
    }

    public void setAudienceCates(Set<AudienceCategory> audienceCates) {
        this.audienceCates = audienceCates;
    }
}
