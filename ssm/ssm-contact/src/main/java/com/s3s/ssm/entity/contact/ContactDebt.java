package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.config.CurrencyEnum;

@Entity
@Table(name = "s_contact_debt")
public class ContactDebt extends AbstractIdOLObject {
    private Contact contact;
    private Double debtMoney;
    private CurrencyEnum currency;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Column(name = "debt_money", nullable = false)
    @NotNull
    public Double getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(Double debtMoney) {
        this.debtMoney = debtMoney;
    }

    @Column(name = "currency", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

}
