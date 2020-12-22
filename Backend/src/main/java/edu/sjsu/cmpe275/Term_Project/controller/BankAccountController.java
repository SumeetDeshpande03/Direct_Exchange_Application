package edu.sjsu.cmpe275.Term_Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.Term_Project.constants.Constants;
import edu.sjsu.cmpe275.Term_Project.entity.BankAccount;
import edu.sjsu.cmpe275.Term_Project.requestModels.BankAccountRequestModel;
import edu.sjsu.cmpe275.Term_Project.service.BankAccountService;

/**
 * Controller for all the Bank Account Rest Api request endpoints. This routes the requests to the respective services
 * @author sumeetdeshpande
 *
 */
@RestController
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	/**
	 * POST API end point for Bank Account
	 * @param bankAccountDetails
	 * @return
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@PostMapping("/createBankAccount")
	public ResponseEntity createBankAccount(@RequestBody BankAccountRequestModel bankAccountDetailsRequest) {
		
		BankAccount bankAccountDetails = new BankAccount(bankAccountDetailsRequest.getBankName(),
				bankAccountDetailsRequest.getCountry(),
				bankAccountDetailsRequest.getAccountNumber(),
				bankAccountDetailsRequest.getOwnerName(),
				bankAccountDetailsRequest.getOwnerAddress(),
				bankAccountDetailsRequest.getPrimaryCurrency(),
				bankAccountDetailsRequest.getModeSupported());
		
		String userName = bankAccountDetailsRequest.getUserName();
		
		try {
			
			BankAccount createdBankAccount = null;
			
			if(userName!=null && !userName.equals("")) {
				createdBankAccount = bankAccountService.createBankAccount(bankAccountDetails, userName);
			} else {
				throw new Exception("User Name not provided");
			}
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(createdBankAccount);
			
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
		
		
	}
	
	
	/**
	 * GET API end point for getting distinct bank accounts of user
	 * @param id
	 * @return
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@GetMapping("/getDistinctBankAccountsCountsOfUser/{userName}")
	public ResponseEntity getDistinctBankAccounts(@PathVariable String userName) {
		
		try {
			int count = 0;
			
			if(userName!=null && !userName.equals("")) {
				count = bankAccountService.getDistinctBankAccountsOfUser(userName);
			} else {
				throw new Exception("User Name not provided");
			}
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(count);
		} catch(Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}

	}

	/**
	 * GET API end point for getting bank accounts of user
	 * 
	 * @param id
	 * @return
	 */
	@CrossOrigin(origins = Constants.FRONT_END_URL)
	@GetMapping("/getBankAccountsOfUser/{userName}")
	public ResponseEntity getBankAccounts(@PathVariable String userName) {

		try {
			List<BankAccount> res;
			if (userName != null && !userName.equals("")) {
				res = bankAccountService.getBankAccountsOfUser(userName);
			} else {
				throw new Exception("User Name not provided");
			}
			/**
			 * Return response with status 200
			 */
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			/**
			 * Return status 400 if input is invalid
			 */
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}

	}

}
