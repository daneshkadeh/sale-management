package com.s3s.ssm.entity.contact;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.Money;

/**
 * This entity is only inserted by the program each month, not by operator.
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_contact_debt_history")
public class ContactDebtHistory extends AbstractIdOLObject {

    private Partner partner;
    private Date startDate; // Ngay dau ki
    private Date endDate; // Ngay cuoi ki

    // => Tong tien mua hang = endDebtAmount + paidAmount - startDebtAmount
    private Money startDebtAmount; // No dau ki
    private Money endDebtAmount; // No cuoi ki
    private Money paidAmount; // Tong tien giao dich trong thang, số tiền Partner trả THU

    @ManyToOne
    @JoinColumn(name = "partner_id")
    @NotNull
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Column(name = "start_date")
    @NotNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    @NotNull
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "start_debt_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_start_debt")) })
    public Money getStartDebtAmount() {
        return startDebtAmount;
    }

    public void setStartDebtAmount(Money startDebtAmount) {
        this.startDebtAmount = startDebtAmount;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "end_debt_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_end_debt")) })
    public Money getEndDebtAmount() {
        return endDebtAmount;
    }

    public void setEndDebtAmount(Money endDebtAmount) {
        this.endDebtAmount = endDebtAmount;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "paid_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_paid")) })
    public Money getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Money paidAmount) {
        this.paidAmount = paidAmount;
    }
}
