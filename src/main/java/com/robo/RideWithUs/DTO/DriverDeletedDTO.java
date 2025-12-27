package com.robo.RideWithUs.DTO;

public class DriverDeletedDTO {

	 private String driverName;
	 private String mobileNumber;
	 private String status;
	 public String getDriverName() {
		 return driverName;
	 }
	 public void setDriverName(String driverName) {
		 this.driverName = driverName;
	 }
	 public String getMobileNumber() {
		 return mobileNumber;
	 }
	 public void setMobileNumber(String mobileNumber) {
		 this.mobileNumber = mobileNumber;
	 }
	 public String getStatus() {
		 return status;
	 }
	 public void setStatus(String status) {
		 this.status = status;
	 }
	 public DriverDeletedDTO(String driverName, String mobileNumber, String status) {
		super();
		this.driverName = driverName;
		this.mobileNumber = mobileNumber;
		this.status = status;
	 }
	 public DriverDeletedDTO() {
		super();
	 }
	 
	 
}
