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
 * The <code>Tray</code> class represents the tray in the game, on which a
 * player places his/her letter tiles drawn from the bag. A <code>Tray</code>
 * object stores the tile codes of the letters tiles on it and the number of
 * letter tiles currently placed on it. Basic operations are adding a tile to
 * the tray or picking one up from it with a specific index position or in
 * general.
 * 
 * @author gergo
 *
 */
public class Tray {

	/**
	 * An <code>int</code> array storing the code of the tiles on the tray.
	 */
	private int[] letters;
	/**
	 * The maximum number of tiles that can be placed on the tray.
	 */
	public static final int TRAY_SIZE = 7;
	/**
	 * The number of letters currently on the tray.
	 */
	private int numOfLetters;

	/**
	 * Constructs a new tray with no letter tiles on it.
	 */
	public Tray() {
		letters = new int[TRAY_SIZE];
		for (int i = 0; i < letters.length; i++) {
			letters[i] = -1;
		}
		numOfLetters = 0;
	}

	/**
	 * Returns all the letters from the tray concatenated as a
	 * <code>String</code>.
	 * 
	 * @return the letters from the tray as a <code>String</code>
	 */
	public String getLettersAsString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < letters.length; i++) {
			String letter = Letters.getL(letters[i]);
			if (letter != null) {
				sb.append(letter);
			}
		}

		return sb.toString();
	}

	/**
	 * Returns the tile code from the given index from the tray, and replaces
	 * the tile code value on the tray with -1. If there is no tile at the given
	 * index, or the index is greater than the <code>TRAY_SIZE</code>, the
	 * returned value is -1.
	 * 
	 * @param index
	 *            the index on the tray from which the tile will be picked up
	 * @return the tile code from the given position from the tray or -1 if
	 *         there is no tile or the <code>index</code> is too large
	 */
	public int pickTile(int index) {
		try {
			int retVal = letters[index];
			letters[index] = -1;
			if (retVal != -1) {
				numOfLetters--;
			}
			return retVal;
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
	}

	/**
	 * Places a tile with the given tile code on the tray at the given index if
	 * possible. If there was no tile at the index, returns <code>true</code>
	 * indicating success, otherwise returns <code>false</code>.
	 * 
	 * @param tileCode
	 *            the code of the tile to be placed on the tray
	 * @param index
	 *            the index where the tile should be placed
	 * @return <code>true</code> if the tile could be placed, <code>false</code>
	 *         otherwise
	 */
	public boolean setTile(int tileCode, int index) {
		if (letters[index] == -1) {
			letters[index] = tileCode;
			numOfLetters++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds the given tile code to the first available position on the tray.
	 * Returns <code>true</code> if the tile has been successfully added,
	 * meaning there was still place left on the tray, <code>false</code>
	 * otherwise.
	 * 
	 * @param code
	 *            the tile code that should be added to the tray
	 * @return <code>true</code> if the tile has been successfully added,
	 *         <code>false</code> otherwise
	 */
	public boolean addTile(int code) {
		boolean isPlace = false;
		for (int i = 0; i < letters.length; i++) {
			if (letters[i] == -1) {
				letters[i] = code;
				numOfLetters++;
				isPlace = true;
				break;
			}
		}
		return isPlace;
	}

	/**
	 * Removes the first occurrence of a tile with the given tile code from the
	 * tray. The tile code on the tray at this position will be replaced by -1.
	 * Returns <code>true</code> if the removal was successful,
	 * <code>false</code> if there was no tile on the tray with the given tile
	 * code.
	 * 
	 * @param code
	 *            the code of the tile to be removed
	 * @return <code>true</code> if the removal is successful,
	 *         <code>false</code> if couldn't find the tile code on the tray
	 */
	public boolean removeTile(int code) {
		boolean removed = false;
		for (int i = 0; i < letters.length; i++) {
			if (letters[i] == code) {
				letters[i] = -1;
				numOfLetters--;
				removed = true;
				break;
			}
		}
		return removed;
	}

	/**
	 * Returns the number of letters on the tray. It is advisable to check if
	 * the number of letters is less than the size of the tray, before calling
	 * the <code>addTile</code> method.
	 * 
	 * @return the number of letters on the tray
	 */
	public int getNumOfLetters() {
		return numOfLetters;
	}

	/**
	 * Returns the tile code of the tile from the tray, from the given index.
	 * The return value is -1 if there is not tile at the given index.
	 * 
	 * @param index
	 *            the index on the tray
	 * @return a tile code or -1 if there is no tile at the given index
	 */
	public int getTileCodeAt(int index) {
		return letters[index];
	}

	/**
	 * Resets the tray to its initial state, with no letters. All tile codes are
	 * set to -1. Should be used for testing only.
	 */
	public void reset() {
		for (int i = 0; i < TRAY_SIZE; i++) {
			letters[i] = -1;
		}
		numOfLetters = 0;
	}

	/**
	 * Sets the letter tile codes on the tray. Receives an array of integers
	 * with the tile codes. Should be used for testing only.
	 * 
	 * @param letters
	 *            the array containing the new letter tile codes
	 */
	public void setLetters(int[] letters) {
		this.letters = letters;
		numOfLetters = 0;
		for (int code : letters) {
			if (code != -1) {
				numOfLetters++;
			}
		}
	}

	/**
	 * Returns the array containing the tile codes of the letters on the tray.
	 * Should be used for testing only.
	 * 
	 * @return the array of tile codes on the tray
	 */
	public int[] getLetters() {
		return letters;
	}
}
