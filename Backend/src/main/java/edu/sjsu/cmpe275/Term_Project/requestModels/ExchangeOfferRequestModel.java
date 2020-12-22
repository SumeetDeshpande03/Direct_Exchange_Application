package edu.sjsu.cmpe275.Term_Project.requestModels;

public class ExchangeOfferRequestModel {
	
	private String sourceCountry;
	
	private String sourceCurrency;
	
	private double amountToRemitSourceCurrency;
	
	private String destinationCountry;
	
	private String destinationCurrency;
	
	private double exchangeRate;
	
	private String expirationDate;
	
	private String allowCounterOffers;
	
	private String allowSplitExchanges;
	
	private String receivingBankName;
	
	private long receivingAccountNumber;
	
	private String offerStatus;
	
	private String userName;

	public String getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public double getAmountToRemitSourceCurrency() {
		return amountToRemitSourceCurrency;
	}

	public void setAmountToRemitSourceCurrency(double amountToRemitSourceCurrency) {
		this.amountToRemitSourceCurrency = amountToRemitSourceCurrency;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency(String destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getAllowCounterOffers() {
		return allowCounterOffers;
	}

	public void setAllowCounterOffers(String allowCounterOffers) {
		this.allowCounterOffers = allowCounterOffers;
	}

	public String getAllowSplitExchanges() {
		return allowSplitExchanges;
	}

	public void setAllowSplitExchanges(String allowSplitExchanges) {
		this.allowSplitExchanges = allowSplitExchanges;
	}

	public String getReceivingBankName() {
		return receivingBankName;
	}

	public void setReceivingBankName(String receivingBankName) {
		this.receivingBankName = receivingBankName;
	}

	public long getReceivingAccountNumber() {
		return receivingAccountNumber;
	}

	public void setReceivingAccountNumber(long receivingAccountNumber) {
		this.receivingAccountNumber = receivingAccountNumber;
	}

	public String getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(String offerStatus) {
		this.offerStatus = offerStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
