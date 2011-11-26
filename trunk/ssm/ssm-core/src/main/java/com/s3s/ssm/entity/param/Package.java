package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_package")
public class Package extends AbstractCodeOLObject {
    private String name;
    private Integer totalExpectedItemAmount;

    @Column(name = "name", length = 128, nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "total_expected_item_amount", nullable = false)
    @NotNull
    public Integer getTotalExpectedItemAmount() {
        return totalExpectedItemAmount;
    }

    public void setTotalExpectedItemAmount(Integer totalExpectedItemAmount) {
        this.totalExpectedItemAmount = totalExpectedItemAmount;
    }
}
