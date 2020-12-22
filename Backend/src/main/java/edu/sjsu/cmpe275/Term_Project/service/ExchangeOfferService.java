package edu.sjsu.cmpe275.Term_Project.service;

import java.util.ArrayList;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.OptBoolean;

import edu.sjsu.cmpe275.Term_Project.entity.ExchangeOffer;
import edu.sjsu.cmpe275.Term_Project.entity.ProposedOffer;
import edu.sjsu.cmpe275.Term_Project.entity.SplitOffer;
import edu.sjsu.cmpe275.Term_Project.entity.TransactionDetails;
import edu.sjsu.cmpe275.Term_Project.entity.User;
import edu.sjsu.cmpe275.Term_Project.repository.ExchangeOfferRepository;
import edu.sjsu.cmpe275.Term_Project.repository.UserRepository;
import edu.sjsu.cmpe275.Term_Project.requestModels.TransactionDetailsSecondModel;

/**
 * ExchangeOfferService which makes the call to the Repository for performing CRUD operations
 * @author sumeetdeshpande
 *
 */
@Service
public class ExchangeOfferService {
	
	private static DecimalFormat df = new DecimalFormat("0.00000");

	@Autowired
	private ExchangeOfferRepository exchangeOfferRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Service to create/post an offer
	 * @param exchangeOffer
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ExchangeOffer createExchangeOffer(ExchangeOffer exchangeOffer, String userName) throws Exception{
		
		User user = userRepository.findById(userName).orElse(null);
		if(user==null) {
			throw new Exception("User does not exist");
		}
		exchangeOffer.setUser(user);
		return exchangeOfferRepository.save(exchangeOffer);
		
	}
		
	// public List<ExchangeOffer>  getOffersByUserName(String user_name) throws Exception {
	// 	System.out.println("Username service " + user_name);
	// 	List<ExchangeOffer> offers = exchangeOfferRepository.findOffersByUserName(user_name);
	// 	return offers;
  // }
  

  public List<ExchangeOffer>  getOffersByUserName(String userName) throws Exception {
		
		User user = userRepository.findById(userName).orElse(null);
		
		if(user==null) {
			throw new Exception("User does not exist");
		}
		
		return user.getExchangeOffers();
	}
	
	
	public List<ExchangeOffer>  getAllOffersByStatus(String userName , String offerStatus) throws Exception {
		
		User user = userRepository.findById(userName).orElse(null);
		
		if(user==null) {
			throw new Exception("User does not exist");
		}
		
		List<ExchangeOffer> matchingOffersByStatus = exchangeOfferRepository.getAllOffersByStatus(user , offerStatus);
		return matchingOffersByStatus;
	}
	
	public ExchangeOffer getOfferById(long id) throws Exception {
		ExchangeOffer offer = exchangeOfferRepository.findById(id).orElse(null);	
		if(offer==null) {
			throw new Exception("Offer not found");
		}
		return offer;
	}
	
	/**
	 * Service to get exact matching offers
	 * @param destinationCountry
	 * @param destinationCurrency
	 * @param sourceCountry
	 * @param sourceCurrency
	 * @param userName
	 * @param amountToRemitSourceCurrency
	 * @param exchangeRate
	 * @return
	 * @throws Exception
	 */
	public List<ExchangeOffer> getExactMatchingOffers(String destinationCountry, String destinationCurrency, 
													  String sourceCountry, String sourceCurrency, 
													  String userName, double amountToRemitInSourceCurrency, 
													  double exchangeRate) throws Exception{
		
		/**
		 * Check whether user exists or not
		 */
		User user = userRepository.findById(userName).orElse(null);
		
		if(user==null) {
			throw new Exception("User does not exist");
		}
		
		/**
		 * Find probable matching offers for this offer based on the inverse of source and destination country and 
		 * where the user is not the same as the current user
		 */
		List<ExchangeOffer> probableMatchingOffers = 
				exchangeOfferRepository.getMatchingExchangeOffersBasedOnCountry(sourceCountry, 
																				sourceCurrency, 
																			    destinationCountry, 
																			    destinationCurrency, 
																			    user);
		
		
		
		/**
		 * Calculate the amount to remit in destination currency
		 */
		System.out.println(destinationCountry+ destinationCurrency+sourceCountry+sourceCurrency+user);
		System.out.println(probableMatchingOffers);
		
		double amountToRemitInDestinationCurrency =  amountToRemitInSourceCurrency * exchangeRate;
		
		/**
		 * Specify the upper(+10%) and lower bounds(-10%) of the amount to be remitted
		 */
		double upperBound = amountToRemitInDestinationCurrency * 1.1;
		double lowerBound = amountToRemitInDestinationCurrency * 0.9;
		
		List<ExchangeOffer> matchingOffersInRange = new ArrayList<>();
		
		for(ExchangeOffer probableMatchingOffer: probableMatchingOffers) {
			double probableMatchingOfferAmountToRemit = probableMatchingOffer.getAmountToRemitSourceCurrency();
			/**
			 * Check whether amount to be remitted is within range and add accordingly
			 */
			System.out.println(lowerBound);
			System.out.println(upperBound);
			System.out.println(probableMatchingOfferAmountToRemit);

			if(probableMatchingOfferAmountToRemit>=lowerBound && probableMatchingOfferAmountToRemit<=upperBound) {
				matchingOffersInRange.add(probableMatchingOffer);
			}
		}
		
		/**
		 * Sort offers based on their absolute difference with the amount to be remitted
		 */
		Collections.sort(matchingOffersInRange, new Comparator<ExchangeOffer>(){
			
			@Override
			public int compare(ExchangeOffer eo1, ExchangeOffer eo2) {
				
				double absoluteDifferenceOffer1 = Math.abs(amountToRemitInDestinationCurrency - eo1.getAmountToRemitSourceCurrency());
				double absoluteDifferenceOffer2 = Math.abs(amountToRemitInDestinationCurrency - eo2.getAmountToRemitSourceCurrency());
				
				if(absoluteDifferenceOffer1<absoluteDifferenceOffer2) {
					return -1;
				} else if(absoluteDifferenceOffer1>absoluteDifferenceOffer2) {
					return 1;
				} else {
					return 0;
				}
				
			}
			
		});
				
		return matchingOffersInRange;
			
	}
	
	/**
	 * Service to get split matching offers
	 * @param destinationCountry
	 * @param destinationCurrency
	 * @param sourceCountry
	 * @param sourceCurrency
	 * @param userName
	 * @param amountToRemitInSourceCurrency
	 * @param exchangeRate
	 * @return
	 * @throws Exception
	 */
	public List<List<ExchangeOffer>> getSplitMatchingOffers(String destinationCountry, 
															String destinationCurrency, 
															String sourceCountry, 
															String sourceCurrency, 
															String userName, 
															double amountToRemitInSourceCurrency, 
															double exchangeRate) throws Exception{
		
		/**
		 * Check whether user exists or not
		 */
		User user = userRepository.findById(userName).orElse(null);
		
		if(user==null) {
			throw new Exception("User does not exist");
		}
		
		/**
		 * Find probable matching offers for this offer based on the inverse of source and destination country and 
		 * where the user is not the same as the current user
		 */
		List<ExchangeOffer> probableMatchingOffers = 
				exchangeOfferRepository.getMatchingExchangeOffersBasedOnCountry(sourceCountry, 
																				sourceCurrency, 
																			    destinationCountry, 
																			    destinationCurrency, 
																			    user);	
		/**
		 * Calculate the amount to remit in destination currency
		 */
		double amountToRemitInDestinationCurrency =  amountToRemitInSourceCurrency * exchangeRate;
		
		/**
		 * Specify the upper(+10%) and lower bounds(-10%) of the amount to be remitted
		 */
		double upperBound = amountToRemitInDestinationCurrency * 1.1;
		double lowerBound = amountToRemitInDestinationCurrency * 0.9;
		
		
		List<SplitOffer> splitOfferList = new ArrayList<>();
		
		for(int i=0; i<probableMatchingOffers.size()-1;i++) {
			
			double matchingOffer1AmountToRemit = probableMatchingOffers.get(i).getAmountToRemitSourceCurrency();
			
			for(int j=i+1; j<probableMatchingOffers.size(); j++) {
				
				double matchingOffer2AmountToRemit = probableMatchingOffers.get(j).getAmountToRemitSourceCurrency();
				
				double totalAmount = matchingOffer1AmountToRemit + matchingOffer2AmountToRemit;
				/**
				 * Check if total amount is within range and add accordingly
				 */
				if(totalAmount>=lowerBound && totalAmount<=upperBound) {
					SplitOffer splitOffer = new SplitOffer();
					splitOffer.setOffer1(probableMatchingOffers.get(i));
					splitOffer.setOffer2(probableMatchingOffers.get(j));
					splitOffer.setTotalAmount(totalAmount);
					splitOfferList.add(splitOffer);
				}
				
			}
		}
		
		/**
		 * Sort the split offer list based on the absolute difference
		 */
		Collections.sort(splitOfferList, new Comparator<SplitOffer>() {
			@Override
			public int compare(SplitOffer so1, SplitOffer so2) {
				
				double absoluteDifferenceOffer1 = Math.abs(amountToRemitInDestinationCurrency - so1.getTotalAmount());
				double absoluteDifferenceOffer2 = Math.abs(amountToRemitInDestinationCurrency - so2.getTotalAmount());
				
				if(absoluteDifferenceOffer1<absoluteDifferenceOffer2) {
					return -1;
				} else if(absoluteDifferenceOffer1>absoluteDifferenceOffer2) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		
		/**
		 * Populate response to send to front end
		 */
		List<List<ExchangeOffer>> responseObject = new ArrayList<>();
		
		for(SplitOffer splitOffer: splitOfferList) {
			List<ExchangeOffer> exchangeOffers = new ArrayList<>();
			exchangeOffers.add(splitOffer.getOffer1());
			exchangeOffers.add(splitOffer.getOffer2());
			responseObject.add(exchangeOffers);
		}
		
		return responseObject;
		
	}
	
	
	
	/**
	 * Service to update an offer to in transaction mode
	 * @param offer id
	 * @param transaction details
	 * @return
	 * @throws Exception
	 */
	public ExchangeOffer updateOfferStatusToInTransaction(long offer_id, TransactionDetails trdetails) throws Exception {
		ExchangeOffer offer = exchangeOfferRepository.findById(offer_id).orElse(null);
		
		if (offer == null) {
			return offer;
		}
		
		TransactionDetails recv_trdetails = new TransactionDetails(offer.getUser().getUserName(), trdetails.getAmount(), "", "",
				trdetails.getBankName(), trdetails.getAccountNumber(),											  
				trdetails.getPercentOfTotalAmount(), (float)offer.getExchangeRate(), offer.getDestinationCountry(), offer.getDestinationCurrency());
		
		trdetails.setExchange_offer(offer);
		trdetails.setBankName(offer.getReceivingBankName());
		trdetails.setAccountNumber(offer.getReceivingAccountNumber());
		trdetails.setCountry(offer.getSourceCountry());
		trdetails.setCurrency(offer.getSourceCurrency());
		trdetails.setOfferid1(offer_id);
		recv_trdetails.setOfferid1(offer_id);
		recv_trdetails.setExchange_offer(offer);
		offer.setOfferStatus("InTransaction");
		offer.getTransactionDetails().add(trdetails);
		offer.getTransactionDetails().add(recv_trdetails);
		exchangeOfferRepository.save(offer);
		return offer;
	}
	
	public ExchangeOffer updateOfferStatusToInTransactionReverse(long offer_id, TransactionDetails trdetails, String userName) throws Exception {
		ExchangeOffer offer = exchangeOfferRepository.findById(offer_id).orElse(null);
		
		if (offer == null) {
			return offer;
		}
		System.out.println(userName+trdetails.getAmount()*offer.getExchangeRate());
		TransactionDetails recv_trdetails = new TransactionDetails(userName, (trdetails.getAmount()*offer.getExchangeRate()), "", "",
				trdetails.getBankName(), trdetails.getAccountNumber(),											  
				trdetails.getPercentOfTotalAmount(), (float)offer.getExchangeRate(), offer.getDestinationCountry(), offer.getDestinationCurrency());
		
		trdetails.setExchange_offer(offer);
		trdetails.setUsername(offer.getUser().getUserName());
		trdetails.setBankName(offer.getReceivingBankName());
		trdetails.setAccountNumber(offer.getReceivingAccountNumber());
		trdetails.setCountry(offer.getSourceCountry());
		trdetails.setCurrency(offer.getSourceCurrency());
		trdetails.setOfferid1(offer_id);
		recv_trdetails.setOfferid1(offer_id);
		recv_trdetails.setExchange_offer(offer);
		offer.setOfferStatus("InTransaction");
		offer.getTransactionDetails().add(trdetails);
		offer.getTransactionDetails().add(recv_trdetails);
		exchangeOfferRepository.save(offer);
		return offer;
	}
	
	
	
	
	/**
	 * Service to update an offer to in transaction mode
	 * @param offer id
	 * @parma proposedOffer
	 * @return
	 * @throws Exception
	 */
	public ExchangeOffer updateOfferStatusForCounterOffer(String offer_id, ProposedOffer proposedOffer) throws Exception {
		long id = Long.parseLong(offer_id);
		ExchangeOffer exchange_offer = exchangeOfferRepository.findById(id).orElse(null);
		if (exchange_offer == null) {
			return exchange_offer;
		}
		
//		if (proposedOffer.getSplitUserId1().equals(exchange_offer.getUser().getUserName())) {
//			throw new Exception("User cannot propose counter offer to its own exchange offer");
//		}
		
		
		proposedOffer.setExchangeOffer(exchange_offer);
		double rate = 1.0/exchange_offer.getExchangeRate();
		proposedOffer.setExchangeRate(Double.parseDouble(df.format(rate)));
//		exchange_offer.setOfferStatus("CounterMade");
		exchange_offer.getProposedOffers().add(proposedOffer);
		exchangeOfferRepository.save(exchange_offer);
		return exchange_offer;
	}
	
	public ExchangeOffer updateOfferStatusForCounter(String offer_id, ProposedOffer proposedOffer, long offerStatusChange) throws Exception {
		long id = Long.parseLong(offer_id);
		ExchangeOffer exchange_offer = exchangeOfferRepository.findById(id).orElse(null);
		ExchangeOffer offerStatus = exchangeOfferRepository.findById((long) offerStatusChange).orElse(null);

	//	ExchangeOffer offerStatus = exchangeOfferRepository.findById(offerStatusChange).orElse(null);
		if (exchange_offer == null) {
			return exchange_offer;
		}
		
//		if (proposedOffer.getSplitUserId1().equals(exchange_offer.getUser().getUserName())) {
//			throw new Exception("User cannot propose counter offer to its own exchange offer");
//		}
		
		
		proposedOffer.setExchangeOffer(exchange_offer);
		double rate = 1.0/exchange_offer.getExchangeRate();
		proposedOffer.setExchangeRate(Double.parseDouble(df.format(rate)));
//		exchange_offer.setOfferStatus("CounterMade");
		exchange_offer.getProposedOffers().add(proposedOffer);
		
		
		if(offerStatus!=null) {
			offerStatus.setOfferStatus("CounterMade");
			exchangeOfferRepository.save(offerStatus);
		}
		exchangeOfferRepository.save(exchange_offer);
		return exchange_offer;
	}
	
	
	public ExchangeOffer updateCounterOffer(long exchange_offer_id, long counter_offer_id, String status) {
		ExchangeOffer exchange_offer = exchangeOfferRepository.findById(exchange_offer_id).orElse(null);
		if (exchange_offer == null) {
			return exchange_offer;
		}
		
		for (ProposedOffer poffer : exchange_offer.getProposedOffers()) {
			if (poffer.getId() == counter_offer_id) {
				poffer.setStatus(status);
				if (status.equals("InTransaction")) {
					exchange_offer.setAmountToRemitSourceCurrency(poffer.getAmount());
					exchange_offer.setOfferStatus(status);
				}	
				break;
			}
		}
		exchangeOfferRepository.save(exchange_offer);
		return exchange_offer;
	}
	
	
	public ExchangeOffer updateTransaction(long offer_id, TransactionDetails trdetails, long offer1) throws Exception {
		ExchangeOffer offer = exchangeOfferRepository.findById(offer_id).orElse(null);
		ExchangeOffer mainOffer = exchangeOfferRepository.findById(offer1).orElse(null);
		
		if (offer == null) {
			return offer;
		}
		
		
		
		trdetails.setExchange_offer(offer);
		trdetails.setBankName(mainOffer.getReceivingBankName());
		trdetails.setAccountNumber(mainOffer.getReceivingAccountNumber());
		trdetails.setCountry(mainOffer.getSourceCountry());
		trdetails.setCurrency(mainOffer.getSourceCurrency());
		mainOffer.setOfferStatus("InTransaction");
		mainOffer.getTransactionDetails().add(trdetails);
	//	offer.getTransactionDetails().add(recv_trdetails);
		exchangeOfferRepository.save(offer);
		return offer;
	}
	
	
}
