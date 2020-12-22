package edu.sjsu.cmpe275.Term_Project.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity Object for Exchange Offer
 * @author sumeetdeshpande
 *
 */
@Entity
@Table(name= "EXCHANGE_OFFER")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ExchangeOffer {

	@Id
	@GeneratedValue
	private long id;
	
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
	
	private String offerStatus = "Open";
	
	@ManyToOne
	@JoinColumn(name="userName")
	@JsonIgnoreProperties({"bankAccounts", "exchangeOffers"})
	private User user;
	
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "exchangeOffer", fetch = FetchType.LAZY)
	private List<ProposedOffer> proposedOffers;
	
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "exchange_offer", fetch = FetchType.LAZY)
	private List<TransactionDetails> transactionDetails;
	

	

	public ExchangeOffer() {
		
	}
	
	public ExchangeOffer(String sourceCountry, String sourceCurrency, double amountToRemitSourceCurrency,
						  String destinationCountry, String destinationCurrency, double exchangeRate,
						   String expirationDate, String allowCounterOffers, String allowSplitExchanges,
						   	String receivingBankName, long receivingAccountNumber, String offerStatus) {
		
		this.sourceCountry = sourceCountry;
		this.sourceCurrency = sourceCurrency;
		this.amountToRemitSourceCurrency = amountToRemitSourceCurrency;
		this.destinationCountry = destinationCountry;
		this.destinationCurrency = destinationCurrency;
		this.exchangeRate = exchangeRate;
		this.expirationDate = expirationDate;
		this.allowCounterOffers = allowCounterOffers;
		this.allowSplitExchanges = allowSplitExchanges;
		this.receivingBankName = receivingBankName;
		this.receivingAccountNumber = receivingAccountNumber;
		if(offerStatus!=null && !offerStatus.equals("")) {
			this.offerStatus = offerStatus;
		}
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<ProposedOffer> getProposedOffers() {
		return proposedOffers;
	}

	public void setProposedOffers(List<ProposedOffer> proposedOffers) {
		this.proposedOffers = proposedOffers;
	}


	public List<TransactionDetails> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	
	public double getAmountToRemitSourceCurrency() {
		return amountToRemitSourceCurrency;
	}
	
	public void setAmountToRemitSourceCurrency(double amountToRemitSourceCurrency) {
		this.amountToRemitSourceCurrency = amountToRemitSourceCurrency;
	}
	
	
}
