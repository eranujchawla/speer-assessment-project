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
public class AuthenticationRequest {

	private String name;
	private String password;
}
