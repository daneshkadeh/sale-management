package com.s3s.ssm.entity.sales;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_contract_document")
public class ContractDocument extends AbstractCodeOLObject {
    /**
     * 
     */
    private static final long serialVersionUID = -151745017600817858L;
    private String name;
    private DocumentPlaceEnum documentPlace;

    @Column(name = "datetime_contract", length = 256)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "document_place", nullable = false, length = 32)
    @NotNull
    @Enumerated(EnumType.STRING)
    public DocumentPlaceEnum getDocumentPlace() {
        return documentPlace;
    }

    public void setDocumentPlace(DocumentPlaceEnum documentPlace) {
        this.documentPlace = documentPlace;
    }

    public enum DocumentPlaceEnum {
        IN_COUNTRY, IN_FOREIGN
    }
}
