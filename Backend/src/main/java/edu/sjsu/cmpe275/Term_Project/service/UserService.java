package edu.sjsu.cmpe275.Term_Project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.Term_Project.entity.User;
import edu.sjsu.cmpe275.Term_Project.repository.UserRepository;

/**
 * User Service which makes the call to the Repository for performing CRUD
 * operations
 * 
 * @author sumeetdeshpande
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Creates user and adds it to the database
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User createUser(User user) throws Exception {
		return userRepository.save(user);
	}

	/**
	 * Gets the user by user id
	 * 
	 * @param id
	 * @return
	 */
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}
	
	public User updateUser(User user) throws Exception {
		return userRepository.save(user);
	}

}
