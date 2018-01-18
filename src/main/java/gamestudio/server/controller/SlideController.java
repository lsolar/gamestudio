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
import gamestudio.game.slidepuzzle.core.Field;
import gamestudio.service.CommentService;
import gamestudio.service.FavoritesService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SlideController {
	private long timeStart = System.currentTimeMillis();
	private int markedTile;

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

	@RequestMapping("/addrating_slide")
	public String rating(@RequestParam(value = "value", required = false) String value, Model model) {

		ratingService
				.setRating(new Rating(userController.getLoggedPlayer().getLogin(), "slide", (Integer.parseInt(value))));

		return fillModel(model);
	}

	@RequestMapping("/addcomment_slide")
	public String comment(@RequestParam(value = "content", required = false) String content, Model model) {
		if (!"".equals(content)) {
			commentService.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "slide", content));
		}
		return fillModel(model);
	}

	@RequestMapping("/addfavorite_slide")
	public String addFavorite(@RequestParam(value = "game", required = false) String game, Model model) {
		favoriteService.addFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "slide"));

		return fillModel(model);
	}

	@RequestMapping("/slide")
	public String slide(@RequestParam(value = "tile", required = false) String tile, Model model) {
		int scoreTime = 0;
		try {
			if (markedTile == 0) {
				markedTile = Integer.parseInt(tile);
			} else {

				field.moveTile(Integer.parseInt(tile), markedTile);
				markedTile = 0;

				if (field.isSolved()) {
					message = "GREAT, YOU'VE DONE IT" + scoreTime + " !";
					Score score = new Score();
					scoreTime = ((int) (System.currentTimeMillis() - timeStart) / 1000);

					if (userController.isLogged()) {
						score.setGame("slide");
						score.setUsername(userController.getLoggedPlayer().getLogin());
						score.setValue(scoreTime);

						scoreService.addScore(score);
					}
				}
			}

		} catch (NumberFormatException e) {
			createField();
		}
		return fillModel(model);

	}

	private String fillModel(Model model) {
		model.addAttribute("slideController", this);
		model.addAttribute("scores", scoreService.getTopScore("slide"));
		model.addAttribute("comment", commentService.getComments("slide"));
		model.addAttribute("averageSlide", ratingService.getAverageRating("slide"));
		if (userController.isLogged()) {
			model.addAttribute("favorite",
					favoriteService.isFavorite("slide", userController.getLoggedPlayer().getLogin()));
			model.addAttribute("userRating",
					ratingService.getUserValue(userController.getLoggedPlayer().getLogin(), "slide"));
		}
		return "slide";
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='table_slide'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);

				sb.append("<td>\n");
				if (!field.isSolved())
					sb.append(String.format("<a href='/slide?tile=%d'>\n", tile));
				sb.append("<img src='/images/slidepuzzle/" + tile + ".jpg'>\n");

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
		field = new Field(3, 3);
		message = "";
		markedTile = 0;
		timeStart = System.currentTimeMillis();

	}

}
