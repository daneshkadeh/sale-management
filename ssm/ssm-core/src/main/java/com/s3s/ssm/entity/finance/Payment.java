package com.s3s.ssm.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.contact.Contact;
import com.s3s.ssm.entity.param.CurrencyEnum;
import com.s3s.ssm.entity.sales.Invoice;

@Entity
@Table(name = "s_payment")
public class Payment extends AbstractIdOLObject {
    private Invoice invoice;
    private Contact contact;
    private PaymentType paymentType;
    private Double money;
    private CurrencyEnum currency = CurrencyEnum.getDefaultCurrency();
    private PaymentMeanEnum paymentMean;
    private PaymentStatus status = PaymentStatus.OPEN;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_type_id")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "money", nullable = false)
    @NotNull
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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

    @Column(name = "payment_mean", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public PaymentMeanEnum getPaymentMean() {
        return paymentMean;
    }

    public void setPaymentMean(PaymentMeanEnum paymentMean) {
        this.paymentMean = paymentMean;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
