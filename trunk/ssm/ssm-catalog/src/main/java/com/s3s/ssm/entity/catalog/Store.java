package com.s3s.ssm.entity.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_store")
public class Store extends AbstractCodeOLObject {
    private String name;
    private String address;
    private String storedAddress;
    private String importAddress;
    private String exportAddress;
    private String managerCode;

    @Column(name = "store_name", nullable = false, length = 128)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address", nullable = false, length = 128)
    @NotNull
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "stored_address", nullable = false, length = 128)
    @NotNull
    public String getStoredAddress() {
        return storedAddress;
    }

    public void setStoredAddress(String storedAddress) {
        this.storedAddress = storedAddress;
    }

    @Column(name = "import_address", nullable = false, length = 128)
    @NotNull
    public String getImportAddress() {
        return importAddress;
    }

    public void setImportAddress(String importAddress) {
        this.importAddress = importAddress;
    }

    @Column(name = "export_address", nullable = false, length = 128)
    @NotNull
    public String getExportAddress() {
        return exportAddress;
    }

    public void setExportAddress(String exportAddress) {
        this.exportAddress = exportAddress;
    }

    // login name of the manager in system
    @Column(name = "manager_code", length = 32)
    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

}
