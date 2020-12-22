package edu.sjsu.cmpe275.Term_Project.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity Object for Exchange Offer
 * @author priyankasharma
 *
 */
@Entity
@Table(name= "PROPOSED_OFFER")
public class ProposedOffer {
	
	@Id
	@GeneratedValue
	private long id;
	
	private double amount;

//	private String proposerUsername;
	
	private String splitUserId1;
	
	private String splitUserId2;
	
	private Date createDate;
	
	private double splitUser1Amount;
	
	private double splitUser2Amount;
	
	private double exchangeRate;
	
	private String status="Open";
	
	private Boolean isCounter=true;
	
	@ManyToOne
	@JsonIgnore
	private ExchangeOffer exchangeOffer;
	
	public ProposedOffer() {
		
	}
	
	public ProposedOffer( double amount ,String splitUserId1 , String splitUserId2, 
			double splitUser1Amount, double splitUser2Amount) {
		this.amount = amount;
		this.splitUserId1 = splitUserId1;
		this.splitUserId2 = splitUserId2;
		this.splitUser1Amount = splitUser1Amount;
		this.splitUser2Amount = splitUser2Amount;
		this.createDate =  new Date();
//		this.proposerUsername = proposerUsername;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		Date currentDate = new Date();
		long delta = 5 * 60 * 1000;
		if ((currentDate.getTime() - delta > this.createDate.getTime()) &&
			 this.status.equals("Open")) {
			// TODO :
			// UpdateProposeoffer(id);
			return "Expired";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsCounter() {
		return isCounter;
	}

	public void setIsCounter(Boolean isCounter) {
		this.isCounter = isCounter;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ExchangeOffer getExchangeOffer() {
		return exchangeOffer;
	}

	public void setExchangeOffer(ExchangeOffer exchangeOffer) {
		this.exchangeOffer = exchangeOffer;
	}

	public String getSplitUserId1() {
		return splitUserId1;
	}

	public void setSplitUserId1(String splitUserId1) {
		this.splitUserId1 = splitUserId1;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getSplitUser2Amount() {
		return splitUser2Amount;
	}

	public void setSplitUser2Amount(double splitUser2Amount) {
		this.splitUser2Amount = splitUser2Amount;
	}

	public double getSplitUser1Amount() {
		return splitUser1Amount;
	}

	public void setSplitUser1Amount(double splitUser1Amount) {
		this.splitUser1Amount = splitUser1Amount;
	}

	public String getSplitUserId2() {
		return splitUserId2;
	}

	public void setSplitUserId2(String splitUserId2) {
		this.splitUserId2 = splitUserId2;
	}
	
}
