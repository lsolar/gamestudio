package Gamestudio.game.puzzle.consoleUI;

import Gamestudio.game.puzzle.core.PuzzleField;

public class PuzzleConsoleUI {
	private PuzzleField puzzleField ;

	public PuzzleConsoleUI(PuzzleField puzzleField) {
       this.puzzleField = puzzleField;
	}

	public void run() {
		puzzleField.fillWithNumbers();
		puzzleField.shuffle();
		print();
		while(!puzzleField.isSolved()) {
		puzzleField.FindNumber();
		print();
		}
		

	}

	private void print() {

		int rowCount = puzzleField.getRowCount();
		int columnCount = puzzleField.getColumnCount();

		for (int row = 0; row < rowCount; row++) {
			System.out.println(" ");
			for (int column = 0; column < columnCount; column++) {
				int number = puzzleField.getNumber(row, column);

				System.out.print(" ");
				if (number == (rowCount * columnCount)) {
					System.out.print("   ");
				}
				if (number < (rowCount * columnCount)) {
					if (number < 10) {
						System.out.print(number + "  ");
					} else {
						System.out.print(number + " ");
					}

				}

			}
			System.out.println(" ");

		}

		// for (int [] row : field.puzzles) {
		// System.out.println(" ");
		// for (int numbers: row) {
		// if(numbers == field.getLastNumber()) {
		// System.out.println(" ");
		// }else if(numbers <10) {
		// System.out.println(" ");
		// System.out.println(" " + numbers);
		// }else if(numbers < 100) {
		// System.out.println(" ");
		// System.out.println(" "+ numbers);
		// }else {
		// System.out.println(" " + numbers);
		// }
		//
		//
		// }

	}

}
