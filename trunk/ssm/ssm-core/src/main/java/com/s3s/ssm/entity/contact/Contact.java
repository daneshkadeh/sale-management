package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_contact")
public class Contact extends AbstractCodeOLObject {
    private ContactType contactType;
    private String fullName;
    private String address;
    private String phone;
    private String fixPhone;
    private String fax;
    private String email;
    private String taxCode;
    private BankAccount bankAccount;
    private Long maximumDayDebt;

    @ManyToOne
    @JoinColumn(name = "contact_type_id", nullable = false)
    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    @Column(name = "full_name", nullable = false, length = 128)
    @NotNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "address", length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone", length = 32)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "fix_phone", length = 32)
    public String getFixPhone() {
        return fixPhone;
    }

    public void setFixPhone(String fixPhone) {
        this.fixPhone = fixPhone;
    }

    @Column(name = "fax", length = 32)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "email", length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "tax_code", length = 32)
    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "maximum_day_debt")
    public Long getMaximumDayDebt() {
        return maximumDayDebt;
    }

    public void setMaximumDayDebt(Long maximumDayDebt) {
        this.maximumDayDebt = maximumDayDebt;
    }

}