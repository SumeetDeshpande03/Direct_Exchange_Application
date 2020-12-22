package edu.sjsu.cmpe275.Term_Project.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity object for Bank Account
 * @author sumeetdeshpande
 *
 */
@Entity
@Table(name = "BANK_ACCOUNT")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BankAccount {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String bankName;

	private String country;
	
	private long accountNumber;
	
	private String ownerName;
	
	private String ownerAddress;
	
	private String primaryCurrency;
	
	private String modeSupported;
	
	@ManyToOne
	@JoinColumn(name="userName")
	@JsonIgnoreProperties({"bankAccounts", "exchangeOffers"})
	private User user;
	
	public BankAccount() {
		
	}
	
	public BankAccount(String bankName, String country, long accountNumber, 
						String ownerName, String ownerAddress, String primaryCurrency, String modeSupported) {
		this.bankName = bankName;
		this.country = country;
		this.accountNumber = accountNumber;
		this.ownerName = ownerName;
		this.ownerAddress = ownerAddress;
		this.primaryCurrency = primaryCurrency;
		this.modeSupported = modeSupported;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public String getPrimaryCurrency() {
		return primaryCurrency;
	}

	public void setPrimaryCurrency(String primaryCurrency) {
		this.primaryCurrency = primaryCurrency;
	}

	public String getModeSupported() {
		return modeSupported;
	}

	public void setModeSupported(String modeSupported) {
		this.modeSupported = modeSupported;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
