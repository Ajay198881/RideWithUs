package com.robo.RideWithUs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.robo.RideWithUs.DAO.GetLocation;
import com.robo.RideWithUs.DTO.RegisterDriverVehicleDTO;
import com.robo.RideWithUs.DTO.ResponseStructure;
import com.robo.RideWithUs.DTO.UpdateDriverVehicleLocationDTO;
import com.robo.RideWithUs.Entity.Driver;
import com.robo.RideWithUs.Entity.Vehicle;
import com.robo.RideWithUs.Exceptions.DriverNotFoundExceptionForthisNumber;
import com.robo.RideWithUs.Exceptions.VehicleNotFoundException;
import com.robo.RideWithUs.Repository.DriverRepository;
import com.robo.RideWithUs.Repository.VehicleRepository;

@Service
public class DriverService {
	
	@Autowired
	DriverRepository driverRepository;
	
	@Autowired
	VehicleRepository vehiclerepository;
	
	@Autowired
	GetLocation getLocation;

	public ResponseEntity<ResponseStructure<Driver>> registerDriver(RegisterDriverVehicleDTO driverVehicleDTO) {

		Driver driver = new Driver();

		driver.setLicenseNumber(driverVehicleDTO.getLicenseNumber());
		driver.setUpiID(driverVehicleDTO.getUpiID());
		driver.setDriverName(driverVehicleDTO.getDriverName());
		driver.setAge(driverVehicleDTO.getDriverAge());
		driver.setMobileNumber(driverVehicleDTO.getDriverMobileNumber());
		driver.setGender(driverVehicleDTO.getGender());
		driver.setMailID(driverVehicleDTO.getMailID());

		Vehicle vehicle = new Vehicle();

		
		vehicle.setBrandName(driverVehicleDTO.getVehicleName());
		vehicle.setVehicleNumber(driverVehicleDTO.getVehicleNumber());
		vehicle.setType(driverVehicleDTO.getVehicletype());
		vehicle.setModal(driverVehicleDTO.getVehicleModel());
		vehicle.setCapacity(driverVehicleDTO.getCapacity());
		vehicle.setPricePerKM(driverVehicleDTO.getPriceperKilometer());
		vehicle.setAverageSpeed(driverVehicleDTO.getAverageSpeed());
		
		
		String city = getLocation.getLocation(driverVehicleDTO.getLatitude(), driverVehicleDTO.getLongitude());
		vehicle.setCity(city);
		
		vehicle.setDriver(driver);  
		driver.setVehicle(vehicle);
		
		Driver saveddriver = driverRepository.save(driver);
		
		ResponseStructure<Driver> responseStructure = new ResponseStructure<Driver>();
		responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
		responseStructure.setMessage("Driver saved successfully");
		responseStructure.setData(saveddriver);
		
		return new ResponseEntity<ResponseStructure<Driver>>(responseStructure,HttpStatus.ACCEPTED);
		
		

	}

	

	public ResponseEntity<ResponseStructure<Vehicle>> UpdateDriverVehicleLocation(UpdateDriverVehicleLocationDTO updatelocation) {
	    
	    // 1. Find driver using mobile number
	    Driver driver = driverRepository.findByMobileNumber(updatelocation.getDriverMobileNumber())
	            .orElseThrow(() -> new DriverNotFoundExceptionForthisNumber());

	    // 2. Get the vehicle linked to driver
	    Vehicle vehicle = driver.getVehicle();
	    if (vehicle == null) {
	        throw new VehicleNotFoundException();
	    }

	    // 3. Convert coordinates into city
	    String city = getLocation.getLocation(updatelocation.getLatitude(), updatelocation.getLongitude());

	    // 4. Update only the city
	    vehicle.setCity(city);

	    // 5. Save (this will NOT give error now)
	    Vehicle updatedvehicle = vehiclerepository.save(vehicle);

	    // Response
	    ResponseStructure<Vehicle> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Vehicle Location updated successfully");
	    response.setData(updatedvehicle);

	    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<Driver>> findbyDriverID(long mobileNo) {
		
		Driver driver = driverRepository.findByMobileNumber(mobileNo).orElseThrow(()->new DriverNotFoundExceptionForthisNumber());
		  
		// Response
	    ResponseStructure<Driver> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.FOUND.value());
	    response.setMessage("Driver Found successfully");
	    response.setData(driver);

	    return new ResponseEntity<ResponseStructure<Driver>>(response, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<Driver>> deleteDriverbyID(long mobileNo) {
		
		Driver driver = driverRepository.findByMobileNumber(mobileNo).orElseThrow(()->new DriverNotFoundExceptionForthisNumber());
		
		driverRepository.delete(driver);
		// Response
	    ResponseStructure<Driver> response = new ResponseStructure<>();
	    response.setStatusCode(HttpStatus.MOVED_PERMANENTLY.value());
	    response.setMessage("Driver deleted successfully");
	    response.setData(driver);

	    return new ResponseEntity<ResponseStructure<Driver>>(response, HttpStatus.MOVED_PERMANENTLY);
	}

}
