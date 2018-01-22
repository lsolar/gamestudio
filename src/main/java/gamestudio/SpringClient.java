package gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import gamestudio.consoleui.ConsoleGameUI;
import gamestudio.consoleui.ConsoleMenu;
import gamestudio.game.minesweeper.consoleui.ConsoleUI;
import gamestudio.game.minesweeper.core.Field;
import gamestudio.service.CommentService;
import gamestudio.service.FavoritesService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.CommentServiceRestClient;
import gamestudio.service.impl.FavoritesServiceJPA;
import gamestudio.service.impl.RatingServiceRestClient;
import gamestudio.service.impl.ScoreServiceRestClient;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {
		"gamestudio" }, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "gamestudio.server.*"))
public class SpringClient {

//	public static void main(String[] args) throws Exception {
//		// SpringApplication.run(SpringClient.class, args);
//		new SpringApplicationBuilder(SpringClient.class).web(false).run(args);

//	}

	@Bean
	public CommandLineRunner runner(ConsoleMenu menu) {
		return args -> menu.show();

	}

	@Bean
	public ConsoleMenu menu(ConsoleGameUI[] games) {
		return new ConsoleMenu(games);
	}

	@Bean
	public ConsoleGameUI consoleUI(Field field) {
		return new ConsoleUI(field);
	}

	@Bean
	public Field fieldMines() {
		return new Field(9, 9, 10);

	}

	@Bean
	public ConsoleGameUI consoleUIPuzzle(gamestudio.game.puzzle.core.Field field) {
		return new gamestudio.game.puzzle.consoleui.ConsoleUI(field);
	}

	@Bean
	public gamestudio.game.puzzle.core.Field fieldPuzzle() {
		return new gamestudio.game.puzzle.core.Field(4, 5);
	}

	@Bean
	public CommentService commentservice() {
		return new CommentServiceRestClient();
	}

	@Bean
	public ScoreService scoreservice() {
		return new ScoreServiceRestClient();
	}

	@Bean
	public RatingService ratingservice() {
		return new RatingServiceRestClient();
	}

	@Bean
	public FavoritesService favoriteservice() {
		return new FavoritesServiceJPA();

	}
}
