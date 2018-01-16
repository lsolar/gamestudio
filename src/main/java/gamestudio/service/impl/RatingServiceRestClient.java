package gamestudio.service.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import gamestudio.entity.Rating;
import gamestudio.service.RatingService;

public class RatingServiceRestClient implements RatingService {
	private static final String URL = "http://localhost:8080/rest/rating";

	@Override
	public void setRating(Rating rating) {

	}

	@Override
	public double getAverageRating(String game) {
		Client client = ClientBuilder.newClient();
		Double o = client.target(URL).path("/" + game).request(MediaType.APPLICATION_JSON).get(Double.class);
		return o != null ? o : 0;
	}

	@Override
	public int getUserValue(String username, String game) {
		return 0;
	}

}
