package com.s3s.ssm.entity.contact;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Profile of a supportee. (Nguoi nhan tai tro)
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "co_supportee_profile")
@PrimaryKeyJoinColumn(name = "profile_id")
public class SupporteeProfile extends PartnerProfile {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SupporteeProfile() {
        this.setType(PartnerProfileTypeEnum.SUPPORTEE);
    }

}
