package com.s3s.ssm.entity.param;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_product")
public class Product extends AbstractCodeOLObject {
    private String name;
    // TODO: we should have a method to get and set id directly (with AOP approach)
    // private Long manufacturerId;
    private Manufacturer manufacturer;
    private ProductType type;
    private String model;
    private String description;

    private Long uploadFileId;

    @Column(name = "name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "model", length = 32)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "description", length = 128)
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @ManyToOne
    @JoinColumn(name = "producttype_id", nullable = false)
    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Column(name = "uploadfile_id", length = 128)
    @NotNull
    public Long getUploadFileId() {
        return uploadFileId;
    }

    public void setUploadFileId(Long uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

}
