package hu.unideb.kgsoft.scrabble;

import hu.unideb.kgsoft.scrabble.model.GameState;

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
 * An interface defining common services a view class for the game should
 * provide. The abstract methods defined here will be called by the controller
 * class to update the view.
 */
public interface GameView {
	/**
	 * Draws an image of the tile, which is located on the board at the given
	 * row and column indices with the given tile code. Calculates the x and y
	 * coordinates corresponding to the row and column indices on the board, to
	 * draw the image on the graphics surface.
	 * 
	 * @param tileCode
	 *            the unique code of the tile, that has to be drawn
	 * @param row
	 *            the row index of the tile on the board
	 * @param col
	 *            the column index of the tile on the board
	 */
	public void drawTileOnBoard(int tileCode, int row, int col);

	/**
	 * Draws a tile on the tray at the given index position. Calculates the x
	 * and y coordinates on the graphics surface corresponding to the index
	 * position.
	 * 
	 * @param tileCode
	 *            the unique code of the tile, that has to be drawn
	 * @param index
	 *            the index position of the tile on the tray
	 */
	public void drawTileOnTray(int tileCode, int index);

	/**
	 * Draws the tile which is in the hand of the player. The x and y
	 * coordinates of the mouse cursor are provided as parameters.
	 * 
	 * @param tileCode
	 *            the unique code of the tile, that has to be drawn
	 * @param mouseX
	 *            the x coordinate of the mouse
	 * @param mouseY
	 *            the y coordinate of the mouse
	 */
	public void drawTileInHand(int tileCode, double mouseX, double mouseY);

	/**
	 * Draws the game board on a graphics surface. It serves as a background for
	 * drawing the letter tiles. It is expected from the implementing class,
	 * that the graphical representation of the tiles on the game board will
	 * reflect their actual position.
	 */
	public void drawBoard();	

	/**
	 * The view class updates the informations about the state of the game on
	 * the screen. Uses the values from the provided {@link GameState} object as
	 * parameter.
	 * 
	 * @param gameState
	 *            the <code>GameState</code> object
	 */
	public void updateGameStateInfo(GameState gameState);

}
