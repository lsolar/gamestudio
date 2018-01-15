package Gamestudio.service;

import java.util.List;

import Gamestudio.entity.Favorite;

public interface FavoriteService {

	
	void setFavorite(Favorite favorite);
	
	List<Favorite> getFavorite(String username);
	
	void removeFavorite(String username, String game);
}
