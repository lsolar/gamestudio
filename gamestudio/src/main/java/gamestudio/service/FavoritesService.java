package gamestudio.service;

import java.util.List;

import gamestudio.entity.Favorite;

public interface FavoritesService {
	void addFavorite(Favorite favorite);

	List<Favorite> getFavorite(String username);

	void removeFavorite(String game);
}
