package edu.sjsu.cmpe275.Term_Project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.Term_Project.entity.BankAccount;
import edu.sjsu.cmpe275.Term_Project.entity.User;
import edu.sjsu.cmpe275.Term_Project.repository.BankAccountRepository;
import edu.sjsu.cmpe275.Term_Project.repository.UserRepository;


/**
 * BankAccountService which makes the call to the Repository for performing CRUD operations
 * @author sumeetdeshpande
 *
 */
@Service
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Service to create bank account
	 * @param bankAccount
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public BankAccount createBankAccount(BankAccount bankAccount, String userName) throws Exception{
		
		User user = userRepository.findById(userName).orElse(null);
		if(user==null) {
			throw new Exception("User does not exist");
		}	
		bankAccount.setUser(user);	
		return bankAccountRepository.save(bankAccount);
		
	}
	
	/**
	 * Service to validate the number of distinct bank accounts user has created
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public int getDistinctBankAccountsOfUser(String userName) throws Exception{
		
		User user = userRepository.findById(userName).orElse(null);		
		if(user==null) {
			throw new Exception("User does not exist");
		}	
		int count = bankAccountRepository.getCountofDistinctAccounts(user);
		return count;
		
	}

	/**
	 * Service to validate the number of distinct bank accounts user has created
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<BankAccount> getBankAccountsOfUser(String userName) throws Exception {

		User user = userRepository.findById(userName).orElse(null);
		if (user == null) {
			throw new Exception("User does not exist");
		}
		List<BankAccount> response = bankAccountRepository.getDistinctAccounts(user);
		return response;

	}

}
