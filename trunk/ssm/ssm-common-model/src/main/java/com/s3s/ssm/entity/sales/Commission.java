package com.s3s.ssm.entity.sales;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;

/**
 * Commission of Invoice (% or money per invoice)
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_commission")
public class Commission extends AbstractIdOLObject {
    private static final long serialVersionUID = 1L;

    private Invoice invoice;
    private CommissionType type;

    // commissionMoney can negative or postive.
    // totalAmount = totalAmount + commissionMoney
    private Money commissionMoney = Money.zero(CurrencyEnum.VND);
    private String remark; // take note "why THU apply commission on the customer".

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "invoice_id")
    @NotNull
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "commission_type_id")
    @NotNull
    public CommissionType getType() {
        return type;
    }

    public void setType(CommissionType type) {
        this.type = type;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "commission_money")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    @NotNull
    public Money getCommissionMoney() {
        return commissionMoney;
    }

    public void setCommissionMoney(Money commissionMoney) {
        this.commissionMoney = commissionMoney;
    }

    @Column(name = "remark", length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Commission duplicate() {
        Commission commission = new Commission();
        try {
            BeanUtils.copyProperties(commission, this);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: Can not copy commission!");
        }
        return commission;
    }
}
