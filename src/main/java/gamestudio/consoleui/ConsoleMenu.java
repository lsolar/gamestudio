package gamestudio.consoleui;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import gamestudio.entity.Comment;
import gamestudio.entity.Favorite;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.FavoritesService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

public class ConsoleMenu {
	private ConsoleGameUI[] games;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private FavoritesService favoriteService;

	private Scanner scanner = new Scanner(System.in);

	public ConsoleMenu(ConsoleGameUI[] games) {
		this.games = games;

	}

	public void show() {
		// scoreService.addScore(new Score("peter", "mines", 12));
		// commentService.addComment(new Comment("ivan", "puzzle","average game"));
		ratingService.setRating(new Rating("jozef", "puzzle", 5));
		ratingService.setRating(new Rating("peter", "puzzle", 4));
		favoriteService.addFavorite(new Favorite("ivan", "mines"));

		System.out.println(scoreService.getTopScore("mines"));

		System.out.println("------------------------------------------------------------");
		System.out.println("List of games:");
		int index = 1;
		for (ConsoleGameUI game : games) {
			double rating = ratingService.getAverageRating(game.getName());
			List<Comment> comment = commentService.getComments(game.getName());
			// List<Favorite> favorite =
			System.out.printf("%d. %s (%.2f/5)\n", index, game.getName(), rating);
			index++;
		}

		System.out.println("------------------------------------------------------------");
		do {
			System.out.println("Select a game or press X for exit: ");
			String line = scanner.nextLine().trim();
			// if ("X".equals(line.to)
			// System.exit(0);
			// for (ConsoleGameUI game : games)
			// if (line.equals(game.getName()))

			try {
				index = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				index = -1;
			}
			// for (ConsoleGameUI game : games)
			// if (line.equals(game.getName()))
			//
		} while (index < 1 || index > games.length);
		ConsoleGameUI game = games[index - 1];
		game.run();
	}
}