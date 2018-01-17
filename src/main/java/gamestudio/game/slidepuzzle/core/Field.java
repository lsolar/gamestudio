package gamestudio.game.slidepuzzle.core;

import java.util.Random;

public class Field {

	private final int rowCount;
	private final int columnCount;
	private final int[][] tiles;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new int[rowCount][columnCount];
		generate();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getTile(int row, int column) {
		return tiles[row][column];
	}

	private void generate() {
		int tile = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = tile;
				tile++;
			}
		}

		shuffle();

	}

	private void shuffle() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
			}
		}
	}

	private Coordinate getTileCoordinate(int tile) {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == tile) {
					return new Coordinate(row, column);

				}
			}
		}
		return null;
	}

	public boolean isSolved() {
		int tile = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] != tile)
					return false;
				tile++;
			}
		}
		return true;
	}

	// public void moveTile(int tile) {
	// if (tile <= 0 || tile >= rowCount * columnCount)
	//
	// Coordinate tileCoordinate = getTileCoordinate(tile);
	// Coordinate tile2Coordinate = getTileCoordinate(tile);
	//
	// if ((tileCoordinate.getRow() == emptyCoordinate.getRow()
	// && Math.abs(tileCoordinate.getColumn() - emptyCoordinate.getColumn()) == 1)
	// || (tileCoordinate.getColumn() == emptyCoordinate.getColumn()
	// && Math.abs(tileCoordinate.getRow() - emptyCoordinate.getRow()) == 1)) {
	// tiles[tile2Coordinate.getRow()][tile2Coordinate.getColumn()] = tile;
	// tiles[tileCoordinate.getRow()][tileCoordinate.getColumn()] = tile2;
	// }
	// }

}