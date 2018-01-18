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

	private Field field = new Field(3, 3);

	private String message = "";

	public String getMessage() {
		return message;
	}

	@RequestMapping("/addrating_puzzle")
	public String rating(@RequestParam(value = "value", required = false) String value, Model model) {

		ratingService.setRating(
				new Rating(userController.getLoggedPlayer().getLogin(), "puzzle", (Integer.parseInt(value))));

		return fillModel(model);
	}

	@RequestMapping("/addcomment_puzzle")
	public String comment(@RequestParam(value = "content", required = false) String content, Model model) {
		if (!"".equals(content)) {
			commentService.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "puzzle", content));
		}
		return fillModel(model);
	}

	@RequestMapping("/addfavorite_puzzle")
	public String addFavorite(@RequestParam(value = "game", required = false) String game, Model model) {
		favoriteService.addFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "puzzle"));

		return fillModel(model);
	}

	@RequestMapping("/puzzle")
	public String puzzle(@RequestParam(value = "tile", required = false) String tile, Model model) {
		try {
			field.moveTile(Integer.parseInt(tile));
			if (field.isSolved()) {
				message = "EXCELLENT, YOU GOT IT!";

				timeCount();

				// if (userController.isLogged())
				// scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(),
				// "puzzle", 1000-10 * field.getPlayingSeconds());
			}
		} catch (NumberFormatException e) {
			createField();
		}
		return fillModel(model);

	}

	private void timeCount() {
		if (userController.isLogged()) {
			Score score = new Score();
			score.setGame("puzzle");
			score.setUsername(userController.getLoggedPlayer().getLogin());
			score.setValue((int) (System.currentTimeMillis() - timeStart) / 1000);

			scoreService.addScore(score);
		}
	}

	private String fillModel(Model model) {
		model.addAttribute("puzzleController", this);
		model.addAttribute("scores", scoreService.getTopScore("puzzle"));
		model.addAttribute("comment", commentService.getComments("puzzle"));
		model.addAttribute("averagePuzzle", ratingService.getAverageRating("puzzle"));
		if (userController.isLogged()) {
			model.addAttribute("favorite",
					favoriteService.isFavorite("puzzle", userController.getLoggedPlayer().getLogin()));
			model.addAttribute("userRating",
					ratingService.getUserValue(userController.getLoggedPlayer().getLogin(), "puzzle"));
		}
		return "puzzle";
	}

	@RequestMapping("/puzzle_medium")
	public String puzzleMedium(@RequestParam(value = "tile", required = false) String tile, Model model) {
		try {
			field.moveTile(Integer.parseInt(tile));
			if (field.isSolved()) {
				message = "EXCELLENT, YOU GOT IT!";

				timeCount();
			}
		} catch (NumberFormatException e) {
			createFieldMedium();
		}
		return fillModel(model);

	}

	@RequestMapping("/puzzle_hard")
	public String puzzleHard(@RequestParam(value = "tile", required = false) String tile, Model model) {
		try {
			field.moveTile(Integer.parseInt(tile));
			if (field.isSolved()) {
				message = "EXCELLENT, YOU GOT IT!";

				timeCount();

			}
		} catch (NumberFormatException e) {
			createFieldHard();
		}
		return fillModel(model);

	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='table_puzzle'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);
				// String image = null;

				sb.append("<td class='tile'>\n");
				if (!field.isSolved())
					sb.append(String.format("<a id='puzzle-link' href='/puzzle?tile=%d'>\n", tile));
				if (tile > 0) {
					sb.append(tile);
				}
				if (!field.isSolved()) {

					sb.append("</a>\n");
				}
				sb.append("</td>\n");
			}
			sb.append("</tr>\n");
		}

		sb.append("</table>\n");
		return sb.toString();

	}

	private void createField() {
		field = new Field(2, 2);
		message = "";
		timeStart = System.currentTimeMillis();

	}

	private void createFieldMedium() {
		field = new Field(3, 3);
		message = "";
		timeStart = System.currentTimeMillis();

	}

	private void createFieldHard() {
		field = new Field(4, 4);
		message = "";
		timeStart = System.currentTimeMillis();

	}
}
