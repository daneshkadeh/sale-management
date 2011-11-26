package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "s_package_line")
public class PackageLine extends AbstractIdOLObject {
    private Package pack;
    private PackageLine parentPackageLine;
    private Item item;
    private Boolean optional;
    private Integer minItemAmount;
    private Integer maxItemAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id", nullable = false)
    @NotNull
    public Package getPackage() {
        return pack;
    }

    public void setPackage(Package pack) {
        this.pack = pack;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_line_id")
    public PackageLine getParentPackageLine() {
        return parentPackageLine;
    }

    public void setParentPackageLine(PackageLine parentPackageLine) {
        this.parentPackageLine = parentPackageLine;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    @NotNull
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(name = "optional", nullable = false)
    @NotNull
    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    @Column(name = "min_item_amount", nullable = false)
    @NotNull
    public Integer getMinItemAmount() {
        return minItemAmount;
    }

    public void setMinItemAmount(Integer minItemAmount) {
        this.minItemAmount = minItemAmount;
    }

    @Column(name = "max_item_amount", nullable = false)
    @NotNull
    public Integer getMaxItemAmount() {
        return maxItemAmount;
    }

    public void setMaxItemAmount(Integer maxItemAmount) {
        this.maxItemAmount = maxItemAmount;
    }

}
