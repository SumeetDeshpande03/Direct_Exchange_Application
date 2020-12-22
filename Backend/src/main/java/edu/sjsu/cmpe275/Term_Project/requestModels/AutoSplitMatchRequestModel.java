package edu.sjsu.cmpe275.Term_Project.requestModels;

public class AutoSplitMatchRequestModel {
	
	private String destinationCountry;
	
	private String destinationCurrency;
	
	private String sourceCountry;
	
	private String sourceCurrency;
	
	private String userName;
	
	private double amountToRemitInSourceCurrency;
	
	private double exchangeRate;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getAmountToRemitInSourceCurrency() {
		return amountToRemitInSourceCurrency;
	}

	public void setAmountToRemitInSourceCurrency(double amountToRemitInSourceCurrency) {
		this.amountToRemitInSourceCurrency = amountToRemitInSourceCurrency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
}
