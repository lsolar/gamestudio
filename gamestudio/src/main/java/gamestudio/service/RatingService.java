package gamestudio.service;

import gamestudio.entity.Rating;

public interface RatingService {

	void setRating(Rating rating);
	double getAverateRating(String game);
	
}
