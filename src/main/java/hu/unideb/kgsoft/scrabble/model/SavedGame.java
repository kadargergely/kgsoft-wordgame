package hu.unideb.kgsoft.scrabble.model;

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

import hu.unideb.kgsoft.scrabble.Field;

public class SavedGame {
    
    private Field[][] fields;
    private int[] playerTray;
    private int[] computerTray;
    private int tileInHand;
    private boolean playersTurn;
    private int playerScore;
    private int computerScore;
    private String[] lastWords;
    private int lastWordsPoints;
    private int[] bagRemaining;
    
    public SavedGame(Field[][] fields, int[] playerTray, int[] computerTray,
            int tileInHand, int playersTurn, int playerScore,
            int computerScore, String[] lastWords, int lastWordsPoints,
            int[] bagRemaining) {
        this.fields = fields;
        this.playerTray = playerTray;
        this.computerTray = computerTray;
        this.tileInHand = tileInHand;
        if (playersTurn == 1) {
            this.playersTurn = true;
        } else if (playersTurn == 0) {
            this.playersTurn = false;
        }
        this.playerScore = playerScore;
        this.computerScore = computerScore;
        this.lastWords = lastWords;
        this.lastWordsPoints = lastWordsPoints;
        this.bagRemaining = bagRemaining;
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public int[] getPlayerTray() {
        return playerTray;
    }

    public void setPlayerTray(int[] playerTray) {
        this.playerTray = playerTray;
    }

    public int[] getComputerTray() {
        return computerTray;
    }

    public void setComputerTray(int[] computerTray) {
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

    public String[] getLastWords() {
        return lastWords;
    }

    public void setLastWords(String[] lastWords) {
        this.lastWords = lastWords;
    }

    public int getLastWordsPoints() {
        return lastWordsPoints;
    }

    public void setLastWordsPoints(int lastWordsPoints) {
        this.lastWordsPoints = lastWordsPoints;
    }

    public int[] getBagRemaining() {
        return bagRemaining;
    }

    public void setBagRemaining(int[] bagRemaining) {
        this.bagRemaining = bagRemaining;
    }    
}
