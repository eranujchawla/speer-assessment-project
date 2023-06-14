/**
 * 
 */
package com.speer.notes.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

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
@Document(collection = "user")
public class CustomUserDetails implements Serializable, UserDetails {

	private static long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String password;

	private String username;

	private Collection<GrantedAuthority> authorities;

	private boolean accountNonExpired;

	private boolean accountNonLocked;

	private boolean credentialsNonExpired;

	private boolean enabled;

	public CustomUserDetails(String name, String password, Collection<GrantedAuthority> grantedAuthority) {
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.username = name;
		this.password = password;
		this.authorities = grantedAuthority;
	}
}
