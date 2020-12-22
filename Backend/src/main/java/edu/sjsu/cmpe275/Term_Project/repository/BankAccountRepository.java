package edu.sjsu.cmpe275.Term_Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.sjsu.cmpe275.Term_Project.entity.BankAccount;
import edu.sjsu.cmpe275.Term_Project.entity.User;

/**
 * Interface BankAccountRepository which extends the JPA Repository for performing all the CRUD operations
 * @author sumeetdeshpande
 *
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{
	
	/**
	 * Query and method to get number of distinct bank accounts of user based on country
	 * @param user
	 * @return
	 */
	@Query("SELECT COUNT(DISTINCT country) FROM BankAccount b WHERE b.user=?1")
	public int getCountofDistinctAccounts(User user);

	/**
	 * Query and method to get number of distinct bank accounts of user based on
	 * country
	 * 
	 * @param user
	 * @return
	 */
	@Query("SELECT b FROM BankAccount b WHERE b.user=?1")
	public List<BankAccount> getDistinctAccounts(User user);

}
