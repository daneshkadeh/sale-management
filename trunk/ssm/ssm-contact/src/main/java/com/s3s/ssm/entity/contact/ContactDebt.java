package com.s3s.ssm.entity.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.config.SCurrency;

@Entity
@Table(name = "s_contact_debt")
public class ContactDebt extends AbstractIdOLObject {
    private Partner partner;
    private Double debtMoney;
    private SCurrency currency;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Column(name = "debt_money", nullable = false)
    @NotNull
    public Double getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(Double debtMoney) {
        this.debtMoney = debtMoney;
    }

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    public SCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(SCurrency currency) {
        this.currency = currency;
    }

}
