package edu.sjsu.cmpe275.Term_Project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.sjsu.cmpe275.Term_Project.entity.ExchangeOffer;
import edu.sjsu.cmpe275.Term_Project.entity.TransactionDetails;
import edu.sjsu.cmpe275.Term_Project.entity.User;

/**
 * Interface ExchangeOfferRepository which extends the JPA Repository for performing all the CRUD operations
 * @author sumeetdeshpande
 *
 */
public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Long>{
	
	/**
	 * Query to get the probable matching offers of user based on destination and source country	
	 * @param destinationCountry
	 * @param destinationCurrency
	 * @param sourceCountry
	 * @param sourceCurrency
	 * @return
	 */
	@Query("SELECT e FROM ExchangeOffer e WHERE e.destinationCountry=?1 AND e.destinationCurrency=?2 AND e.sourceCountry=?3 AND e.sourceCurrency=?4 AND e.user!=?5 AND e.offerStatus='Open'")
	public List<ExchangeOffer> getMatchingExchangeOffersBasedOnCountry(String destinationCountry, String destinationCurrency, 
																		String sourceCountry, String sourceCurrency, User user);
	
	/**
	 * Query to get the all offers except user's offers with status as active
	 * @param offer_status
	 * @return
	 */
	@Query("SELECT e FROM ExchangeOffer e WHERE e.user!=?1 AND e.offerStatus=?2")
	public List<ExchangeOffer> getAllOffersByStatus(User user, String offerStatus);

	/**
	 * Query to get the probable split matching offers of user based on destination and source country	
	 * @param destinationCountry
	 * @param destinationCurrency
	 * @param sourceCountry
	 * @param sourceCurrency
	 * @return
	 */
	@Query("SELECT e FROM ExchangeOffer e WHERE e.destinationCountry=?1 AND e.destinationCurrency=?2 AND e.sourceCountry=?3 AND e.sourceCurrency=?4 AND e.user!=?5 AND e.offerStatus='Open' AND e.allowSplitExchanges='Allow'")
	public List<ExchangeOffer> getSplitMatchingExchangeOffersBasedOnCountry(String destinationCountry, String destinationCurrency, 
																		String sourceCountry, String sourceCurrency, User user);
	
	@Transactional

    @Modifying(clearAutomatically = true)
	@Query("UPDATE ExchangeOffer e set e.offerStatus = 'Expired' WHERE e.id =?1")
	public ExchangeOffer updateOfferStatus(Long id);
}
