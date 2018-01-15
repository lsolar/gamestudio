package gamestudio;

import java.util.Date;

import gamestudio.entity.Comment;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.CommentServiceJDBC;
import gamestudio.service.impl.CommentServiceSORM;
import gamestudio.service.impl.RatingServiceJDBC;
import gamestudio.service.impl.RatingServiceSORM;
import gamestudio.service.impl.ScoreServiceJDBC;
import gamestudio.service.impl.ScoreServiceSORM;

public class Test {

	public static void main(String[] args) {
		// Score score = new Score();
		// score.setUsername("lukas");
		// score.setGame("mines");
		// score.setValue(100);
		//
		// ScoreService scoreService = new ScoreServiceJDBC();
		// scoreService.addScore(score);
		//
		// System.out.println(scoreService.getTopScore("mines"));

		// Comment comment = new Comment();
		// comment.setUsername("lukas");
		// comment.setGame("mines");
		// comment.setContent("dobra hra");
		// // Date date = new Date();
		// // java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
		// comment.setCreatedOn(new Date());
		//
		// CommentService commentService = new CommentServiceJDBC();
		// commentService.addComment(comment);
		//
		// System.out.println(commentService.getComments("mines"));

		// Rating rating = new Rating();
		// rating.setUsername("peter");
		// rating.setGame("mines");
		// rating.setValue(2);
		//
		// RatingService ratingService = new RatingServiceJDBC();
		// ratingService.setRating(rating);
		//
		// System.out.println(ratingService.getAverateRating("mines"));

		// Score score = new Score();
		// score.setUsername("jaro");
		// score.setGame("mines");
		// score.setValue(100);
		//
		// ScoreService scoreService = new ScoreServiceSORM();
		// scoreService.addScore(score);
		//
		// System.out.println(scoreService.getTopScore("mines"));

		Rating rating = new Rating();
		rating.setUsername("lukas");
		rating.setGame("mines");
		rating.setValue(7);
		rating.setIdent(15);

		RatingService ratingService = new RatingServiceSORM();
		ratingService.setRating(rating);

		Comment comment = new Comment();
		comment.setUsername("ivan");
		comment.setGame("mines");
		comment.setContent("good game");
		Date date = new Date();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
		comment.setCreatedOn(sqlDate);

		CommentService commentService = new CommentServiceSORM();
		commentService.addComment(comment);

	}

}
