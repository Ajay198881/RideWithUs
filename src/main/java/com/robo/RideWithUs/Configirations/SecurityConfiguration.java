package com.robo.RideWithUs.Configirations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.robo.RideWithUs.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	  @Autowired
	    private JwtFilter jwtFilter;
	   

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http
	            .csrf(csrf -> csrf.disable())
	            .cors(cors -> {})
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/auth/**").permitAll()
	                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
	                .requestMatchers("/customer/**").hasRole("CUSTOMER")
	                .requestMatchers("/driver/**").hasRole("DRIVER")
	                .anyRequest().authenticated()
	            )
	            .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
}
