/**
 * 
 */
package com.speer.notes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.speer.notes.model.NoteUser;

/**
 * @author chawl
 *
 */
@Repository
public interface UserRepo extends MongoRepository<NoteUser, String> {

}
