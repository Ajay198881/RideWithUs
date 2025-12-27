package com.robo.RideWithUs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DTO.LoginDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.User;
import com.robo.RideWithUs.Exceptions.UserNotFoundWithThisMobileNumberException;
import com.robo.RideWithUs.Repository.UserRepository;
import com.robo.RideWithUs.components.JwtUtil;

@Service
public class LoginService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;

	public ResponseEntity<ResponseStructure<String>> Login(LoginDTO logindto) {
		User user = userRepository.findByMobileNumber(logindto.getMobileNumber()).orElseThrow(()->new UserNotFoundWithThisMobileNumberException());
	   
		String token = jwtUtil.generateToken(user.getMobileNumber());
		
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Bearer Token Generated Sucessfully");
		responseStructure.setData(token);
		
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.CREATED);
		
	
	}

}
