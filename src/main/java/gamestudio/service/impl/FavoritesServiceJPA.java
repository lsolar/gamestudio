package gamestudio.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import gamestudio.entity.Favorite;
import gamestudio.service.FavoritesService;

@Transactional
public class FavoritesServiceJPA implements FavoritesService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addFavorite(Favorite favorite) {
		try {
			entityManager.createQuery("SELECT f FROM Favorite f WHERE f.game = :game AND f.username = :username")
					.setParameter("game", favorite.getGame()).setParameter("username", favorite.getUsername())
					.getSingleResult();
			entityManager.createQuery("DELETE FROM Favorite f WHERE f.game = :game AND f.username = :username")
					.setParameter("game", favorite.getGame()).setParameter("username", favorite.getUsername())
					.executeUpdate();
		} catch (NoResultException e) {
			entityManager.persist(favorite);
		}
	}

	@Override
	public List<Favorite> getFavorite(String username) {
		return entityManager.createQuery("SELECT f FROM Favorite f WHERE f.username = :username")
				.setParameter("username", username).getResultList();
	}

	@Override
	public void removeFavorite(String game) {

	}

	@Override
	public boolean isFavorite(String game, String username) {
		try {
			entityManager.createQuery("SELECT f FROM Favorite f WHERE f.game = :game AND f.username = :username")
					.setParameter("game", game).setParameter("username", username).getSingleResult();

		} catch (NoResultException e) {
			return false;
		}
		return true;
	}

}
