package com.robo.RideWithUs.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.robo.RideWithUs.Exceptions.InvalidTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.err.println("Request Path: " + request.getServletPath());

		if (request.getServletPath().startsWith("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {

			token = authHeader.substring(7);
			System.err.println("Authorization Header = " + authHeader);

			try {
				username = jwtUtils.extractUserName(token);
				System.out.println("Username from token = " + username);

				String role = jwtUtils.extractRole(token);
				System.err.println("Role from token = " + role);

				boolean valid = jwtUtils.isTokenValid(token, username);
				System.out.println("Is token valid = " + valid);

			} catch (Exception e) {
//				 throw new InvalidTokenException();
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid or Expired Token");
				return;
			}
		}

		// Validate token and set authentication context
		if (username != null && token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			String role = jwtUtils.extractRole(token); // ✅ Extract role claim (ADMIN/USER)

			if (jwtUtils.isTokenValid(token, username)) {
				var authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						authorities);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);

				
						SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);

	}

}
