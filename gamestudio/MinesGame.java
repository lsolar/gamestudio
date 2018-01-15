package Gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import Gamestudio.game.minesweeper.consoleui.ConsoleUI;
import Gamestudio.game.minesweeper.core.Field;
import Gamestudio.game.puzzle.consoleUI.PuzzleConsoleUI;
import Gamestudio.game.puzzle.core.PuzzleField;

public class MinesGame {
	//Miny
		@Bean
		public CommandLineRunner runner(ConsoleUI ui) {
			return args -> ui.run();
		}

		@Bean
		public ConsoleUI consoleUI(Field field) {
			return new ConsoleUI(field);
		}

		@Bean
		public Field field() {
			return new Field(9, 9, 10);
		}
}
