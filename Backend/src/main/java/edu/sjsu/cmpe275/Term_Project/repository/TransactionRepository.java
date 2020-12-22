package edu.sjsu.cmpe275.Term_Project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import edu.sjsu.cmpe275.Term_Project.entity.ExchangeOffer;
import edu.sjsu.cmpe275.Term_Project.entity.TransactionDetails;

/**
 * Interface TransactionDetails which extends the JPA Repository for performing all the CRUD operations
 * @author sumeetdeshpande
 *
 */
public interface TransactionRepository extends JpaRepository<TransactionDetails, Long>{
	
	/**
	 * Query and method to get transactions where transaction status is pending and isTransferred is zero
	 * @param userName
	 * @return
	 */
	@Query("SELECT t FROM TransactionDetails t WHERE t.status='InTransaction' AND t.isTransferred='0' AND t.Username=?1")
	public List<TransactionDetails> getPendingTransactionsOfUser(String userName);
	
	/**
	 * Query and method to get all transactions of a user
	 * @param userName
	 * @return
	 */
	@Query("SELECT t FROM TransactionDetails t WHERE t.Username=?1")
	public List<TransactionDetails> getAllTransactionsOfUser(String userName);
	
	/**
	 * Query and method to get all transactions of an exchange offer
	 * @param exchangeOffer
	 * @return
	 */
	@Query("SELECT t FROM TransactionDetails t WHERE t.exchange_offer=?1")
	public List<TransactionDetails> getAllPartiesForAnOffer(ExchangeOffer exchangeOffer);
	
	@Query("SELECT t FROM TransactionDetails t WHERE t.Username=?1 and status='Expired'")
	public List<TransactionDetails> getExpiredTransactions(String userName);
	
	
	public List<TransactionDetails> findAll();
	
//	/**
//	 * Query and method to 
//	 * @param exchangeOffer
//	 * @return
//	 */
//	@Transactional
//
//	@Modifying
//	
//	@Query("UPDATE TransactionDetails t set t.status = 'Expired' WHERE t.id =?1")
//	public TransactionDetails updateTransactionStatus(Long id);
	
//update EntityName m set m.salary = m.salary +10 where m.id = 1


}
