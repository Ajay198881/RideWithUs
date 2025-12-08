package com.robo.RideWithUs.DTO;

import com.robo.RideWithUs.Entity.Vehicle;

public class VehicleDetail {

	private Vehicle vehicle;
	private int fare;
	private int estimatedTime;
	private String estimatedTimeString;
	public String getEstimatedTimeString() {
		return estimatedTimeString;
	}
	public void setEstimatedTimeString(String estimatedTimeString) {
		this.estimatedTimeString = estimatedTimeString;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public int getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	
	public VehicleDetail(Vehicle vehicle, int fare, int estimatedTime, String estimatedTimeString) {
		super();
		this.vehicle = vehicle;
		this.fare = fare;
		this.estimatedTime = estimatedTime;
		this.estimatedTimeString = estimatedTimeString;
	}
	public VehicleDetail() {
		super();
	}
	
	
}
