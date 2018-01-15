package Gamestudio.game.minesweeper.consoleui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Gamestudio.game.minesweeper.core.Clue;
import Gamestudio.game.minesweeper.core.Field;
import Gamestudio.game.minesweeper.core.GameState;
import Gamestudio.game.minesweeper.core.Mine;
import Gamestudio.game.minesweeper.core.Tile;

public class ConsoleUI {
	private static final String URL = "jdbc:postgresql://localhost/test";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Postgres1234";
	private Field field ;
	private Scanner scanner;
	private static final Pattern INPUT_PATTERN = Pattern.compile("([OM])([A-I])([1-9])");
	
	public ConsoleUI(Field field) {
		this.field = field;
		scanner = new Scanner(System.in);
	}
	
	public void run() throws SQLException {
		print();
		
		long startTime = System.currentTimeMillis();
		while(field.getState() == GameState.PLAYING) {
			processInput();
			print();
		}
		long finalTime = (System.currentTimeMillis() - startTime) / 1000;
		if(field.getState() == GameState.SOLVED) {
			System.out.println("Gratulujem vyhral si v case " + finalTime + " s.");
		} else if(field.getState() == GameState.FAILED) {
			System.out.println("Prehral si v case " + finalTime + " s.");
		}
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement statement = connection.createStatement()){
				statement.executeUpdate("INSERT INTO score(id, cas) VALUES ((nextval('score_seq')),' " + finalTime + "')");
				
		}
	}
	
	
	
	private void processInput() {
		System.out.print("Zadaj cinnost (MA1, OC4, X) ");
		String input = scanner.nextLine().trim().toUpperCase();
		
		if("X".equals(input)) {
			System.exit(0);
		}
		
		Matcher matcher = INPUT_PATTERN.matcher(input);
		if(matcher.matches()) {
			int row = matcher.group(2).charAt(0) - 'A';
			int column = Integer.parseInt(matcher.group(3)) - 1;

			if("O".equals(matcher.group(1))) {
				field.openTile(row, column);
			} else {
				field.markTile(row, column);
			}
		} else {
			System.out.println("Zle zadany vstup!");	
		}
	}

	private void print() {
		System.out.print("  ");
		for (int column = 1; column <= field.getColumnCount(); column++) {
			System.out.print(column + " ");
		}
		System.out.println();
		
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print((char)(row + 'A'));
			for (int column = 0; column < field.getColumnCount(); column++) {
				System.out.print(" ");
				Tile tile = field.getTile(row, column);
				switch (tile.getState()) {
				case CLOSED:
					System.out.print('-');
					break;
				case MARKED:
					System.out.print('M');
					break;
				case OPEN:
					if (tile instanceof Mine) {
						System.out.print('X');
					} else if (tile instanceof Clue) {
						System.out.print(((Clue) tile).getValue());
					}
					break;
				}
			}
			System.out.println();
		}
	}
}
