package com.robo.RideWithUs.DTO;


public class BookVehicelDTO {

	private int vehicleID;
	private String sourceLocation;
	private String destinationLocation;
	private int fare;
	private int estiamtedTime;
	private int distanceTravelled;
	
	
	public int getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
	public String getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public String getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
	public int getEstiamtedTime() {
		return estiamtedTime;
	}
	public void setEstiamtedTime(int estiamtedTime) {
		this.estiamtedTime = estiamtedTime;
	}
	public int getDistanceTravelled() {
		return distanceTravelled;
	}
	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}
	
	public BookVehicelDTO(int vehicleID, String sourceLocation, String destinationLocation, int fare, int estiamtedTime, int distanceTravelled) {
		super();
		this.vehicleID = vehicleID;
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
		this.fare = fare;
		this.estiamtedTime = estiamtedTime;
		this.distanceTravelled = distanceTravelled;
	}
	public BookVehicelDTO() {
		super();
	}

		
}
