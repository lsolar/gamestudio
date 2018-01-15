package gamestudio.game.minesweeper.core;

import java.util.Random;

public class Field {
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	private long timeStart;
//	public long actualTime;

	private GameState state = GameState.PLAYING;

	private final Tile[][] tiles;

	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	private void generate() {
		generateMines();
		fillWithClues();
//		long timeStart = System.currentTimeMillis();

	}

	private void generateMines() {
		int minesToSet = mineCount;
		Random random = new Random();

		while (minesToSet > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				minesToSet--;
			}
		}

	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null)
					tiles[row][column] = new Clue(countNeighboursMines(row, column));
			}
		}
	}

	// private int countNeighboursMines(int row, int column) {

	// int mines = 0;
	// int rowMinus = row - 1;
	// int columnMinus = column - 1;
	// int rowPlus = row + 1;
	// int columnPlus = column + 1;
	//
	// if (rowMinus >= 0) {
	// if (columnMinus >= 0) {
	// if (tiles[row][column])
	// }
	// }
	//
	// }

	private int countNeighboursMines(int row, int column) {
		int mines = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						if (tiles[aRow][aColumn] instanceof Mine)
							mines++;
					}
				}
			}
		}
		// if (row > 0 && column > 0 && tiles[row - 1][column - 1] instanceof Mine)
		// mines++;
		// if (row > 0 && tiles[row - 1][column] instanceof Mine)
		// mines++;
		// if (row > 0 && column < columnCount - 1 && tiles[row - 1][column + 1]
		// instanceof Mine)
		// mines++;
		//
		// if (column > 0 && tiles[row][column - 1] instanceof Mine)
		// mines++;
		// if (column < columnCount - 1 && tiles[row][column + 1] instanceof Mine)
		// mines++;
		//
		// if (row < rowCount - 1 && column > 0 && tiles[row + 1][column - 1] instanceof
		// Mine)
		// mines++;
		// if (row < rowCount - 1 && tiles[row + 1][column] instanceof Mine)
		// mines++;
		// if (row < rowCount - 1 && column < columnCount - 1 && tiles[row + 1][column +
		// 1] instanceof Mine)
		// mines++;

		// if (isMineAt(row-1, column-1))
		// mines++;
		// if (isMineAt(row-1, column))
		// mines++;
		// if (isMineAt(row-1, column+1))
		// mines++;
		// if (isMineAt(row, column-1))
		// mines++;
		// if (isMineAt(row, column+1))
		// mines++;
		// if (isMineAt(row+1, column-1))
		// mines++;
		// if (isMineAt(row+1, column))
		// mines++;
		// if (isMineAt(row+1, column-1))
		// mines++;
		return mines;

	}

	// private boolean isMineAt(int row, int column) {
	// return row >= 0 && row < rowCount && column >= 0 && column < columnCount &&
	// tiles[row][column] instanceof Mine;
	// }
	
//	public long getStartTime() {
//		return timeStart;
//	}
//	
//	public long getActualTime() {
//		return actualTime;
//	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == TileState.CLOSED) {
			tile.setState(TileState.MARKED);
		} else if (tile.getState() == TileState.MARKED) {
			tile.setState(TileState.CLOSED);
		}
	}

	public void openTile(int row, int column) {
		if (state == GameState.PLAYING) {
			Tile tile = tiles[row][column];
			if (tile.getState() == TileState.CLOSED) {
				tile.setState(TileState.OPEN);
				if (tile instanceof Mine) {
					state = GameState.FAILED;
					System.out.println("Game Over.");
					return;
				}

				if (((Clue) tile).getValue() == 0)
					openNeighbourTiles(row, column);

				if (isSolved()) {
					state = GameState.SOLVED;
					return;

				}
			}
		}
	}

	private void openNeighbourTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						openTile(aRow, aColumn);
					}
				}
			}
		}
	}

	private boolean isSolved() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Tile tile = tiles[row][column];
				if (tile instanceof Clue && tile.getState() != TileState.OPEN)
					return false;
			}
		}
		return true;
	}
}