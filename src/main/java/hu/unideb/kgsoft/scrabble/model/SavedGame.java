package hu.unideb.kgsoft.scrabble.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import hu.unideb.kgsoft.scrabble.utils.Utils;

/**
 * The {@code SavedGame} class is a model class which represents a saved game.
 * It contains all the data needed to load the game.
 * 
 * @author gergo
 *
 */
@Entity
@Table(name = "wordgame_saved_games")
public class SavedGame {

	@Id
	@Column(name = "saved_game_id")
	@SequenceGenerator(name = "IdGenerator", sequenceName = "wordgame_saved_game_id_s", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
	private int savedGameId;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@Column(name = "player_tray")
	private String playerTray;

	@Column(name = "computer_tray")
	private String computerTray;

	@Column(name = "tile_in_hand")
	private int tileInHand;

	@Column(name = "players_turn")
	private boolean playersTurn;

	@Column(name = "player_score")
	private int playerScore;

	@Column(name = "computer_score")
	private int computerScore;

	@Column(name = "last_words")
	private String lastWords;

	@Column(name = "last_words_points")
	private int lastWordsPoints;

	@Column(name = "bag_remaining")
	private String bagRemaining;

	@OneToMany(mappedBy = "savedGame", cascade = CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	private List<FieldEntity> fields;

	public SavedGame() {
	}

	public SavedGame(int savedGameId, UserEntity user, String playerTray, String computerTray, int tileInHand,
			boolean playersTurn, int playerScore, int computerScore, String lastWords, int lastWordsPoints,
			String bagRemaining, List<FieldEntity> fields) {
		this.savedGameId = savedGameId;
		this.user = user;
		this.playerTray = playerTray;
		this.computerTray = computerTray;
		this.tileInHand = tileInHand;
		this.playersTurn = playersTurn;
		this.playerScore = playerScore;
		this.computerScore = computerScore;
		this.lastWords = lastWords;
		this.lastWordsPoints = lastWordsPoints;
		this.bagRemaining = bagRemaining;
		this.fields = fields;
	}

	public SavedGame(int savedGameId, UserEntity user, int[] playerTray, int[] computerTray, int tileInHand,
			boolean playersTurn, int playerScore, int computerScore, List<String> lastWords, int lastWordsPoints,
			int[] bagRemaining, Field[][] fields) {
		this.savedGameId = savedGameId;
		this.user = user;
		this.playerTray = Utils.intArrayToString(playerTray, " ");
		this.computerTray = Utils.intArrayToString(computerTray, " ");
		this.tileInHand = tileInHand;
		this.playersTurn = playersTurn;
		this.playerScore = playerScore;
		this.computerScore = computerScore;		
		StringJoiner lastWordsSj = new StringJoiner(" ");
		for (String w : lastWords) {
			lastWordsSj.add(w);
		}		
		this.lastWords = lastWordsSj.toString();
		this.lastWordsPoints = lastWordsPoints;
		this.bagRemaining = Utils.intArrayToString(bagRemaining, "");
		this.fields = fieldsToList(fields);
	}

	private List<FieldEntity> fieldsToList(Field[][] fields) {
		List<FieldEntity> fieldList = new ArrayList<>();
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				if (fields[i][j].getStatus() != Field.Status.EMPTY) {
					fieldList.add(new FieldEntity(0, this, i, j, fields[i][j].getStatus(), fields[i][j].getTileCode(),
							fields[i][j].getJokerTileCode()));
				}
			}
		}
		return fieldList;
	}

	private Field[][] fieldstoArray(List<FieldEntity> fields) {
		Field[][] fieldArray = new Field[Gameboard.BOARD_SIZE][Gameboard.BOARD_SIZE];
		Iterator<FieldEntity> fieldListIterator = fields.iterator();
		FieldEntity currentField = null;
		if (fieldListIterator.hasNext()) {
			currentField = fieldListIterator.next();
		}
		for (int i = 0; i < fieldArray.length; i++) {
			for (int j = 0; j < fieldArray[i].length; j++) {
				if (currentField.getRowIndex() == i && currentField.getColumnIndex() == j) {
					fieldArray[i][j] = new Field(currentField.getStatus(), null, currentField.getTileCode(),
							currentField.getJokerTileCode());
					if (fieldListIterator.hasNext()) {
						currentField = fieldListIterator.next();
					}
				} else {
					fieldArray[i][j] = new Field();
				}
			}
		}		
		return fieldArray;
	}	

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<FieldEntity> getFields() {
		return fields;
	}

	public void setFields(List<FieldEntity> fields) {
		this.fields = fields;
	}

	public int getSavedGameId() {
		return savedGameId;
	}

	public void setSavedGameId(int savedGameId) {
		this.savedGameId = savedGameId;
	}

	public String getPlayerTray() {
		return playerTray;
	}

	public void setPlayerTray(String playerTray) {
		this.playerTray = playerTray;
	}

	public String getComputerTray() {
		return computerTray;
	}

	public void setComputerTray(String computerTray) {
		this.computerTray = computerTray;
	}

	public int getTileInHand() {
		return tileInHand;
	}

	public void setTileInHand(int tileInHand) {
		this.tileInHand = tileInHand;
	}

	public boolean isPlayersTurn() {
		return playersTurn;
	}

	public void setPlayersTurn(boolean playersTurn) {
		this.playersTurn = playersTurn;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getComputerScore() {
		return computerScore;
	}

	public void setComputerScore(int computerScore) {
		this.computerScore = computerScore;
	}

	public String getLastWords() {
		return lastWords;
	}

	public void setLastWords(String lastWords) {
		this.lastWords = lastWords;
	}

	public int getLastWordsPoints() {
		return lastWordsPoints;
	}

	public void setLastWordsPoints(int lastWordsPoints) {
		this.lastWordsPoints = lastWordsPoints;
	}

	public String getBagRemaining() {
		return bagRemaining;
	}

	public void setBagRemaining(String bagRemaining) {
		this.bagRemaining = bagRemaining;
	}

	public int[] getPlayerTrayTileCodes() {
		return Utils.stringToIntArray(playerTray, " ");
	}

	public int[] getComputerTrayTileCodes() {
		return Utils.stringToIntArray(computerTray, " ");
	}

	public int[] getBagRemainingAsArray() {
		return Utils.stringToIntArray(bagRemaining, "");
	}

	public Field[][] getFieldsAsTwoDimensionalArray() {
		return fieldstoArray(fields);
	}
}
