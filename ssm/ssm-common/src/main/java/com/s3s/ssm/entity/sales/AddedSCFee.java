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
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.model.Money;

@Entity
@Table(name = "s_added_sc_fee")
public class AddedSCFee extends AbstractIdOLObject {
    private String name;
    private AddedSCFeeType type;
    private String referenceNumber; // can be supplier invoice number, shipment number ...
    private Partner serviceProvider;
    private Money basePrice;
    private Money unitPrice;
    private String remark;
    private AddedSCFeeStatus status = AddedSCFeeStatus.OPEN;
    private ImportationSC importationSC;

    public enum AddedSCFeeStatus {
        OPEN, PAID, ABANDONED
    }

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fee_type_id")
    public AddedSCFeeType getType() {
        return type;
    }

    public void setType(AddedSCFeeType type) {
        this.type = type;
    }

    @Column(name = "ref_number", nullable = false, length = 128)
    @NotNull
    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_provider_id")
    public Partner getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(Partner serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "base_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_base_price")) })
    public Money getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Money basePrice) {
        this.basePrice = basePrice;
    }

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "unit_price")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "currency_code_unit_price")) })
    public Money getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "status", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    public AddedSCFeeStatus getStatus() {
        return status;
    }

    public void setStatus(AddedSCFeeStatus status) {
        this.status = status;
    }

    @Column(name = "remark", nullable = false, length = 256)
    @NotNull
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "importation_sc_id")
    public ImportationSC getImportationSC() {
        return importationSC;
    }

    public void setImportationSC(ImportationSC importationSC) {
        this.importationSC = importationSC;
    }

}
