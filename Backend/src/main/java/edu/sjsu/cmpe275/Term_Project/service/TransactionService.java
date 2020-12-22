package edu.sjsu.cmpe275.Term_Project.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.Term_Project.entity.ExchangeOffer;
import edu.sjsu.cmpe275.Term_Project.entity.TransactionDetails;
import edu.sjsu.cmpe275.Term_Project.entity.User;
import edu.sjsu.cmpe275.Term_Project.repository.ExchangeOfferRepository;
import edu.sjsu.cmpe275.Term_Project.repository.TransactionRepository;
import edu.sjsu.cmpe275.Term_Project.repository.UserRepository;
import edu.sjsu.cmpe275.Term_Project.requestModels.UserReportingModel;

/**
 * TransactionService which makes the call to the Repository for performing CRUD operations
 * @author sumeetdeshpande
 *
 */
@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExchangeOfferRepository exchangeOfferRepository;
	
	public List<UserReportingModel> getTransactionsByUserName(String userName) throws Exception {
		
		User user = userRepository.findById(userName).orElse(null);
		if(user==null) {
			throw new Exception("User does not exist");
		}
	
		
		List<TransactionDetails> transactionList = transactionRepository.getAllTransactionsOfUser(userName);
		List<UserReportingModel> reportings =  new ArrayList<>();
		ExchangeOffer offer;
		
		for(TransactionDetails transaction: transactionList) {
			UserReportingModel reporting = new UserReportingModel();
			offer = exchangeOfferRepository.findById(transaction.getOfferid1()).orElse(null);
			reporting.setDate(transaction.getCreateDate());
			reporting.setDestAmount(transaction.getAmount() * offer.getExchangeRate());
			reporting.setDestCountry(offer.getDestinationCountry());
			reporting.setDestCurrency(offer.getDestinationCurrency());
			reporting.setRate(offer.getExchangeRate());
			reporting.setServiceFee(transaction.getServiceFee());
			reporting.setSourceAmount(transaction.getAmount());
			reporting.setSourceCountry(offer.getSourceCountry());
			reporting.setSourceCurrency(offer.getSourceCurrency());
			reporting.setTotal(transaction.getAmount() - transaction.getServiceFee());
			reporting.setStatus(transaction.getStatus());
			reportings.add(reporting);
			
		}
	/*	for(TransactionDetails transaction: transactionList) {
			UserReportingModel reporting = new UserReportingModel();
			reporting.setDate(transaction.getCreateDate());
			reporting.setDestAmount(transaction.getAmount() * transaction.getExchange_offer().getExchangeRate());
			reporting.setDestCountry(transaction.getExchange_offer().getDestinationCountry());
			reporting.setDestCurrency(transaction.getExchange_offer().getDestinationCurrency());
			reporting.setRate(transaction.getExchange_offer().getExchangeRate());
			reporting.setServiceFee(transaction.getServiceFee());
			reporting.setSourceAmount(transaction.getAmount());
			reporting.setSourceCountry(transaction.getCountry());
			reporting.setSourceCurrency(transaction.getCurrency());
			reporting.setTotal(transaction.getAmount() - transaction.getServiceFee());
			reporting.setStatus(transaction.getStatus());
			reportings.add(reporting);
			
		}
		*/
		
		
		
		return reportings;
		
	}
	
	
	public List<TransactionDetails> getAllTransactions() throws Exception{
		List<TransactionDetails> transactionList = transactionRepository.findAll();
		return transactionList;

	}
	
	
	public boolean updateStatus(String userName, List<TransactionDetails> transactionList) throws Exception {

		Date currentDate = new Date();
		long delta = 5 * 60 * 1000;
		
		for(TransactionDetails transaction: transactionList) {
			if(transaction.getCreateDate().getTime() < (currentDate.getTime()-delta)) {
				//update to expired
				transaction.setStatus("Expired");
				
			//	ExchangeOffer updatedOffer = transaction.getExchange_offer();
				ExchangeOffer updatedOffer = exchangeOfferRepository.findById(transaction.getOfferid1()).orElse(null);
				updatedOffer.setOfferStatus("Expired");
				transactionRepository.save(transaction);
				exchangeOfferRepository.save(updatedOffer);
				
				
			}
		}
		
		
		return true;
		
		

		
	}
	
	public List<TransactionDetails> getPendingTransactionsByUserName(String userName) throws Exception {
		
		User user = userRepository.findById(userName).orElse(null);
		if(user==null) {
			throw new Exception("User does not exist");
		}
		List<TransactionDetails> transactionList = transactionRepository.getAllTransactionsOfUser(userName);

	/*	boolean success = updateStatus(userName, transactionList);
		int totalTransactions = transactionList.size();
		int expiredTransactions = transactionRepository.getExpiredTransactions(userName).size();
		double reputation = ((1-(expiredTransactions)/(totalTransactions)) * 4)+1;
		
		user.setReputation(reputation);
		userRepository.save(user);
		*/
		
		List<TransactionDetails> pendingTransactionList = transactionRepository.getPendingTransactionsOfUser(userName);
		return pendingTransactionList;
		
	}
	
	public boolean updateTransactionsBasedOnPayment(long exchangeOfferId, String userName, double amount) throws Exception {
		
		User user = userRepository.findById(userName).orElse(null);
		ExchangeOffer exchangeOffer = exchangeOfferRepository.findById(exchangeOfferId).orElse(null);
		
		if(user==null) {
			throw new Exception("User does not exist");
		}
		
		List<TransactionDetails> allPartiesBasedOnOffer = transactionRepository.getAllPartiesForAnOffer(exchangeOffer);
		boolean allPartiesPaid = true;
		/**
		 * Update status of the user who has paid
		 */
		for(TransactionDetails t: allPartiesBasedOnOffer) {
			if(t.getUsername().equalsIgnoreCase(userName)) {
				t.setStatus("Paid");
				t.setAmount(amount*0.9995);
				t.setServiceFee(amount*0.0005);
			}
		}
		/**
		 * Check if all parties have paid
		 */
		for(TransactionDetails t: allPartiesBasedOnOffer) {
			if(!(t.getStatus().equalsIgnoreCase("Paid"))) {
				allPartiesPaid = false;
			}
		}
		/**
		 * If all parties have paid, mark status as fulfilled
		 */
		if(allPartiesPaid) {
			for(TransactionDetails t: allPartiesBasedOnOffer) {
				t.setStatus("Fulfilled");
			}
		}
		/**
		 * Save the status in the repository eventually
		 */
		for(TransactionDetails t: allPartiesBasedOnOffer) {
			transactionRepository.save(t);
		}
		
		return allPartiesPaid;
		
	}
	
	public List<TransactionDetails> getAllPartiesForAnOffer(ExchangeOffer exchangeOffer) throws Exception {
		
		List<TransactionDetails> allPartiesBasedOnOffer = transactionRepository.getAllPartiesForAnOffer(exchangeOffer);
		return allPartiesBasedOnOffer;
		
	}
	
	
	
}
