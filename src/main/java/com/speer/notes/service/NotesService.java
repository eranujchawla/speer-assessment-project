/**
 * 
 */
package com.speer.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speer.notes.model.Note;
import com.speer.notes.repo.NotesRepo;

/**
 * @author chawl
 *
 */
@Service
public class NotesService {
	
	@Autowired
	private NotesRepo repo;
	
	/**
	 * Return all the notes
	 * 
	 * @return
	 */
	public List<Note> getall(String name) {
		return repo.findByUser(name);
	}

	/**
	 * Return Note by id
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Note> getNoteById(String id) {
		return repo.findById(id);
	}
	
	/**
	 * Return Note by id and name
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	public Optional<Note> getNoteById(String user, String id) {
		return repo.findByUserAndId(user, id);
	}
	
	/**
	 * Return Note by id
	 * 
	 * @param id
	 * @return
	 */
	public Note saveNote(Note note) {
		return repo.save(note);
	}
	
	/**
	 * Return Note by id
	 * 
	 * @param id
	 * @return
	 */
	public Note updateNote(Note note) {
		return repo.save(note);
	}

	/**
	 * Delete a note by id
	 * 
	 * @param id
	 */
	public void deleteNote(String id) {
		repo.deleteById(id);
	}

	/**
	 * @param query
	 * @return
	 */
	public List<Note> searchByKeyword(String query) {
		return repo.findByKeywords(query);
	}

}
