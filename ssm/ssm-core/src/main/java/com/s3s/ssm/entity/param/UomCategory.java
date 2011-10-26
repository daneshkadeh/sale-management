package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_uom_category")
public class UomCategory extends AbstractCodeOLObject {
    private String name;

    @Column(name = "uom_category_name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
