package gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Player;
import gamestudio.service.FavoritesService;
import gamestudio.service.PlayerService;
import gamestudio.service.RatingService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
	@Autowired
	private PlayerService playerService;
	private Player loggedPlayer;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private FavoritesService favoriteService;

	@RequestMapping("/")
	public String index(Model model) {
		fillModel(model);
		return "index3";
	}

	private void fillModel(Model model) {
		model.addAttribute("averageMines", ratingService.getAverageRating("mines"));
		model.addAttribute("averagePuzzle", ratingService.getAverageRating("puzzle"));
		model.addAttribute("averageGuess", ratingService.getAverageRating("guessnumber"));
		model.addAttribute("averageSlide", ratingService.getAverageRating("slide"));
		if (isLogged()) {
			model.addAttribute("favorites", favoriteService.getFavorite(getLoggedPlayer().getLogin()));
		}
	}

	@RequestMapping("/user")
	public String user(Model model) {
		fillModel(model);
		return "login";
	}

	@RequestMapping("/login")
	public String login(Player player, Model model) {
		loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
		fillModel(model);
		return isLogged() ? "index3" : "login";
	}

	@RequestMapping("/logout")
	public String logout(Model model) {
		loggedPlayer = null;
		fillModel(model);
		return "index3";
	}

	@RequestMapping("/register")
	public String register(Player player, Model model) {
		fillModel(model);
		playerService.register(player);
		loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
		return isLogged() ? "index3" : "login";
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}
}
