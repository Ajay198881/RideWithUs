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
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().startsWith("/auth");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getServletPath();
		System.out.println("Request Path: " + path);

		// ✅ Completely bypass JWT logic for auth APIs
		if (path.startsWith("/auth/")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);

		try {
			String username = jwtUtils.extractUserName(token);
			String role = jwtUtils.extractRole(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
					&& jwtUtils.isTokenValid(token, username)) {

				var authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						authorities);

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid or Expired Token");
			return;
		}

		filterChain.doFilter(request, response);
	}

}
