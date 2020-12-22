package edu.sjsu.cmpe275.Term_Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.sjsu.cmpe275.Term_Project.entity.User;

/**
 * Interface User Repository which extends the JPA Repository for performing all
 * the CRUD operations
 * 
 * @author sumeetdeshpande, AmbikaNa
 *
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query("From User where user_name = ?1")
    User findUserByUsername(String username);
}
