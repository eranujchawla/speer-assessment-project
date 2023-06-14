/**
 * 
 */
package com.speer.notes.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speer.notes.model.AuthenticationRequest;
import com.speer.notes.model.AuthenticationResponse;
import com.speer.notes.service.CustomUserDetailsService;
import com.speer.notes.service.JwtService;

/**
 * @author chawl
 *
 */

@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtService jwtService;

	@GetMapping(path = "/test")
	public String test(Principal principal) {
		return "Hello, " + principal.getName() + " !! : \"";
	}

	@PostMapping(path = "/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getName(),
					authenticationRequest.getPassword()));

		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (DisabledException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println("Exception occured " + e.getMessage());
		}
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getName());

		final String jwtToken = jwtService.generateToken(userDetails);
		return new ResponseEntity<>(new AuthenticationResponse(jwtToken), HttpStatus.CREATED);

	}

	@PostMapping(path = "/signup")
	public ResponseEntity<UserDetails> create(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getName(),
					authenticationRequest.getPassword()));

		} catch (Exception e) {
			System.out.println("User not authenticated");
		}
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getName());
		if (userDetails != null) {
			return new ResponseEntity<>(userDetails, HttpStatus.NOT_ACCEPTABLE);
		}

		userDetails = customUserDetailsService.createUser(authenticationRequest);
		return new ResponseEntity<>(userDetails, HttpStatus.CREATED);

	}
}
