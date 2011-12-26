package com.s3s.ssm.entity.param;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.s3s.ssm.entity.AbstractCodeOLObject;

@Entity
@Table(name = "s_exchange_rate")
public class ExchangeRate extends AbstractCodeOLObject {
	private DateTime updateDate = new DateTime();    //default is current date
	private Currency currency;
	private Integer rate;
	
	@Column(name = "update_date", nullable = false)
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}

	@ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	@Column(name = "rate", nullable = false)
    @NotNull
	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
}
