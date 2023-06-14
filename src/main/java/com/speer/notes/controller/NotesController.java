/**
 * 
 */
package com.speer.notes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.speer.notes.model.AuthenticationRequest;
import com.speer.notes.model.Note;
import com.speer.notes.service.CustomUserDetailsService;
import com.speer.notes.service.NotesService;

/**
 * @author chawl
 *
 */
@RestController
@RequestMapping("/api/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;

	@Autowired
	private CustomUserDetailsService userService;

	// 1. GET /api/notes: get a list of all notes for the authenticated user.
	@GetMapping
	public ResponseEntity<List<Note>> readAll(@RequestBody AuthenticationRequest user) {
		try {
			userService.validateCorrectUser(user.getName());
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {
			List<Note> list = notesService.getall(user.getName());
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	// 2. GET /api/notes/:id: get a note by ID for the authenticated user.
	@GetMapping("/{id}")
	public ResponseEntity<Note> read(@PathVariable String id, @RequestBody AuthenticationRequest user) {
		try {
			userService.validateCorrectUser(user.getName());
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Optional<Note> note = notesService.getNoteById(user.getName(), id);
		if (note.isPresent()) {
			return new ResponseEntity<>(note.get(), HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// 3. POST /api/notes: create a new note for the authenticated user.
	@PostMapping
	public ResponseEntity<Note> create(@RequestBody Note note) {
		if (note == null || note.getKeywords() == null || note.getUser() == null) {
			return ResponseEntity.badRequest().build();
		}
		try {
			userService.validateCorrectUser(note.getUser());
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {
			Note newNote = notesService.saveNote(note);
			return new ResponseEntity<Note>(newNote, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 4. PUT /api/notes/:id: update an existing note by ID for the authenticated
	// user.
	@PutMapping("/{id}")
	public ResponseEntity<Note> update(@PathVariable("id") String id, @RequestBody Note note) {
		try {
			userService.validateCorrectUser(note.getUser());
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {
			note.setId(id);
			Note updatedNote = notesService.updateNote(note);
			return new ResponseEntity<>(updatedNote, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 5. DELETE /api/notes/:id: delete a note by ID for the authenticated user.
	@DeleteMapping("/{id}")
	public ResponseEntity<Note> remove(@PathVariable("id") String id, @RequestBody AuthenticationRequest user) {
		try {
			userService.validateCorrectUser(user.getName());
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try {
			notesService.deleteNote(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 6. POST /api/notes/:id/share: share a note with another user for the
	// authenticated user.
	@PostMapping("/{id}/share")
	public ResponseEntity<Note> share(@PathVariable("id") String id, @RequestBody AuthenticationRequest user) {
		Optional<Note> note = notesService.getNoteById(id);
		if (note.isPresent()) {
			return ResponseEntity.ok(note.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// 7. GET /api/search?q=:query: search for notes based on keywords for the
	// authenticated user.
	@GetMapping("/search")
	public ResponseEntity<List<Note>> search(@RequestParam(name = "q") String query) {
		try {
			System.out.println("keyword passed : " + query);
			List<Note> list = notesService.searchByKeyword(query);
			System.out.println("no of records : " + list.size());
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
