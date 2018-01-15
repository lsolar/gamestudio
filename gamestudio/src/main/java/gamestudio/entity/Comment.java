package gamestudio.entity;

import java.util.Date;

/*
  CREATE TABLE comment (
  	ident INTEGER PRIMARY KEY,
  	username VARCHAR(32) NOT NULL,
  	game VARCHAR(32) NOT NULL,
  	content VARCHAR(32),
 	createdOn TIMESTAMP NOT NULL
 	);
 */

public class Comment {
	private int ident;
	private String username;
	private String game; 
	private String content;
	private Date createdOn;
	
	public int getIdent() {
		return ident;
	}
	public void setIdent(int ident) {
		this.ident = ident;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	@Override
	public String toString() {
		return "Comment [ident=" + ident + ", username=" + username + ", game=" + game + ", content=" + content
				+ ", createdOn=" + createdOn + "]";
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
