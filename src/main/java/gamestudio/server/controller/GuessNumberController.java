package gamestudio.server.controller;

import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Comment;
import gamestudio.entity.Favorite;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.FavoritesService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

public class GuessNumberController {
	@Controller
	@Scope(WebApplicationContext.SCOPE_SESSION)
	public class PuzzleController {
		private long timeStart = System.currentTimeMillis();
		private Scanner scanner;
		Random random = new Random();
		int maxNumber = 10;
		int number = random.nextInt(maxNumber) + 1;

		@Autowired
		private RatingService ratingService;

		@Autowired
		private ScoreService scoreService;

		@Autowired
		private CommentService commentService;

		@Autowired
		private FavoritesService favoriteService;

		@Autowired
		private UserController userController;

		@RequestMapping("/addrating_guessnumber")
		public String rating(@RequestParam(value = "value", required = false) String value, Model model) {

			ratingService.setRating(
					new Rating(userController.getLoggedPlayer().getLogin(), "puzzle", (Integer.parseInt(value))));

			return fillModel(model);
		}

		@RequestMapping("/addcomment_guessnumber")
		public String comment(@RequestParam(value = "content", required = false) String content, Model model) {
			if (!"".equals(content)) {
				commentService
						.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "guessnumber", content));
			}
			return fillModel(model);
		}

		@RequestMapping("/addfavorite_guessnumber")
		public String addFavorite(@RequestParam(value = "game", required = false) String game, Model model) {
			favoriteService.addFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "guessnumber"));

			return fillModel(model);
		}

		@RequestMapping("/guessnumber_ask")
		public String guess(@RequestParam(value = "guess", required = false) String guess, Model model) {

			String message = "";
			try {
				int userGuess = Integer.parseInt(guess);

				if (userGuess < number) {
					message = "My number is higher.";
				} else if (userGuess > number) {
					message = "My nunmber is lower.";
				} else {
					message = "You won, number is " + number;
				}
			} catch (NumberFormatException e) {
				message = "Bad input, try again.";
			}
			model.addAttribute("message", message);
			fillModel(model);
			return "guessnumber";

		}

		private String fillModel(Model model) {

			model.addAttribute("guessnumberController", this);
			model.addAttribute("scores", scoreService.getTopScore("guessnumber"));
			model.addAttribute("comment", commentService.getComments("guessnumber"));
			model.addAttribute("averageGuessnumber", ratingService.getAverageRating("guessnumber"));
			if (userController.isLogged()) {
				model.addAttribute("favorite",
						favoriteService.isFavorite("guessnumber", userController.getLoggedPlayer().getLogin()));
			}
			return "guessnumber";
		}

	}

}
