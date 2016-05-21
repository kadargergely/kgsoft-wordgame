package hu.unideb.kgsoft.scrabble.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "wordgame_users")
public class UserEntity {
	
	@Id
	@Column(name = "user_id")
	@SequenceGenerator(name = "IdGenerator", sequenceName = "wordgame_user_id_s", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
	private int userId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "passwd")
	private String password;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private SavedGame savedGame;

	public UserEntity() {
	}

	public UserEntity(int userId, String username, String password, SavedGame savedGame) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.savedGame = savedGame;
	}

	public SavedGame getSavedGame() {
		return savedGame;
	}

	public void setSavedGame(SavedGame savedGame) {
		this.savedGame = savedGame;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
}
