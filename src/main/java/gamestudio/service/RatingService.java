package gamestudio.service;

import gamestudio.entity.Rating;

public interface RatingService {

	void setRating(Rating rating);
	double getAverageRating(String game);
	int getUserValue(String username, String game);
	
}
