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
import gamestudio.game.minesweeper.core.Clue;
import gamestudio.game.minesweeper.core.Field;
import gamestudio.game.minesweeper.core.GameState;
import gamestudio.game.minesweeper.core.Tile;
import gamestudio.game.minesweeper.core.TileState;
import gamestudio.service.CommentService;
import gamestudio.service.FavoritesService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {
	private Field field;
	private double rating;
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

	boolean marking = false;

	private String message;

	public String getMessage() {
		return message;
	}

	public boolean isMarking() {
		return marking;
	}

	public double getRating() {
		rating = ratingService.getAverageRating("mines");
		return rating;
	}

	@RequestMapping("/addrating_mines")
	public String rating(@RequestParam(value = "value", required = false) String value, Model model) {

		ratingService
				.setRating(new Rating(userController.getLoggedPlayer().getLogin(), "mines", (Integer.parseInt(value))));

		return fillModel(model);
	}

	@RequestMapping("/addcomment_mines")
	public String comment(@RequestParam(value = "content", required = false) String content, Model model) {
		if (!"".equals(content)) {
			commentService.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "mines", content));
		}
		return fillModel(model);
	}

	@RequestMapping("/addfavorite_mines")
	public String addFavorite(@RequestParam(value = "game", required = false) String game, Model model) {
		favoriteService.addFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "mines"));

		return fillModel(model);
	}

	@RequestMapping("/mines_mark")
	public String mines(Model model) {
		marking = !marking;
		model.addAttribute("minesController", this);
		return "mines";
	}

	@RequestMapping("/mines")
	public String mines(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {

		try {
			if (marking)
				field.markTile(Integer.parseInt(row), Integer.parseInt(column));
			else
				field.openTile(Integer.parseInt(row), Integer.parseInt(column));
			if (field.getState() == GameState.FAILED)
				message = "GAME OVER, TRY AGAIN";
			else if (field.getState() == GameState.SOLVED) {
				message = "YOU WON!";

				timeCount();

			}
		} catch (NumberFormatException e) {
			createField();
		}
		return fillModel(model);
	}

	private void timeCount() {
		if (userController.isLogged()) {
			Score score = new Score();
			score.setGame("mines");
			score.setUsername(userController.getLoggedPlayer().getLogin());
			score.setValue((int) (System.currentTimeMillis() - timeStart) / 1000);
			scoreService.addScore(score);
		}
	}

	@RequestMapping("/mines_medium")
	public String minesMedium(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {

		try {
			if (marking)
				field.markTile(Integer.parseInt(row), Integer.parseInt(column));
			else
				field.openTile(Integer.parseInt(row), Integer.parseInt(column));
			if (field.getState() == GameState.FAILED)
				message = "GAME OVER, TRY AGAIN";
			else if (field.getState() == GameState.SOLVED) {
				message = "YOU WON!";

				timeCount();

			}
		} catch (NumberFormatException e) {
			createFieldMedium();
		}
		return fillModel(model);
	}

	@RequestMapping("/mines_hard")
	public String minesHard(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {

		try {
			if (marking)
				field.markTile(Integer.parseInt(row), Integer.parseInt(column));
			else
				field.openTile(Integer.parseInt(row), Integer.parseInt(column));
			if (field.getState() == GameState.FAILED)
				message = "GAME OVER, TRY AGAIN";
			else if (field.getState() == GameState.SOLVED) {
				message = "YOU WON!";

				timeCount();
			}
		} catch (NumberFormatException e) {
			createFieldHard();
		}
		return fillModel(model);
	}

	private String fillModel(Model model) {
		model.addAttribute("minesController", this);
		model.addAttribute("scores", scoreService.getTopScore("mines"));
		model.addAttribute("comments", commentService.getComments("mines"));
		model.addAttribute("averageMines", ratingService.getAverageRating("mines"));

		if (userController.isLogged()) {
			model.addAttribute("favorite",
					favoriteService.isFavorite("mines", userController.getLoggedPlayer().getLogin()));
			model.addAttribute("userRating",
					ratingService.getUserValue(userController.getLoggedPlayer().getLogin(), "mines"));
		}
		return "mines";
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='mines-table'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				String image = null;

				switch (tile.getState()) {
				case CLOSED:
					image = "closed";
					break;
				case MARKED:
					image = "marked";
					break;
				case OPEN:
					if (tile instanceof Clue)
						image = "open" + ((Clue) tile).getValue();
					else
						image = "mine";
				}

				sb.append("<td>\n");
				if (tile.getState() != TileState.OPEN && field.getState() == GameState.PLAYING)
					sb.append(String.format("<a href='/mines?row=%d&column=%d'>\n", row, column));
				sb.append("<img src='/images/mines/" + image + ".png'>\n");
				if (tile.getState() != TileState.OPEN)
					sb.append("</a>\n");
				sb.append("<td>\n");
			}
			sb.append("</tr>\n");
		}
		sb.append("</table>\n");
		return sb.toString();
	}

	private void createField() {
		field = new Field(5, 5, 1);
		message = "";
		timeStart = System.currentTimeMillis();
	}

	private void createFieldMedium() {
		field = new Field(10, 10, 15);
		message = "";
	}

	private void createFieldHard() {
		field = new Field(13, 13, 35);
		message = "";
	}

}
