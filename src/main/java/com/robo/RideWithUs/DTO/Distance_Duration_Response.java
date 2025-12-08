package com.robo.RideWithUs.DTO;

public class Distance_Duration_Response {

	private double distanceInKm;
    private double durationInMinutes;

    public Distance_Duration_Response(double distanceInKm, double durationInMinutes) {
        this.distanceInKm = distanceInKm;
        this.durationInMinutes = durationInMinutes;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }

    public double getDurationInMinutes() {
        return durationInMinutes;
    }
}
