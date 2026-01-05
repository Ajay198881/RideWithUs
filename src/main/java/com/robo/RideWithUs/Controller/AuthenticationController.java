package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.CustomerRegisterDTO;
import com.robo.RideWithUs.DTO.LoginDTO;
import com.robo.RideWithUs.DTO.RegisterDriverVehicleDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Service.CustomerService;
import com.robo.RideWithUs.Service.DriverService;
import com.robo.RideWithUs.Service.LoginService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	DriverService driverService;

	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<String>> Login(@RequestBody LoginDTO logindto) {
		
		return loginservice.login(logindto);
	}
	
	@PostMapping("/register/Customer")
	public ResponseEntity<ResponseStructure<Customer>> registerCustomer(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
		
		return customerService.registerCustomer(customerRegisterDTO);
	}
	
	@PostMapping("/register/Driver")
	public ResponseEntity<ResponseStructure<Driver>> registerDriver(@RequestBody RegisterDriverVehicleDTO driverVehicleDTO) {
		return driverService.registerDriver(driverVehicleDTO);
	}
	
	
}
