/**
 * 
 */
package com.speer.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speer.notes.model.NoteUser;
import com.speer.notes.service.UserService;

/**
 * @author chawl
 *
 */
@RestController
@RequestMapping(value = "/api/auth")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping(value = "/signup")
	private ResponseEntity<NoteUser> signUp(@RequestBody NoteUser noteUser) {
		try {
			NoteUser newUser = service.saveUser(noteUser);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Creation failed");
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	@PostMapping(value = "/login")
	private ResponseEntity<NoteUser> login(@RequestBody NoteUser noteUser) {
		try {
			NoteUser newUser = service.saveUser(noteUser);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Creation failed");
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}

}
