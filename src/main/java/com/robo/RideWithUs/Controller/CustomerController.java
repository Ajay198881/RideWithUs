package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.DTO.ActiveBookingDTO;
import com.robo.RideWithUs.DTO.AvailableVehicleDTO;
import com.robo.RideWithUs.DTO.BookingHistoryDTO;

import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.Entity.Bookings;
import com.robo.RideWithUs.Entity.Customer;
import com.robo.RideWithUs.Service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerservice;

	@GetMapping("/findCustomer/{mobileNumber}")
	public ResponseEntity<ResponseStructure<Customer>> findCustomerByMobileNumber(@PathVariable String mobileNumber) {

		return customerservice.findCustomerByMobileNumber(mobileNumber);

	}

	@GetMapping("/seeallAvailablevehicle/{mobileNumber}/{city}")
	public ResponseEntity<ResponseStructure<AvailableVehicleDTO>> sellAllAvailableVehicles(
			@PathVariable String mobileNumber, @PathVariable String city) {

		return customerservice.seeAllAvailableVehicles(mobileNumber, city);
	}

	@DeleteMapping("deleteCustomer/{mobileNumber}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomerByMobileNumber(@PathVariable String mobileNumber) {

		return customerservice.deleteCustomerByMobileNumber(mobileNumber);
	}

	@GetMapping("/seeCustomerBookingHistory")
	public ResponseEntity<ResponseStructure<BookingHistoryDTO>> seeCustomerBookingHistory(
			@RequestHeader String mobileNo) {

		return customerservice.seeCustomerBookingHistory(mobileNo);
	}

	@GetMapping("/findCustomerHaveActiveBookings/{mobileNo}")
	public ResponseEntity<ResponseStructure<ActiveBookingDTO>> findCustomerHaveActiveBookings(
			@PathVariable String mobileNo) {

		return customerservice.seeActiveBooking(mobileNo);
	}

	@PutMapping("/cancelBookingByCustomer")
	public ResponseEntity<ResponseStructure<Bookings>> cancelBookingByCustomer(@RequestHeader int customerID,
			@RequestHeader int bookingID) {
		return customerservice.cancelBookingByCustomer(customerID, bookingID);
	}

}
