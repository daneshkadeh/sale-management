package com.s3s.ssm.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "s_goods")
@PrimaryKeyJoinColumn(name = "goods_id")
public class Goods extends Product {
    private Long alertQuantity;

    @Column(name = "alert_quantity", nullable = false)
    @NotNull
    public Long getAlertQuantity() {
        return alertQuantity;
    }

    public void setAlertQuantity(Long alertQuantity) {
        this.alertQuantity = alertQuantity;
    }
}
