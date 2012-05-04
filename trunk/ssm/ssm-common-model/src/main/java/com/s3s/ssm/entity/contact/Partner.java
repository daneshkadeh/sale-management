/*
 * Partner
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.entity.contact;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.model.Money;

/**
 * A partner represents all the entities that you can do business with
 * 
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "co_partner")
@Inheritance(strategy = InheritanceType.JOINED)
public class Partner extends AbstractActiveCodeOLObject {
    private static final long serialVersionUID = 1435468012252943876L;
    private String name;
    private Integer title; // Partner Form
    private String comment;// Notes
    private String website;
    private String phone;
    private String fax;
    private String email;
    private Money debitLimit;// Payable Limit
    private UnitOfMeasure debitTimeUnit;// the unit of debit limit. Ex: date, month, year
    private BankAccount bankAccount;
    private Set<Individual> individuals = new HashSet<>();
    private Set<PartnerCategory> partnerCateSet = new HashSet<PartnerCategory>();

    private ContactDebt contactDebt;
    private Set<ContactDebtHistory> contactDebtHistories = new HashSet<ContactDebtHistory>();

    private Set<PartnerAddressLink> listAddressLinks = new HashSet<>();

    private Set<PartnerProfile> listProfiles = new HashSet<>();

    public Partner() {
        // always set mainIndividual and mainAddressLink into a partner
        Individual mainIndividual = new Individual();
        mainIndividual.setRole(IndividualRoleEnum.MAIN);
        mainIndividual.setPartner(this);
        individuals.add(mainIndividual);

        PartnerAddressLink mainAddressLink = new PartnerAddressLink();
        mainAddressLink.setMain(true);
        mainAddressLink.setPartner(this);
        listAddressLinks.add(mainAddressLink);
        mainAddressLink.setAddress(new Address());

        contactDebt = new ContactDebt();
        contactDebt.setPartner(this);
    }

    @Column(name = "name", length = 128, nullable = false)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "title")
    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    @Column(name = "comment", length = 256)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "website", length = 256)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "phone", length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "fax", length = 20)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "email")
    @Email(message = "{Supplier.email.invalid}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "debit_limit")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(Money debitLimit) {
        this.debitLimit = debitLimit;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id")
    public UnitOfMeasure getDebitTimeUnit() {
        return debitTimeUnit;
    }

    public void setDebitTimeUnit(UnitOfMeasure debitTimeUnit) {
        this.debitTimeUnit = debitTimeUnit;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "s_partner_partner_category", joinColumns = { @JoinColumn(name = "partner_id") }, inverseJoinColumns = { @JoinColumn(name = "partner_category_id") })
    public
            Set<PartnerCategory> getPartnerCateSet() {
        return partnerCateSet;
    }

    public void setPartnerCateSet(Set<PartnerCategory> partnerCateSet) {
        this.partnerCateSet = partnerCateSet;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "partner")
    public ContactDebt getContactDebt() {
        return contactDebt;
    }

    public void setContactDebt(ContactDebt contactDebt) {
        this.contactDebt = contactDebt;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<ContactDebtHistory> getContactDebtHistories() {
        return contactDebtHistories;
    }

    public void setContactDebtHistories(Set<ContactDebtHistory> contactDebtHitories) {
        this.contactDebtHistories = contactDebtHitories;
    }

    public void addContactDebtHistory(ContactDebtHistory debtHistory) {
        debtHistory.setPartner(this);
        contactDebtHistories.add(debtHistory);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "partner")
    public Set<PartnerAddressLink> getListAddressLinks() {
        return listAddressLinks;
    }

    public void setListAddressLinks(Set<PartnerAddressLink> listAddresses) {
        this.listAddressLinks = listAddresses;
    }

    public void addAddressLink(PartnerAddressLink addressLink) {
        if (listAddressLinks.isEmpty()) {
            addressLink.setMain(true);
        }
        addressLink.setPartner(this);
        listAddressLinks.add(addressLink);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "partner")
    public Set<PartnerProfile> getListProfiles() {
        return listProfiles;
    }

    public void setListProfiles(Set<PartnerProfile> listProfiles) {
        this.listProfiles = listProfiles;
    }

    @Transient
    public void addPartnerProfile(PartnerProfile profile) {
        profile.setPartner(this);
        listProfiles.add(profile);
    }

    public PartnerProfile getPartnerProfile(PartnerProfileTypeEnum type) {
        for (PartnerProfile profile : listProfiles) {
            if (profile.getType() == type) {
                return profile;
            }
        }
        return null;
    }

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    //
    @Transient
    public Bank getBank() {
        return bankAccount != null ? bankAccount.getBank() : null;
    }

    @Transient
    public void setBank(Bank bank) {
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }
        bankAccount.setBank(bank);
    }

    @Transient
    public String getAccountNumber() {
        return bankAccount != null ? bankAccount.getAccountNumber() : null;
    }

    @Transient
    public void setAccountNumber(String accountNumber) {
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }
        bankAccount.setAccountNumber(accountNumber);
    }

    @Transient
    public String getAccountName() {
        return bankAccount != null ? bankAccount.getAccountName() : null;
    }

    @Transient
    public void setAccountName(String accountName) {
        if (bankAccount == null) {
            bankAccount = new BankAccount();
        }
        bankAccount.setAccountName(accountName);
    }

    @Transient
    public Individual getMainIndividual() {
        Individual mainIndividual = null;
        if (mainIndividual == null) {
            for (Individual individual : individuals) {
                if (individual.getRole() == IndividualRoleEnum.MAIN) {
                    mainIndividual = individual;
                    break;
                }
            }
        }
        return mainIndividual;
    }

    @Transient
    public PartnerAddressLink getMainAddressLink() {
        PartnerAddressLink mainAddressLink = null;
        if (mainAddressLink == null) {
            for (PartnerAddressLink addressLink : listAddressLinks) {
                if (addressLink.isMain()) {
                    mainAddressLink = addressLink;
                    break;
                }
            }
        }
        return mainAddressLink;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "partner")
    public Set<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(Set<Individual> individuals) {
        this.individuals = individuals;
    }

    public void addIndividual(Individual individual) {
        individual.setPartner(this);
        individuals.add(individual);
    }

    @Transient
    public boolean isSupplier() {
        for (PartnerProfile profile : listProfiles) {
            if (PartnerProfileTypeEnum.SUPPLIER.equals(profile.getType())) {
                return true;
            }
        }
        return false;
    }

    @Transient
    public boolean isCustomer() {
        for (PartnerProfile profile : listProfiles) {
            if (PartnerProfileTypeEnum.CUSTOMER.equals(profile.getType())) {
                return true;
            }
        }
        return false;
    }
}
