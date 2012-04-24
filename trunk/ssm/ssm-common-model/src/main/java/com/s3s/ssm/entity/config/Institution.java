/*
 * Institution
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.entity.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.s3s.ssm.entity.AbstractCodeOLObject;

/**
 * Contain information of company
 * 
 * @author Le Thanh Hoang
 * 
 */
@Entity
@Table(name = "config_institution")
public class Institution extends AbstractCodeOLObject {
    private String companyName;
    private String agent;
    private String position;
    private UploadFile logo;
    private String companyAddress;
    private String tel;
    private String fax;
    private String website;
    private String email;
    // rule of code generation
    private String orderInvCodeRule;
    private String salesInvCodeRule;
    private String salesRefundInvCodeRule;
    private String purInvCodeRule;
    private String purRefundInvCodeRule;
    private String sponContractCodeRule;
    private String movementInvCodeRule;
    private String exportInvCodeRule;
    private String importInvCodeRule;
    private String paymentBillCodeRule;
    private String receiptsCodeRule;
    private String promotionCodeRule;
    private Set<Organization> organizations = new HashSet<Organization>();

    @Column(name = "company_name", length = 250)
    @NotBlank
    @Size(max = 250)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "agent", length = 250)
    @NotBlank
    @Size(max = 250)
    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Column(name = "position", length = 100)
    @NotBlank
    @Size(max = 100)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @ManyToOne
    @JoinColumn(name = "upload_file_id")
    public UploadFile getLogo() {
        return logo;
    }

    public void setLogo(UploadFile logo) {
        this.logo = logo;
    }

    @Column(name = "company_address", length = 250)
    @NotBlank
    @Size(max = 250)
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Column(name = "tel", length = 20)
    @Size(max = 20)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "fax", length = 20)
    @Size(max = 20)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "website", length = 100)
    @URL
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "email", length = 100)
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "order_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getOrderInvCodeRule() {
        return orderInvCodeRule;
    }

    public void setOrderInvCodeRule(String orderInvCodeRule) {
        this.orderInvCodeRule = orderInvCodeRule;
    }

    @Column(name = "sales_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getSalesInvCodeRule() {
        return salesInvCodeRule;
    }

    public void setSalesInvCodeRule(String salesInvCodeRule) {
        this.salesInvCodeRule = salesInvCodeRule;
    }

    @Column(name = "sales_refund_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getSalesRefundInvCodeRule() {
        return salesRefundInvCodeRule;
    }

    public void setSalesRefundInvCodeRule(String salesRefundInvCodeRule) {
        this.salesRefundInvCodeRule = salesRefundInvCodeRule;
    }

    @Column(name = "pur_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getPurInvCodeRule() {
        return purInvCodeRule;
    }

    public void setPurInvCodeRule(String purInvCodeRule) {
        this.purInvCodeRule = purInvCodeRule;
    }

    @Column(name = "pur_refund_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getPurRefundInvCodeRule() {
        return purRefundInvCodeRule;
    }

    public void setPurRefundInvCodeRule(String purRefundInvCodeRule) {
        this.purRefundInvCodeRule = purRefundInvCodeRule;
    }

    @Column(name = "spon_contract_code_rule", length = 50)
    @Size(max = 50)
    public String getSponContractCodeRule() {
        return sponContractCodeRule;
    }

    public void setSponContractCodeRule(String sponContractCodeRule) {
        this.sponContractCodeRule = sponContractCodeRule;
    }

    @Column(name = "movement_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getMovementInvCodeRule() {
        return movementInvCodeRule;
    }

    public void setMovementInvCodeRule(String movementInvCodeRule) {
        this.movementInvCodeRule = movementInvCodeRule;
    }

    @Column(name = "export_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getExportInvCodeRule() {
        return exportInvCodeRule;
    }

    public void setExportInvCodeRule(String exportInvCodeRule) {
        this.exportInvCodeRule = exportInvCodeRule;
    }

    @Column(name = "import_inv_code_rule", length = 50)
    @Size(max = 50)
    public String getImportInvCodeRule() {
        return importInvCodeRule;
    }

    public void setImportInvCodeRule(String importInvCodeRule) {
        this.importInvCodeRule = importInvCodeRule;
    }

    @Column(name = "payment_bill_code_rule", length = 50)
    @Size(max = 50)
    public String getPaymentBillCodeRule() {
        return paymentBillCodeRule;
    }

    public void setPaymentBillCodeRule(String paymentBillCodeRule) {
        this.paymentBillCodeRule = paymentBillCodeRule;
    }

    @Column(name = "receipt_code_rule", length = 50)
    @Size(max = 50)
    public String getReceiptsCodeRule() {
        return receiptsCodeRule;
    }

    public void setReceiptsCodeRule(String receiptsCodeRule) {
        this.receiptsCodeRule = receiptsCodeRule;
    }

    @Column(name = "promotion_code_rule", length = 50)
    @Size(max = 50)
    public String getPromotionCodeRule() {
        return promotionCodeRule;
    }

    public void setPromotionCodeRule(String promotionCodeRule) {
        this.promotionCodeRule = promotionCodeRule;
    }

    @OneToMany(mappedBy = "institution")
    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }
}
