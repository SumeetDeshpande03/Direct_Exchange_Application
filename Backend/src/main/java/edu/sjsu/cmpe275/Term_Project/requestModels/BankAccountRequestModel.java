package edu.sjsu.cmpe275.Term_Project.requestModels;

public class BankAccountRequestModel {
	
	private String bankName;

	private String country;
	
	private long accountNumber;
	
	private String ownerName;
	
	private String ownerAddress;
	
	private String primaryCurrency;
	
	private String modeSupported;
	
	private String userName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
