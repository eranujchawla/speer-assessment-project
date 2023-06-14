/**
 * 
 */
package com.speer.notes.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * @author chawl
 *
 */
@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	public String generateToken(UserDetails user) {
		Map<String, String> claims = new HashMap<>();
		String generatedToken = createToken(claims, user.getUsername());
		return generatedToken;
	}

	private String createToken(Map<String, String> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(LocalDateTime.now().getNano()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256, secret )
				.compact();
	}
	
	/**
	 * @param token
	 * @return
	 */
	public String extractUserName(String token) {
		return extract(token, Claims::getSubject);
	}
	
	/**
	 * @param token
	 * @return
	 */
	public Date extractExpirationDate(String token) {
		return extract(token, Claims::getExpiration);
	}
	
	/**
	 * @param token
	 * @return
	 */
	public Boolean isTokenExpired(String token) {
		return extractExpirationDate(token).before(new Date());
	}

	/**
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateUser(String token, UserDetails userDetails) {
		String userName = extractUserName(token);
		return userName.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	/**
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	private <T> T extract(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAll(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * @param token
	 * @return
	 */
	private Claims extractAll(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * @return
	 */
	private Key getKey() {
		byte[] bytesKey = secret.getBytes();
		return Keys.hmacShaKeyFor(bytesKey);
	}
}
