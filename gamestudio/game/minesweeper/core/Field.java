package Gamestudio.game.minesweeper.core;

import java.util.Random;

public class Field {
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	public GameState state = GameState.PLAYING;
	private boolean isWin = false;

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

	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(isMineClear(row, column));

				}

			}
		}

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

	public int isMineClear(int row, int column) {
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

		return mines;
	}
	// int mines = 0;
	//
	// if (row > 0 && column > 0 && tiles[row - 1][column - 1] instanceof Mine)
	// mines++;
	//
	// if (row > 0 && tiles[row - 1][column] instanceof Mine)
	// mines++;
	//
	// if (row > 0 && column < columnCount - 1 && tiles[row - 1][column + 1]
	// instanceof Mine)
	// mines++;
	// if (column > 0 && tiles[row][column - 1] instanceof Mine)
	// mines++;
	//
	// if (column < columnCount - 1 && tiles[row][column + 1] instanceof Mine)
	// mines++;
	//
	// if (row < rowCount - 1 && column >0 && tiles[row + 1][column - 1] instanceof
	// Mine)
	// mines++;
	//
	// if (row < rowCount - 1 && tiles[row + 1][column ] instanceof Mine)
	// mines++;
	//
	// if (row < rowCount - 1 && column > columnCount - 1 && tiles[row + 1][column +
	// 1] instanceof Mine)
	// mines++;
	// return mines;
	//
	// }

	// private boolean isMinesAt(int row, int column) {
	//
	// return row >= 0 && row < getRowCount() && column >= 0 && column <
	// getColumnCount()
	// && tiles[row][column] instanceof Mine;
	//
	// }

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

	public boolean isWin() {
		int count = 0;

		for (int row = 0; row < rowCount ; row++) {
			for (int column = 0; column < columnCount ; column++) {
				Tile tile = getTile(row, column);
				if (tile.getState() ==TileState.OPEN && tile instanceof Clue) {

					count++;

				}
			}
		}

		if (count == (getColumnCount()* getRowCount())- mineCount) {
			return true;
		} else {
			return false;
		}

	}

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == TileState.MARKED) {

		} else if (tile.getState() == TileState.CLOSED) {
			tile.setState(TileState.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			if(((Clue)tile).getValue()== 0){
				opeNeighbourTiles(row,column);
				
			}

		}

	}

	private void opeNeighbourTiles(int row, int column) {
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
}
