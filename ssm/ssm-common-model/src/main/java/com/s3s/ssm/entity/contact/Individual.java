package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * Individual stores information of 1 person
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "co_individual")
public class Individual extends AbstractIdOLObject {
    /**
     * 
     */
    private static final long serialVersionUID = 8236821023203647503L;
    private IndividualTitleEnum title;
    private String firstName;
    private String lastName;
    private String fullName; // calculated from firstName and lastName;
    private Partner partner;

    private String position; // position in the partner
    private IndividualRoleEnum role;

    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.STRING)
    public IndividualTitleEnum getTitle() {
        return title;
    }

    public void setTitle(IndividualTitleEnum title) {
        this.title = title;
    }

    @Column(name = "first_name", length = 128, nullable = false)
    @NotBlank
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 128, nullable = false)
    @NotBlank
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "full_name", length = 256, nullable = false)
    @NotBlank
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", nullable = false)
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Column(name = "profile", length = 128)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "role")
    @NotNull
    @Enumerated(EnumType.STRING)
    public IndividualRoleEnum getRole() {
        return role;
    }

    public void setRole(IndividualRoleEnum role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
