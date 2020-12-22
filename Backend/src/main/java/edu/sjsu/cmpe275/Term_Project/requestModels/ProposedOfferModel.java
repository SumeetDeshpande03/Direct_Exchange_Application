package edu.sjsu.cmpe275.Term_Project.requestModels;

public class ProposedOfferModel {
	
	public String getSplitUserId1() {
		return splitUserId1;
	}

	public void setSplitUserId1(String splitUserId1) {
		this.splitUserId1 = splitUserId1;
	}

	public String getSplitUserId2() {
		return splitUserId2;
	}

	public void setSplitUserId2(String splitUserId2) {
		this.splitUserId2 = splitUserId2;
	}

	public double getSplitUser1Amount() {
		return splitUser1Amount;
	}

	public void setSplitUser1Amount(double splitUser1Amount) {
		this.splitUser1Amount = splitUser1Amount;
	}

	public double getSplitUser2Amount() {
		return splitUser2Amount;
	}

	public void setSplitUser2Amount(double splitUser2Amount) {
		this.splitUser2Amount = splitUser2Amount;
	}

	private String exchangeOfferId;
	
	private double amount;
	
//	private String proposerUsername;
	
	private String splitUserId1;
	
	private String splitUserId2;
	
	private double splitUser1Amount;
	
	private double splitUser2Amount;
	
	private long offerStatusChange; 

	public long getOfferStatusChange() {
		return offerStatusChange;
	}

	public void setOfferStatusChange(long offerStatusChange) {
		this.offerStatusChange = offerStatusChange;
	}

	public String getExchangeOfferId() {
		return exchangeOfferId;
	}

	public void setExchangeOfferId(String exchangeOfferId) {
		this.exchangeOfferId = exchangeOfferId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

//	public String getProposerUsername() {
//		return proposerUsername;
//	}

//	public void setProposerUsername(String proposerUsername) {
//		this.proposerUsername = proposerUsername;
//	}
}
