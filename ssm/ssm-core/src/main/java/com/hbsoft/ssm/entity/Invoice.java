package com.hbsoft.ssm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_invoice")
public class Invoice extends AbstractEntity {
	private Integer id;
	private Date createdDate;
	private Integer customerId;
	private Double totalBeforeTax;
	private Double taxTotal;
	private Double totalAfterTax;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "created_date", nullable = false)
	@NotNull
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customer_id", nullable = false)
	@NotNull
	public Integer getCustomerId() {
		return customerId;
	}

	public void setTotalBeforeTax(Double totalBeforeTax) {
		this.totalBeforeTax = totalBeforeTax;
	}

	@Column(name = "total_before_tax", nullable = false)
	@NotNull
	public Double getTotalBeforeTax() {
		return totalBeforeTax;
	}

	public void setTaxTotal(Double taxTotal) {
		this.taxTotal = taxTotal;
	}

	@Column(name = "tax_total", nullable = false)
	@NotNull
	public Double getTaxTotal() {
		return taxTotal;
	}

	public void setTotalAfterTax(Double totalAfterTax) {
		this.totalAfterTax = totalAfterTax;
	}

	@Column(name = "total_after_tax", nullable = false)
	@NotNull
	public Double getTotalAfterTax() {
		return totalAfterTax;
	}
}
