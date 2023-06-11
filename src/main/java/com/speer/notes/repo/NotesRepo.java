/**
 * 
 */
package com.speer.notes.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.speer.notes.model.Note;

/**
 * @author chawl
 *
 */
@Repository
public interface NotesRepo extends MongoRepository<Note, String>{

	List<Note> findByKeywords(String query);

}
