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

/**
 * The <code>Player</code> class represents a human player. A
 * <code>Player</code> object stores information about the player's score, tray
 * and tile in hand.
 * 
 * @author gergo
 *
 */
public interface Player {	

	public String getName();
	
	public void setName();
	
	/**
	 * Returns the <code>Tray</code> object representing the tray of the player.
	 * 
	 * @return the <code>Tray</code> object of the <code>Player</code> object
	 */
	public Tray getTray();

	/**
	 * Informs the <code>Player</code> instance, that his/her turn comes.
	 */
	public void giveTurn();

	/**
	 * Inform the <code>Player</code> instance of a new tile placed on the
	 * gameboard.
	 * 
	 * @param gameState
	 */
	public void tilePlacedOnBoard();

	public void gameStarted(GameState gameState);

	public void gameEnded(GameState gameState);

	public void otherPlayerEndedTurn(GameState gameState);
}
