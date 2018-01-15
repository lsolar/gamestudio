package gamestudio.service.impl;

import java.util.List;

import gamestudio.entity.Rating;
import gamestudio.service.RatingService;
import orm.SORM;

public class RatingServiceSORM implements RatingService {
	private SORM sorm = new SORM();

	@Override
	public void setRating(Rating rating) {
		sorm.update(rating);
	}

	@Override
	public double getAverateRating(String game) {
		List<Rating> list = sorm.select(Rating.class, String.format("WHERE game = '%s'", game));
		double ratingValues = 0;
		for (Rating rating : list) {
			ratingValues += rating.getValue();

		}
		return ratingValues / list.size();
	}

}
