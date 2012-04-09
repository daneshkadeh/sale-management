package com.s3s.ssm.entity.catalog;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.s3s.ssm.model.Money;

@Entity
@Table(name = "ca_voucher")
@PrimaryKeyJoinColumn(name = "voucher_id")
public class Voucher extends Product {
    private Money minAmount;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "min_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code")) })
    public Money getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Money minQuantity) {
        this.minAmount = minQuantity;
    }
}
