package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.contact.BankAccount;
import com.s3s.ssm.entity.contact.Contact;

@Entity
@Table(name = "s_supplier")
public class Supplier extends AbstractCodeOLObject {
    private String name;
    private Contact mainContact;
    private String phoneNumber;
    private String fixPhoneNumber;
    private String faxNumber;
    private String email;
    private BankAccount bankAccount;
    private String note;

    @Column(name = "supplier_name", length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "main_contact_id")
    public Contact getMainContact() {
        return mainContact;
    }

    public void setMainContact(Contact mainContact) {
        this.mainContact = mainContact;
    }

    @Column(name = "phone_number", length = 32)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "fix_phone_number", length = 32)
    public String getFixPhoneNumber() {
        return fixPhoneNumber;
    }

    public void setFixPhoneNumber(String fixPhoneNumber) {
        this.fixPhoneNumber = fixPhoneNumber;
    }

    @Column(name = "fax_number", length = 32)
    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEmail() {
        return email;
    }

    @Column(name = "email", length = 64)
    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "note", length = 256)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
