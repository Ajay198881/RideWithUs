package com.robo.RideWithUs.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.robo.RideWithUs.DTO.LoginDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.User;
import com.robo.RideWithUs.Exceptions.CustomerNotFoundException;
import com.robo.RideWithUs.Exceptions.DriverNotFoundException;
import com.robo.RideWithUs.Exceptions.InValidCredientialsForPasswordException;
import com.robo.RideWithUs.Exceptions.InValidRoleException;
import com.robo.RideWithUs.Exceptions.UserNotFoundWithThisMobileNumberException;
import com.robo.RideWithUs.Repository.CustomerRepository;
import com.robo.RideWithUs.Repository.DriverRepository;
import com.robo.RideWithUs.Repository.UserRepository;
import com.robo.RideWithUs.security.JwtUtils;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseStructure<String>> login(LoginDTO logindto) {

        User user = userRepository.findByMobileNumber(logindto.getMobileNumber())
                .orElseThrow(()-> new UserNotFoundWithThisMobileNumberException());

        // ✅ Password validation
        if (!passwordEncoder.matches(logindto.getPassword(), user.getPassword())) {
            throw new InValidCredientialsForPasswordException();
        }

        String token;

        // ✅ Correct string comparison
        if ("CUSTOMER".equals(user.getRole())) {

            Customer customer = customerRepository.findByMobileNumber(user.getMobileNumber())
                    .orElseThrow(()-> new CustomerNotFoundException());

            token = jwtUtil.generateToken(customer.getCustomerName(), user.getRole());

        } else if ("DRIVER".equals(user.getRole())) {

            Driver driver = driverRepository.findByMobileNumber(user.getMobileNumber())
                    .orElseThrow(() -> new DriverNotFoundException());

            token = jwtUtil.generateToken(driver.getDriverName(), user.getRole());

        } else {
            throw new InValidRoleException();
        }

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bearer Token Generated Successfully");
        response.setData(token);

        return new ResponseEntity<ResponseStructure<String>>(response, HttpStatus.OK);
    }
}
