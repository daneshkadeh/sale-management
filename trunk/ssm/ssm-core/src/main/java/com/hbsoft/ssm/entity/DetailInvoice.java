package com.hbsoft.ssm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


//id 	int(11) 			No 	None 	auto_increment 	Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//goods_id 	int(11) 			No 	None 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//goods_name 	varchar(250) 	latin1_swedish_ci 		No 	None 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//quantity 	int(11) 			No 	0 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//price_before_tax 	float 			No 	0 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//tax 	float 			No 	0 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//price_after_tax 	float 			No 	0 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//money_before_tax 	float 			No 	0 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//money_of_tax 	float 			No 	0 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//money_after_tax 	float 			No 	0 		Browse distinct values 	Change 	Drop 	Primary 	Unique 	Index 	Fulltext
//invoice_id 	int(11) 			No 	0
@Entity
@Table(name = "tbl_detail_invoice")
public class DetailInvoice {
	private Integer id;
	private Integer goodsId;
	private String goodsName;
	private Integer quantity;
	private Double priceBeforeTax;
	private Double tax;
	private Double priceAfterTax;
	private Double moneyBeforeTax;
	private Double moneyOfTax;
	private Double moneyAfterTax;
	private Integer invoiceId;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name = "goods_id", nullable = false)
	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "goods_name", nullable = false)
	public String getGoodsName() {
		return goodsName;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "quantity", nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	public void setPriceBeforeTax(Double priceBeforeTax) {
		this.priceBeforeTax = priceBeforeTax;
	}

	@Column(name = "price_before_tax", nullable = false)
	public Double getPriceBeforeTax() {
		return priceBeforeTax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	@Column(name = "tax", nullable = false)
	public Double getTax() {
		return tax;
	}

	public void setPriceAfterTax(Double priceAfterTax) {
		this.priceAfterTax = priceAfterTax;
	}

	@Column(name = "price_after_tax", nullable = false)
	public Double getPriceAfterTax() {
		return priceAfterTax;
	}

	public void setMoneyBeforeTax(Double moneyBeforeTax) {
		this.moneyBeforeTax = moneyBeforeTax;
	}

	@Column(name = "money_before_tax", nullable = false)
	public Double getMoneyBeforeTax() {
		return moneyBeforeTax;
	}

	public void setMoneyOfTax(Double moneyOfTax) {
		this.moneyOfTax = moneyOfTax;
	}

	@Column(name = "money_of_tax", nullable = false)
	public Double getMoneyOfTax() {
		return moneyOfTax;
	}

	public void setMoneyAfterTax(Double moneyAfterTax) {
		this.moneyAfterTax = moneyAfterTax;
	}

	@Column(name = "money_after_tax", nullable = false)
	public Double getMoneyAfterTax() {
		return moneyAfterTax;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Column(name = "invoice_id", nullable = false)
	public Integer getInvoiceId() {
		return invoiceId;
	}
	
	

}
