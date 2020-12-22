package edu.sjsu.cmpe275.Term_Project.requestModels;

public class UpdateCounterOfferDetailsModel {
	private long exchangeOfferId;
	
	private long counterOfferId;
	
	private String status;

	public long getExchangeOfferId() {
		return exchangeOfferId;
	}

	public void setExchangeOfferId(long exchangeOfferId) {
		this.exchangeOfferId = exchangeOfferId;
	}

	public long getCounterOfferId() {
		return counterOfferId;
	}

	public void setCounterOfferId(long counterOfferId) {
		this.counterOfferId = counterOfferId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
