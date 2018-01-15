package gamestudio.server.controller;

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
import gamestudio.game.puzzle.core.Field;
import gamestudio.service.CommentService;
import gamestudio.service.FavoritesService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

public class GuessNumberController {
	@Controller
	@Scope(WebApplicationContext.SCOPE_SESSION)
	public class PuzzleController {
		private long timeStart = System.currentTimeMillis();

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
		
		int maxNumber = 10;
		private String message = "";
		public String getMessage() {
			return message;
		}

		@RequestMapping("/addrating_guessnumber")
		public String rating(@RequestParam(value = "value", required = false) String value, Model model) {

			ratingService.setRating(
					new Rating(userController.getLoggedPlayer().getLogin(), "puzzle", (Integer.parseInt(value))));

			return fillModel(model);
		}

		@RequestMapping("/addcomment_guessnumber")
		public String comment(@RequestParam(value = "content", required = false) String content, Model model) {
			if (!"".equals(content)) {
				commentService.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "guessnumber", content));
			}
			return fillModel(model);
		}

		@RequestMapping("/addfavorite_guessnumber")
		public String addFavorite(@RequestParam(value = "game", required = false) String game, Model model) {
			favoriteService.addFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "guessnumber"));

			return fillModel(model);
		}

//		@RequestMapping("/guessnumber")
//		public String puzzle(@RequestParam(value = "tile", required = false) String tile, Model model) {
//			try {
//				if(sad);
//					message = "EXCELLENT, YOU GOT IT!";
//
//					if (userController.isLogged()) {
//						Score score = new Score();
//						score.setGame("guessbumber");
//						score.setUsername(userController.getLoggedPlayer().getLogin());
//						score.setValue((int) (System.currentTimeMillis() - timeStart) / 1000);
//
//						scoreService.addScore(score);
//					}
//
//					// if (userController.isLogged())
//					// scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(),
//					// "puzzle", 1000-10 * field.getPlayingSeconds());
//				}
//			} catch (NumberFormatException e) {
//			}
//			return fillModel(model);
//
//		}

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


		public String render() {
			StringBuilder sb = new StringBuilder();

			sb.append("<table class='table_puzzle'>\n");
				sb.append("<tr>\n");

			return sb.toString();

		}

	}

}
