package com.s3s.ssm.entity.contact;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.config.ContactType;

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
    private Set<ContactShop> listShops = new HashSet<>();

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

    @Column(name = "maximum_day_debt")
    public Long getMaximumDayDebt() {
        return maximumDayDebt;
    }

    public void setMaximumDayDebt(Long maximumDayDebt) {
        this.maximumDayDebt = maximumDayDebt;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contact")
    public Set<ContactShop> getListShops() {
        return listShops;
    }

    public void setListShops(Set<ContactShop> listShops) {
        this.listShops = listShops;
    }

    public void addShop(ContactShop contactShop) {
        contactShop.setContact(this);
        listShops.add(contactShop);
    }
}
