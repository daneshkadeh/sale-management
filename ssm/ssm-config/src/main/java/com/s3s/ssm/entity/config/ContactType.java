package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_contact_type")
public class ContactType extends AbstractCodeOLObject {
    private String description;
    private ContactFamilyType contactFamilyType;

    @Column(name = "description", nullable = false, length = 128)
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "contact_family_type", nullable = false, length = 32)
    @NotNull
    @Enumerated(EnumType.STRING)
    public ContactFamilyType getContactFamilyType() {
        return contactFamilyType;
    }

    public void setContactFamilyType(ContactFamilyType contactFamilyType) {
        this.contactFamilyType = contactFamilyType;
    }

}
