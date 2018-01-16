package gamestudio.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import gamestudio.entity.Rating;
import gamestudio.service.RatingService;

@Transactional
public class RatingServiceJPA implements RatingService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void setRating(Rating rating) {
		Rating existRating;
		try {
			existRating = (Rating) entityManager
					.createQuery("SELECT r FROM Rating r where r.game = :game AND r.username = :username")
					.setParameter("game", rating.getGame()).setParameter("username", rating.getUsername())
					.getSingleResult();
			existRating.setValue(rating.getValue());
		} catch (Exception e) {
			entityManager.persist(rating);
		}
	}

	@Override
	public double getAverageRating(String game) {
		Object o = entityManager.createQuery("SELECT AVG(r.value) FROM Rating r WHERE r.game = :game")
				.setParameter("game", game).getSingleResult();
		return o == null ? 0 : (double) o;

	}

	@Override
	public int getUserValue(String username, String game) {
		try {
			return (int) entityManager
					.createQuery("SELECT r.value FROM Rating r WHERE r.game = :game AND r.username = :username")
					.setParameter("game", game).setParameter("username", username).getSingleResult();
		} catch (Exception e) {
			return 0;
		}

	}
}
