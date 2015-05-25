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
 * Class for holding informations about the state of the game. The view class
 * has a reference to an object of this class to present these informations to
 * the user.
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

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public int getTilesInBag() {
        return tilesInBag;
    }

    public void setTilesInBag(int tilesInBag) {
        this.tilesInBag = tilesInBag;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the wordsCorrect
     */
    public boolean isWordsCorrect() {
        return wordsCorrect;
    }

    /**
     * @param wordsCorrect the wordsCorrect to set
     */
    public void setWordsCorrect(boolean wordsCorrect) {
        this.wordsCorrect = wordsCorrect;
    }

    /**
     * @return the movableTiles
     */
    public boolean isMovableTiles() {
        return movableTiles;
    }

    /**
     * @param movableTiles the movableTiles to set
     */
    public void setMovableTiles(boolean movableTiles) {
        this.movableTiles = movableTiles;
    }

    /**
     * @return the tileInHand
     */
    public boolean isTileInHand() {
        return tileInHand;
    }

    /**
     * @param tileInHand the tileInHand to set
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
     * @return the computerScore
     */
    public int getComputerScore() {
        return computerScore;
    }

    /**
     * @param computerScore the computerScore to set
     */
    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }

    /**
     * @return the playerScore
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * @param playerScore the playerScore to set
     */
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    /**
     * @return the playersTurn
     */
    public boolean isPlayersTurn() {
        return playersTurn;
    }

    /**
     * @param playersTurn the playersTurn to set
     */
    public void setPlayersTurn(boolean playersTurn) {
        this.playersTurn = playersTurn;
    }

    /**
     * @return the currentWords
     */
    public List<String> getCurrentWords() {
        return currentWords;
    }

    /**
     * @param currentWords the currentWords to set
     */
    public void setCurrentWords(List<String> currentWords) {
        this.currentWords = currentWords;
    }

    /**
     * @return the currentWordsPoints
     */
    public int getCurrentWordsPoints() {
        return currentWordsPoints;
    }

    /**
     * @param currentWordsPoints the currentWordsPoints to set
     */
    public void setCurrentWordsPoints(int currentWordsPoints) {
        this.currentWordsPoints = currentWordsPoints;
    }

    /**
     * @return the lastWords
     */
    public List<String> getLastWords() {
        return lastWords;
    }

    /**
     * @param lastWords the lastWords to set
     */
    public void setLastWords(List<String> lastWords) {
        this.lastWords = lastWords;
    }

    /**
     * @return the lastWordsPoints
     */
    public int getLastWordsPoints() {
        return lastWordsPoints;
    }

    /**
     * @param lastWordsPoints the lastWordsPoints to set
     */
    public void setLastWordsPoints(int lastWordsPoints) {
        this.lastWordsPoints = lastWordsPoints;
    }

    /**
     * @return the boardLegal
     */
    public boolean isBoardLegal() {
        return boardLegal;
    }

    /**
     * @param boardLegal the boardLegal to set
     */
    public void setBoardLegal(boolean boardLegal) {
        this.boardLegal = boardLegal;
    }    
    
}
