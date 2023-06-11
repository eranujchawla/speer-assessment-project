/**
 * 
 */
package com.speer.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speer.notes.model.NoteUser;
import com.speer.notes.repo.UserRepo;

/**
 * @author chawl
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;

	public NoteUser saveUser(NoteUser noteUser) {
		return repo.save(noteUser);
	}

	
}
