package gamestudio.game.guessnumber.consoleui;

import java.util.Random;
import java.util.Scanner;

import gamestudio.game.guessnumber.core.GameState;

public class ConsoleUI {
	private Scanner scanner;
	int maxNumber = 10;
	Random random = new Random();
	int number = random.nextInt(maxNumber) + 1;
	int userGuess;
	int guess;

	public ConsoleUI() {
		scanner = new Scanner(System.in);
	}

	public void run() {
//		while (state = GameState.PLAYING) {
		while (userGuess != number) {
			System.out.print("Enter your guess: ");
			userGuess = scanner.nextInt();
			guess++;
			if (userGuess < number) {
				System.out.println("My number is higher.");
				System.out.println();
			} else if (userGuess > number) {
				System.out.println("My nunmber is lower.");
				System.out.println();
			} else {
				System.out.println("You won, number is " + number + " and your score is " + guess);
			}
		}
	}
}
