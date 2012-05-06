package com.s3s.ssm.entity.sales;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractActiveCodeOLObject;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * 
 * @author phamcongbang
 * 
 */
@Entity
@Table(name = "s_commission_type")
public class CommissionType extends AbstractActiveCodeOLObject {
    private String name;
    private CommissionMethod commissionMethod;
    private Double percent; // 0 <= percent <= 100
    private Money commissionMoney = Money.zero(CurrencyEnum.VND);

    /**
     * PERCENT: commission = totalAmount * percent. MONEY: commission = money
     * 
     */
    public enum CommissionMethod {
        PERCENT, MONEY;
        @Override
        public String toString() {
            return ControlConfigUtils.getEnumString(getDeclaringClass(), this);
        }
    }

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "commission_method", nullable = false, length = 32)
    @NotNull
    @Enumerated(EnumType.STRING)
    public CommissionMethod getCommissionMethod() {
        return commissionMethod;
    }

    public void setCommissionMethod(CommissionMethod commissionMethod) {
        this.commissionMethod = commissionMethod;
    }

    @Column(name = "percent")
    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
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
}
