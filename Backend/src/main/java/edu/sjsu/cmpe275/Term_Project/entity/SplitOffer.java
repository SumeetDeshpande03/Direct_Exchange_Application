package edu.sjsu.cmpe275.Term_Project.entity;

/**
 * Object used for Storing split offers and their total amount.
 * Useful when sorting the array of Split offers
 * @author sumeetdeshpande
 *
 */
public class SplitOffer {
	
	private ExchangeOffer offer1;
	
	private ExchangeOffer offer2;
	
	private double totalAmount;

	public ExchangeOffer getOffer1() {
		return offer1;
	}

	public void setOffer1(ExchangeOffer offer1) {
		this.offer1 = offer1;
	}

	public ExchangeOffer getOffer2() {
		return offer2;
	}

	public void setOffer2(ExchangeOffer offer2) {
		this.offer2 = offer2;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
}
