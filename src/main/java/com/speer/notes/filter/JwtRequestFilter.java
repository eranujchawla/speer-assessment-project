/**
 * 
 */
package com.speer.notes.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.speer.notes.service.CustomUserDetailsService;
import com.speer.notes.service.JwtService;

/**
 * @author chawl
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null) {
			String[] jwtArray = StringUtils.split(authorizationHeader, " ");
//			System.out.println(jwtArray[0]);
//			System.out.println(jwtArray[1]);
			String userName = jwtService.extractUserName(jwtArray[1]);

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userName);

				if (jwtService.validateUser(jwtArray[1], userDetails)) {
					UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userName, null,
							userDetails.getAuthorities());
					upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(upat);
				}

			}
		}
		filterChain.doFilter(request, response);
	}

}
