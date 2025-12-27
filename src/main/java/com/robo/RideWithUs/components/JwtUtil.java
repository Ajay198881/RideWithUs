package com.robo.RideWithUs.components;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final long Expiration_Time = 1000 * 60 * 60 * 24; //24 hours time
	// It will generates secret key for validation jwt token and signing purpose. And it is in the form of algorithm
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
	
	// This method will create JWT Token based on user mobileNumber
	public String generateToken(String mobileNmuber) {
		return Jwts.builder()
				.setSubject(mobileNmuber)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + Expiration_Time))
				.signWith(key)
				.compact();
	}
}
