/**
 * 
 */
package com.speer.notes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chawl
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

	private String jwt;
	
}
