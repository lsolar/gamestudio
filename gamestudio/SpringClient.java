package Gamestudio;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Gamestudio.game.puzzle.consoleUI.PuzzleConsoleUI;
import Gamestudio.game.puzzle.core.PuzzleField;

@Configuration
@SpringBootApplication
public class SpringClient {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Zadaj hru (Puzzle/Miny)");
		String input = sc.nextLine();

		switch (input) {
		case "Miny":
			SpringApplication.run(MinesGame.class, args);
			break;
		case "Puzzle":
			SpringApplication.run(SpringClient.class, args);
			break;
		}
	}



//puzzle
	@Bean
	public CommandLineRunner runner1(PuzzleConsoleUI ui2) {
		return args -> ui2.run();
	}

	
	@Bean
	public PuzzleConsoleUI puzzleConsoleUI(PuzzleField puzzleField) {
		return new PuzzleConsoleUI(puzzleField);
	}

	@Bean
	public PuzzleField puzzleField() {
		return new PuzzleField(5, 5);
	
	}
}

