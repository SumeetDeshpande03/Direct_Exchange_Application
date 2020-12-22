package edu.sjsu.cmpe275.Term_Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.Term_Project.constants.Constants;
import edu.sjsu.cmpe275.Term_Project.entity.ExchangeOffer;
import edu.sjsu.cmpe275.Term_Project.entity.ProposedOffer;
import edu.sjsu.cmpe275.Term_Project.entity.SplitOffer;
import edu.sjsu.cmpe275.Term_Project.entity.TransactionDetails;
import edu.sjsu.cmpe275.Term_Project.entity.User;
import edu.sjsu.cmpe275.Term_Project.requestModels.AutoSplitMatchRequestModel;
import edu.sjsu.cmpe275.Term_Project.requestModels.ExchangeOfferRequestModel;
import edu.sjsu.cmpe275.Term_Project.requestModels.ProposedOfferModel;
import edu.sjsu.cmpe275.Term_Project.requestModels.TransactionDetailsModel;
import edu.sjsu.cmpe275.Term_Project.requestModels.UpdateCounterOfferDetailsModel;
import edu.sjsu.cmpe275.Term_Project.service.ExchangeOfferService;

/**
 * Controller for all the ExchangeOffer Rest Api request endpoints. This routes the requests to the respective services
 * @author sumeetdeshpande
 *
 */
@RestController
public class ExchangeOfferController {
	
	@Autowired
	private ExchangeOfferService exchangeOfferService;
	
	/**
	 * POST API end point for Exchange Offer
	 * @param exchangeOfferDetailsRequest
	 * @return
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PostMapping("/createExchangeOffer")
	public ResponseEntity createExchangeOffer(@RequestBody ExchangeOfferRequestModel exchangeOfferDetailsRequest) {
		
		ExchangeOffer exchangeOfferDetails = new ExchangeOffer(exchangeOfferDetailsRequest.getSourceCountry(),
				exchangeOfferDetailsRequest.getSourceCurrency(),
				exchangeOfferDetailsRequest.getAmountToRemitSourceCurrency(),
				exchangeOfferDetailsRequest.getDestinationCountry(),
				exchangeOfferDetailsRequest.getDestinationCurrency(),
				exchangeOfferDetailsRequest.getExchangeRate(),
				exchangeOfferDetailsRequest.getExpirationDate(),
				exchangeOfferDetailsRequest.getAllowCounterOffers(),
				exchangeOfferDetailsRequest.getAllowSplitExchanges(),
				exchangeOfferDetailsRequest.getReceivingBankName(),
				exchangeOfferDetailsRequest.getReceivingAccountNumber(),
				exchangeOfferDetailsRequest.getOfferStatus());
		
		String userName = exchangeOfferDetailsRequest.getUserName();
		
		try {
			ExchangeOffer createdExchangeOffer = null;
			
			if(userName!=null && !userName.equals("")) {
				createdExchangeOffer = exchangeOfferService.createExchangeOffer(exchangeOfferDetails, userName);
			} else {
				throw new Exception("User Name not provided");
			}
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(createdExchangeOffer);
			
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
		
	}
    
  
  /**
	 * GET API end point for getOffers
	 * 
	 * @param username
	 * @return list of exchange offers
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@GetMapping("/exchangeOffer/getOffers/{username}")
	public ResponseEntity getOffers(@PathVariable String username) {
		try {
			List<ExchangeOffer> offers = exchangeOfferService.getOffersByUserName(username);
			return ResponseEntity.ok(offers);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}
	
	/**
	 * GET API end point for getAllActiveOffers
	 * 
	 * @param username
	 * @return list of exchange offers
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@GetMapping("/exchangeOffer/getAllActiveOffers/{username}")
	public ResponseEntity getAllActiveOffers(@PathVariable String username) {
		try {
			List<ExchangeOffer> offers = exchangeOfferService.getAllOffersByStatus(username , "Open");
			return ResponseEntity.ok(offers);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}
	
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@GetMapping("/exchangeOffer/getOffer/{id}")
	public ResponseEntity getOffer(@PathVariable long id) {
		try {	
			ExchangeOffer offer = exchangeOfferService.getOfferById(id);
			return ResponseEntity.ok(offer);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}
	
	
	

	/**
	 * POST API End point for exact auto matching offers
	 * @param autoSplitMatchRequestDetails
	 * @return
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PostMapping("/getExactMatchingOffers")
	public ResponseEntity getExactMatchingOffers(@RequestBody AutoSplitMatchRequestModel autoSplitMatchRequestDetails) {
		
		try {
			List<ExchangeOffer> exactMatchingOffers = 
					exchangeOfferService.getExactMatchingOffers(autoSplitMatchRequestDetails.getDestinationCountry(), 
																autoSplitMatchRequestDetails.getDestinationCurrency(),
																autoSplitMatchRequestDetails.getSourceCountry(),
																autoSplitMatchRequestDetails.getSourceCurrency(),
																autoSplitMatchRequestDetails.getUserName(),
																autoSplitMatchRequestDetails.getAmountToRemitInSourceCurrency(),
																autoSplitMatchRequestDetails.getExchangeRate());
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(exactMatchingOffers);
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}
	
	/**
	 * POST API End point for split matching offers
	 * @param autoSplitMatchRequestDetails
	 * @return
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PostMapping("/getSplitMatchingOffers")
	public ResponseEntity getSplitMatchingOffers(@RequestBody AutoSplitMatchRequestModel autoSplitMatchRequestDetails) {
		
		try {
			List<List<ExchangeOffer>> splitMatchingOffers = 
					exchangeOfferService.getSplitMatchingOffers(autoSplitMatchRequestDetails.getDestinationCountry(), 
																autoSplitMatchRequestDetails.getDestinationCurrency(),
																autoSplitMatchRequestDetails.getSourceCountry(),
																autoSplitMatchRequestDetails.getSourceCurrency(),
																autoSplitMatchRequestDetails.getUserName(),
																autoSplitMatchRequestDetails.getAmountToRemitInSourceCurrency(),
																autoSplitMatchRequestDetails.getExchangeRate());
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(splitMatchingOffers);
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}
	
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PutMapping("/exchangeOffer/updateOfferStatusToInTransaction")
	public ResponseEntity updateOfferStatusToInTransaction(@RequestBody TransactionDetailsModel transaction) {
//		System.out.println(transaction);
		try {
			System.out.println("DEBUG: " + transaction.getUserName() + " " + transaction.getAmount() + " " + 
						transaction.getPercentOfTotalAmount() + " " +  transaction.getInverseExRate());
			TransactionDetails trdetails = new TransactionDetails(transaction.getUserName(), transaction.getAmount(), "", "",
																  transaction.getBankName(), transaction.getAccountNumber(),
																  transaction.getPercentOfTotalAmount(), transaction.getInverseExRate(),"","");
			
			ExchangeOffer offer = exchangeOfferService.updateOfferStatusToInTransaction(transaction.getExchangeOfferId(), trdetails);
			if (offer == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
			}
			return ResponseEntity.ok(offer);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
		
	}
	
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PutMapping("/exchangeOffer/updateOfferStatusToInTransactionReverse")
	public ResponseEntity updateOfferStatusToInTransactionReverse(@RequestBody TransactionDetailsModel transaction) {
//		System.out.println(transaction);
		try {
			System.out.println("DEBUG: " + transaction.getUserName() + " " + transaction.getAmount() + " " + 
						transaction.getPercentOfTotalAmount() + " " +  transaction.getInverseExRate());
			TransactionDetails trdetails = new TransactionDetails(transaction.getUserName(), transaction.getAmount(), "", "",
																  transaction.getBankName(), transaction.getAccountNumber(),
																  transaction.getPercentOfTotalAmount(), transaction.getInverseExRate(),"","");
			
			ExchangeOffer offer = exchangeOfferService.updateOfferStatusToInTransactionReverse(transaction.getExchangeOfferId(), trdetails, transaction.getUserName());
			if (offer == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
			}
			return ResponseEntity.ok(offer);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
		
	}
	
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PostMapping("exchangeOffer/updateOfferStatusForCounterOffer")
	public ResponseEntity updateOfferStatusToCounterMade(@RequestBody ProposedOfferModel proposedOffer) {
		try {
			ProposedOffer counterOffer = new ProposedOffer(proposedOffer.getAmount(),
					   proposedOffer.getSplitUserId1() , proposedOffer.getSplitUserId2()  ,
					   proposedOffer.getSplitUser1Amount(),proposedOffer.getSplitUser2Amount());
			ExchangeOffer offer =
				exchangeOfferService.updateOfferStatusForCounterOffer(proposedOffer.getExchangeOfferId(), counterOffer);
			
			if (offer == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
			}
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(offer);
			
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}
	
	
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PostMapping("exchangeOffer/updateOfferStatusForCounter")
	public ResponseEntity updateOfferStatusToCounter(@RequestBody ProposedOfferModel proposedOffer) {
		try {
			ProposedOffer counterOffer = new ProposedOffer(proposedOffer.getAmount(),
					   proposedOffer.getSplitUserId1() , proposedOffer.getSplitUserId2()  ,
					   proposedOffer.getSplitUser1Amount(),proposedOffer.getSplitUser2Amount());
			
			ExchangeOffer offer =
				exchangeOfferService.updateOfferStatusForCounter(proposedOffer.getExchangeOfferId(), counterOffer, proposedOffer.getOfferStatusChange());
			
		
			if (offer == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
			}
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(offer);
			
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}
	
	
	
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PutMapping("exchangeOffer/updateCounterOffer")
	public ResponseEntity updateCounterOffer(@RequestBody UpdateCounterOfferDetailsModel updateCounterOffers) {
		try {
			
			long exchangeOfferId = updateCounterOffers.getExchangeOfferId();
			long counterOfferId = updateCounterOffers.getCounterOfferId();
			String status = updateCounterOffers.getStatus();
			
			ExchangeOffer offer =
				exchangeOfferService.updateCounterOffer(exchangeOfferId, counterOfferId, status);
			
			
			if (offer == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
			}
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(offer);
			
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}
	

	
	
	
}
