/**
 * 
 */
package com.speer.notes.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.speer.notes.model.CustomUserDetails;

/**
 * @author chawl
 *
 */
@Repository

public interface CustomUserRepo extends MongoRepository<CustomUserDetails, String> {
	
	List<CustomUserDetails> findByUsername(String username);
	
	
}
