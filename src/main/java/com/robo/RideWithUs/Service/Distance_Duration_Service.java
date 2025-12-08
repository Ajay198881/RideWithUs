package com.robo.RideWithUs.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.robo.RideWithUs.DTO.Distance_Duration_Response;
import com.robo.RideWithUs.Exceptions.DistanceDurationAPIFailedException;

@Service
public class Distance_Duration_Service {

	private static final String API_KEY = "pk.9f97384d7176ae66c2b751ed432be655"; // Use your key here
	
	public Distance_Duration_Response getDistanceAndDuration(
	        double sourceLat, double sourceLon,
	        double destLat, double destLon) {

	    RestTemplate restTemplate = new RestTemplate();

	    String url = "https://us1.locationiq.com/v1/matrix/driving/" +
	            sourceLon + "," + sourceLat + ";" +
	            destLon + "," + destLat +
	            "?key=" + API_KEY +
	            "&sources=0" +
	            "&destinations=1" +
	            "&annotations=distance,duration";

	    Map<String, Object> response = restTemplate.getForObject(url, Map.class);

	    if (response == null || !response.containsKey("durations") || !response.containsKey("distances")) {
	        throw new DistanceDurationAPIFailedException();
	    }


	    // Extract distances as List<List<Number>>
	    List<List<Number>> distances = (List<List<Number>>) response.get("distances");
	    Number distanceValue = distances.get(0).get(0);
	    double distanceMeters = distanceValue.doubleValue();

	    // Extract durations as List<List<Number>>
	    List<List<Number>> durations = (List<List<Number>>) response.get("durations");
	    Number durationValue = durations.get(0).get(0);
	    double durationSeconds = durationValue.doubleValue();

	    double distanceKm = distanceMeters / 1000.0;
	    double durationMinutes = durationSeconds / 60.0;


	    return new Distance_Duration_Response(distanceKm, durationMinutes);
	}



}
