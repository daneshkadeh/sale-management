package com.s3s.ssm.entity.sales;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.model.Money;

/**
 * When THU pays for contract, they can create many paymentSC. If paymentSC is LC, there is only 1 payment SC. If
 * paymentSC is TT, there is many payments. PaymentSC does not have status because it's always paid
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_payment_sc")
public class PaymentSC extends AbstractIdOLObject {
    private SalesContract salesContract;
    private PaymentSCType type;
    private Money amount;
    private Bank bank;
    private String referenceCode; // reference code of the bank payment
    private String remark;

    public enum PaymentSCType {
        LC, TT
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_contract_id")
    public SalesContract getSalesContract() {
        return salesContract;
    }

    public void setSalesContract(SalesContract salesContract) {
        this.salesContract = salesContract;
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull
    public PaymentSCType getType() {
        return type;
    }

    public void setType(PaymentSCType type) {
        this.type = type;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id")
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Column(name = "reference_code", length = 64)
    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    @Column(name = "remark", length = 512)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
