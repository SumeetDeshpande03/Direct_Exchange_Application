package edu.sjsu.cmpe275.Term_Project.requestModels;

import edu.sjsu.cmpe275.Term_Project.entity.ExchangeOffer;

public class TransactionDetailsModel {
	
	public long getExchangeOfferId() {
		return exchangeOfferId;
	}

	public void setExchangeOfferId(long exchangeOfferId) {
		this.exchangeOfferId = exchangeOfferId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getPercentOfTotalAmount() {
		return percentOfTotalAmount;
	}

	public void setPercentOfTotalAmount(double percentOfTotalAmount) {
		this.percentOfTotalAmount = percentOfTotalAmount;
	}

	public Boolean getIsTransferred() {
		return isTransferred;
	}

	public void setIsTransferred(Boolean isTransferred) {
		this.isTransferred = isTransferred;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	

	private String userName;

	private long exchangeOfferId;
	
	private double amount;
	
	private String createDate;
	
	private String expiryDate;
	
	private float inverseExRate;
	
	private ExchangeOffer exchangeOffer;
	
	public ExchangeOffer getExchangeOffer() {
		return exchangeOffer;
	}

	public void setExchangeOffer(ExchangeOffer exchangeOffer) {
		this.exchangeOffer = exchangeOffer;
	}

	public float getInverseExRate() {
		return inverseExRate;
	}

	public void setInverseExRate(float inverseExRate) {
		this.inverseExRate = inverseExRate;
	}



	private double percentOfTotalAmount;
	
	private Boolean isTransferred=false;
	
	private String bankName;

	private long accountNumber;

}
