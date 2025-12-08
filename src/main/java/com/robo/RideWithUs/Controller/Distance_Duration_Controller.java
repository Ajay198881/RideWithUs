package com.robo.RideWithUs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robo.RideWithUs.Service.Distance_Duration_Service;

@RestController
public class Distance_Duration_Controller {

	@Autowired
	Distance_Duration_Service distanceDurationService;
	
	public com.robo.RideWithUs.DTO.Distance_Duration_Response Distance_Duration_Response(
            @RequestParam double sourceLat,
            @RequestParam double sourceLon,
            @RequestParam double destLat,
            @RequestParam double destLon) {

        return distanceDurationService.getDistanceAndDuration(sourceLat, sourceLon, destLat, destLon);
    }
}
