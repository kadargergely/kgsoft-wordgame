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

/**
 * The <code>Player</code> class represents a human player. A
 * <code>Player</code> object stores information about the player's score, tray
 * and tile in hand.
 * 
 * @author gergo
 *
 */
public class Player {

    private int score;
    private Tray tray;
    private int tileInHand;

    /**
     * Construct a new <code>Player</code> object. Initializes it with no
     * points, an empty tray and no tile in hand.
     */
    public Player() {
        score = 0;
        tray = new Tray();
        tileInHand = -1;
    }

    /**
     * Returns the number of points gained by the player.
     * 
     * @return the number of points gained by the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the player.
     * 
     * @param score
     *            the number of points to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the <code>Tray</code> object representing the tray of the player.
     * 
     * @return the <code>Tray</code> object of the <code>Player</code> object
     */
    public Tray getTray() {
        return tray;
    }

    /**
     * Returns the tile code of the tile which is currently in the hand of the
     * player.
     * 
     * @return the tile code of the tile which is currently in the hand of the
     *         player
     */
    public int getTileInHand() {
        return tileInHand;
    }

    /**
     * Sets the tile code of the tile which is currently in the hand of the
     * player.
     * @param tileInHand
     *            the tile code to set
     */
    public void setTileInHand(int tileInHand) {
        this.tileInHand = tileInHand;
    }
}
