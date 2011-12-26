package com.s3s.ssm.entity.sales;

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
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.config.ContactType;
import com.s3s.ssm.entity.config.CurrencyEnum;

@Entity
@Table(name = "s_package_line_item_price")
public class PackageLineItemPrice extends AbstractIdOLObject {
    private PackageLine packageLine;
    private ContactType contactType;
    private Double sellItemPrice;
    private CurrencyEnum currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_line_id", nullable = false)
    @NotNull
    public PackageLine getPackageLine() {
        return packageLine;
    }

    public void setPackageLine(PackageLine packageLine) {
        this.packageLine = packageLine;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_type_id", nullable = false)
    @NotNull
    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    @Column(name = "sell_price", nullable = false)
    @NotNull
    public Double getSellItemPrice() {
        return sellItemPrice;
    }

    public void setSellItemPrice(Double sellItemPrice) {
        this.sellItemPrice = sellItemPrice;
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
