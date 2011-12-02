package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_package")
public class SPackage extends AbstractCodeOLObject {
    private String name;
    private Integer minTotalItemAmount;
    private Integer maxTotalItemAmount;

    @Column(name = "name", length = 128, nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "min_total_item_amount", nullable = false)
    @NotNull
    public Integer getMinTotalItemAmount() {
        return minTotalItemAmount;
    }

    public void setMinTotalItemAmount(Integer minTotalItemAmount) {
        this.minTotalItemAmount = minTotalItemAmount;
    }

    @Column(name = "max_total_item_amount", nullable = false)
    @NotNull
    public Integer getMaxTotalItemAmount() {
        return maxTotalItemAmount;
    }

    public void setMaxTotalItemAmount(Integer maxTotalItemAmount) {
        this.maxTotalItemAmount = maxTotalItemAmount;
    }
}
