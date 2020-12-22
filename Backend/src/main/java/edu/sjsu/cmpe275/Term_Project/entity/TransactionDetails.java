package edu.sjsu.cmpe275.Term_Project.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity Object for Exchange Offer
 * @author priyankasharma
 *
 */


@Entity
@Table(name= "Transaction_Details")
public class TransactionDetails {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties({"user", "proposedOffers","transactionDetails"})
	private ExchangeOffer exchange_offer;
	


	private String Username;
	
	private double amount;
	
	private Date createDate;
	
	private String Country;
	
	private String Currency;
	
	private String status="InTransaction";
	
	private String expiryDate;
	
	private float Rate;
	
	private double percentOfTotalAmount;
	
	private Boolean isTransferred=false;
	
	private String bankName;

	private long accountNumber;
	
	private double serviceFee;
	
	private long offerid1;
	
	private long offerid2;
	
	public long getOfferid1() {
		return offerid1;
	}

	public void setOfferid1(long offerid1) {
		this.offerid1 = offerid1;
	}

	public long getOfferid2() {
		return offerid2;
	}

	public void setOfferid2(long offerid2) {
		this.offerid2 = offerid2;
	}

	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public TransactionDetails() {
		
	}

	public TransactionDetails(String Username, double amount, String createDate, String expiryDate, 
			String bankName,  long accountNumber, double percentOfTotalAmount, float rate, String country, String currency) {
		this.Username = Username;
		this.amount = amount;
		this.createDate = new Date();
		this.expiryDate = expiryDate;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.percentOfTotalAmount = percentOfTotalAmount;
		this.Rate = rate;
		this.Country = country;
		this.Currency = currency;
				
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ExchangeOffer getExchange_offer() {
		return exchange_offer;
	}

	public void setExchange_offer(ExchangeOffer exchange_offer) {
		this.exchange_offer = exchange_offer;
	}
	
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Boolean getIsTransferred() {
		return isTransferred;
	}

	public void setIsTransferred(Boolean isTransferred) {
		this.isTransferred = isTransferred;
	}

	public double getPercentOfTotalAmount() {
		return percentOfTotalAmount;
	}

	public void setPercentOfTotalAmount(double percentOfTotalAmount) {
		this.percentOfTotalAmount = percentOfTotalAmount;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public float getRate() {
		return Rate;
	}

	public void setRate(float rate) {
		Rate = rate;
	}

	
}
