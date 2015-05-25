package hu.unideb.kgsoft.scrabble.model;

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
