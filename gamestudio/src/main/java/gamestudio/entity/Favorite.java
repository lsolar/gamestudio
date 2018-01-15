package gamestudio.entity;

/*CREATE TABLE favorite (
  ident INTEGER PRIMARY KEY,
  username VARCHAR(32) NOT NULL,
  game VARCHAR(32) NOT NULL
 );
 */

public class Favorite {
	private int ident;
	private String game;
	private String username;

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
