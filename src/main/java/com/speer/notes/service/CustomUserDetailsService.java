package com.speer.notes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.speer.notes.model.AuthenticationRequest;
import com.speer.notes.model.CustomUserDetails;
import com.speer.notes.repo.CustomUserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomUserRepo customUserRepo;

	/**
	 * Overrides the default method by calling database.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<CustomUserDetails> users = customUserRepo.findByUsername(username);
		if (users != null && !users.isEmpty())
			return users.get(0);
		return null;
	}

	/**
	 * Creates a new user in the User collection
	 * 
	 * @param authenticationRequest
	 * @return
	 */
	public UserDetails createUser(AuthenticationRequest authenticationRequest) {
		CustomUserDetails user = new CustomUserDetails(authenticationRequest.getName(),
				authenticationRequest.getPassword(), new ArrayList<GrantedAuthority>());
		return customUserRepo.save(user);
	}

	/**
	 * Method check if the token sent matches the user name passed.
	 * 
	 * @param userInRequest
	 * @throws Exception
	 */
	public void validateCorrectUser(String userInRequest) throws Exception {
		String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		System.out.println("authenticatedUser : " + authenticatedUser);
//		System.out.println("Request for user : " + userInRequest);
		if (!userInRequest.equalsIgnoreCase(authenticatedUser)) {
			throw new Exception(userInRequest + " User not autheticated!");
		}
	}

}
