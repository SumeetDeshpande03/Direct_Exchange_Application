package edu.sjsu.cmpe275.Term_Project.requestModels;

import java.util.Date;

public class UserReportingModel {
	
	private Date Date;
	private String sourceCurrency;
	private String DestCurrency;
	private String DestCountry;
	private String sourceCountry;
	
	private double rate;
	private double serviceFee;
	private double sourceAmount;
	private double destAmount;
	
	private double total;
	private String status;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public UserReportingModel() {
		
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public String getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	public String getDestCurrency() {
		return DestCurrency;
	}
	public void setDestCurrency(String destCurrency) {
		DestCurrency = destCurrency;
	}
	public String getDestCountry() {
		return DestCountry;
	}
	public void setDestCountry(String destCountry) {
		DestCountry = destCountry;
	}
	public String getSourceCountry() {
		return sourceCountry;
	}
	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}
	public double getSourceAmount() {
		return sourceAmount;
	}
	public void setSourceAmount(double sourceAmount) {
		this.sourceAmount = sourceAmount;
	}
	public double getDestAmount() {
		return destAmount;
	}
	public void setDestAmount(double destAmount) {
		this.destAmount = destAmount;
	}
	
	


}
