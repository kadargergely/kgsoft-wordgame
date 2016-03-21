package hu.unideb.kgsoft.scrabble;

/*
 * #%L
 * kgsoft-scrabble
 * %%
 * Copyright (C) 2015 kgsoft
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Class for holding informations about the state of the game. An instance of
 * this class is used by the controller to communicate the changes of the game's
 * state to the view, which then displays these informations in a form, which is
 * independent from the controller.
 */
public class GameState {
	private int computerScore;
	private int playerScore;
	private boolean playersTurn;
	private List<String> currentWords;
	private int currentWordsPoints;
	private List<String> lastWords;
	private int lastWordsPoints;
	private boolean boardLegal;
	private boolean tileInHand;
	private boolean movableTiles;
	private boolean wordsCorrect;
	private String playerName;
	private int tilesInBag;
	private boolean gameStarted;

	/**
	 * Returns {@code true} if the game has started.
	 * 
	 * @return {@code true} if the game has started, {@code false} otherwise
	 */
	public boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * Sets the boolean value to the {@code gameStarted} field.
	 * 
	 * @param gameStarted
	 *            the value to set
	 */
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * Returns the number of tiles that are currently in the bag.
	 * 
	 * @return the number of tiles that are currently in the bag
	 */
	public int getTilesInBag() {
		return tilesInBag;
	}

	/**
	 * Sets how many tiles are currently in the bag.
	 * 
	 * @param tilesInBag
	 *            the number of tiles to set
	 */
	public void setTilesInBag(int tilesInBag) {
		this.tilesInBag = tilesInBag;
	}

	/**
	 * Returns the name of the human player.
	 * 
	 * @return the playerName the name of the human player
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Sets the name of the player.
	 * 
	 * @param playerName
	 *            the player name to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Returns {@code true} if the words on the board are grammatically correct.
	 * 
	 * @return the wordsCorrect {@code true} if the words on the board are
	 *         grammatically correct, {@code false} otherwise
	 */
	public boolean isWordsCorrect() {
		return wordsCorrect;
	}

	/**
	 * Sets the boolean value for the {@code wordsCorrect} field.
	 * 
	 * @param wordsCorrect
	 *            the value to set
	 */
	public void setWordsCorrect(boolean wordsCorrect) {
		this.wordsCorrect = wordsCorrect;
	}

	/**
	 * Returns {@code true} when there are tiles on the fields, placed on the
	 * board in the current turn.
	 * 
	 * @return the movableTiles {@code true} when there are movable tiles on the
	 *         board, {@code false} otherwise
	 * 
	 */
	public boolean isMovableTiles() {
		return movableTiles;
	}

	/**
	 * Sets the boolean value for the {@code movableTiles} field.
	 * 
	 * @param movableTiles
	 *            the value to set
	 */
	public void setMovableTiles(boolean movableTiles) {
		this.movableTiles = movableTiles;
	}

	/**
	 * Returns {@code true} if there is a tile in the players hand.
	 * 
	 * @return the tileInHand {@code true} if there is a tile in the players
	 *         hand, {@code false} otherwise
	 */
	public boolean isTileInHand() {
		return tileInHand;
	}

	/**
	 * Sets the boolean value of the {@code tileInHand} field.
	 * 
	 * @param tileInHand
	 *            the value to set
	 */
	public void setTileInHand(boolean tileInHand) {
		this.tileInHand = tileInHand;
	}

	/**
	 * Constructor for creating a <code>GameState</code> object with default
	 * values.
	 */
	public GameState() {
		computerScore = 0;
		playerScore = 0;
		playersTurn = true;
		currentWords = new ArrayList<String>();
		currentWordsPoints = 0;
		lastWords = new ArrayList<String>();
		lastWordsPoints = 0;
		tilesInBag = 100;
		gameStarted = false;
	}

	/**
	 * Returns the current score of the computer.
	 * 
	 * @return the current score of the computer
	 */
	public int getComputerScore() {
		return computerScore;
	}

	/**
	 * Sets the current score of the computer.
	 * 
	 * @param computerScore
	 *            the score to set
	 */
	public void setComputerScore(int computerScore) {
		this.computerScore = computerScore;
	}

	/**
	 * Returns the current score of the player.
	 * 
	 * @return the current score of the player
	 */
	public int getPlayerScore() {
		return playerScore;
	}

	/**
	 * Sets the current score of the player.
	 * 
	 * @param playerScore
	 *            the score to set
	 */
	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	/**
	 * Returns {@code true} when the current turn is the player's.
	 * 
	 * @return {@code true} when the current turn is the player's and
	 *         {@code false} otherwise
	 */
	public boolean isPlayersTurn() {
		return playersTurn;
	}

	/**
	 * Sets the boolean value for the {@code playersTurn} field.
	 * 
	 * @param playersTurn
	 *            the value to set
	 */
	public void setPlayersTurn(boolean playersTurn) {
		this.playersTurn = playersTurn;
	}

	/**
	 * Returns the words formed in the current turn as a list of strings.
	 * 
	 * @return the currentWords the words formed in the current turn
	 */
	public List<String> getCurrentWords() {
		return currentWords;
	}

	/**
	 * Sets the words formed in the current turn.
	 * 
	 * @param currentWords
	 *            the words to set
	 */
	public void setCurrentWords(List<String> currentWords) {
		this.currentWords = currentWords;
	}

	/**
	 * Returns the number of points gained in the current turn.
	 * 
	 * @return the number of points gained in the current turn
	 */
	public int getCurrentWordsPoints() {
		return currentWordsPoints;
	}

	/**
	 * Sets the number of points gained in in the current turn.
	 * 
	 * @param currentWordsPoints
	 *            the points to set
	 */
	public void setCurrentWordsPoints(int currentWordsPoints) {
		this.currentWordsPoints = currentWordsPoints;
	}

	/**
	 * Returns the words formed in the preceding turn as a list of strings.
	 * 
	 * @return the words formed in the preceding turn
	 */
	public List<String> getLastWords() {
		return lastWords;
	}

	/**
	 * Sets the words formed in the preceding turn.
	 * 
	 * @param lastWords
	 *            the words to set
	 */
	public void setLastWords(List<String> lastWords) {
		this.lastWords = lastWords;
	}

	/**
	 * Returns the number of points gained by a player in the preceding turn.
	 * 
	 * @return the number of points gained by a player in the preceding turn
	 */
	public int getLastWordsPoints() {
		return lastWordsPoints;
	}

	/**
	 * Sets the number of points gained by a player in the preceding turn.
	 * 
	 * @param lastWordsPoints
	 *            the points to set
	 */
	public void setLastWordsPoints(int lastWordsPoints) {
		this.lastWordsPoints = lastWordsPoints;
	}

	/**
	 * Returns {@code true} if the tiles on the board are placed correctly.
	 * 
	 * @return {@code true} if the tiles on the board are placed correctly,
	 *         {@code false} otherwise
	 */
	public boolean isBoardLegal() {
		return boardLegal;
	}

	/**
	 * Sets the boolean value for the {@code boardLegal} field.
	 * @param boardLegal
	 *            the value to set
	 */
	public void setBoardLegal(boolean boardLegal) {
		this.boardLegal = boardLegal;
	}

}
