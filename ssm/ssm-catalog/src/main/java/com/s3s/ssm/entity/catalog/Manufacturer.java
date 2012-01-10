package com.s3s.ssm.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.config.UploadFile;

@Entity
@Table(name = "s_manufacturer")
public class Manufacturer extends AbstractCodeOLObject {

    private String name;
    private UploadFile symbol;

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "symbol_id")
    public UploadFile getSymbol() {
        return symbol;
    }

    public void setSymbol(UploadFile symbol) {
        this.symbol = symbol;
    }

}