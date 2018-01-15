package Gamestudio.game.puzzle.core;

import java.util.Random;
import java.util.Scanner;

public class PuzzleField {
	private int rowCount;
	private int columnCount;
	public int[][] puzzles;
	private Scanner scanner;

	public PuzzleField(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		puzzles = new int[rowCount][columnCount];

	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void fillWithNumbers() {
		int number = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				puzzles[row][column] = number;
				number++;

			}

		}
	}

	public void moveRight(int row, int column) {
		swap(puzzles, row, column, row, column + 1);
	}

	public void moveLeft(int row, int column) {
		swap(puzzles, row, column, row, column - 1);
	}

	public void moveDown(int row, int column) {
		swap(puzzles, row, column, row + 1, column);

	}

	public void moveUp(int row, int column) {
		swap(puzzles, row, column, row - 1, column);
	}

	public int getNumber(int row, int column) {
		return puzzles[row][column];
	}

	public boolean isSolved() {
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (puzzles[i][j] + 1 != getNumber(i, j)) {
					return false;
				}
			}
		}
		return true;

	}

	public void swap(int[][] puzzles, int row, int column, int aRow, int aColumn) {
		int temp = puzzles[row][column];
		puzzles[row][column] = puzzles[aRow][aColumn];
		puzzles[aRow][aColumn] = temp;

	}

	public void shuffle() {
		Random random = new Random();
		int shuffleNumber = random.nextInt(3);
		for (int i = 0; i < 1000; i++) {
			if (shuffleNumber == 1) {
				moveLeft(random.nextInt(getRowCount()-1),random.nextInt(getColumnCount()-1));
			} else if (shuffleNumber == 2) {
				moveDown(random.nextInt(getRowCount()-1),random.nextInt(getColumnCount()-1));
			} else if (shuffleNumber == 3) {
				moveRight(random.nextInt(getRowCount()-1),random.nextInt(getColumnCount()-1));
			} else if (shuffleNumber == 4) {
				moveUp(random.nextInt(getRowCount()-1),random.nextInt(getColumnCount()-1));
			}
		}
	}

	public void FindNumber() {
		int maxNumber = (getRowCount() * getColumnCount());
		System.out.println("input number");
		Scanner sc = new Scanner(System.in);
		String Input1 = sc.nextLine();
		int Input = 0;
		try {
			Input = Integer.parseInt(Input1);
		} catch (NumberFormatException e) {
			System.out.println("Write number");
			
		}
		for (int row = 0; row < rowCount; ++row) {
			for (int column = 0; column < columnCount; ++column) {
				if (puzzles[row][column] == Input) {
					if (column < columnCount - 1 && puzzles[row][column + 1] == maxNumber) {
						moveRight(row, column);
						return;

					} else if (column > 0 && puzzles[row][column - 1] == maxNumber) {
						moveLeft(row, column);

					}
					if (row < rowCount - 1 && puzzles[row + 1][column] == maxNumber) {
						moveDown(row, column);

						return;
					} else if (row > 0 && puzzles[row - 1][column] == maxNumber) {
						moveUp(row, column);

					}

				}
			}
		}

	}

}
