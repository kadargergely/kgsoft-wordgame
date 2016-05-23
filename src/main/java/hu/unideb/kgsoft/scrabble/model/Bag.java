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

import java.util.Random;

/**
 * The <code>Bag</code> class represents the bag from which the players can draw
 * tiles during the game. One <code>Bag</code> objects exists in a game. The
 * <code>Bag</code> objects stores how many tiles are left of each letter. Basic
 * operations are drawing a new random letter from the bag or putting one back.
 * 
 * @author gergo
 *
 */
public class Bag {

	/**
	 * An <code>int</code> array storing how many tiles are still in the bag for
	 * each tile code.
	 */
	private int[] remaining;
	/**
	 * The total number of tiles in the bag.
	 */
	private int total;

	/**
	 * Constructs a new <code>Bag</code> object and fills it with the tiles.
	 */
	public Bag() {
		remaining = new int[Letters.getNum()];
		total = 0;

		for (int i = 0; i < remaining.length; i++) {
			remaining[i] = Letters.getQuantity(i);
			total += remaining[i];
		}
	}

	/**
	 * Constructs a new {@code Bag} object with the given array storing the
	 * number of tiles left for each tile code.
	 * 
	 * @param remaining
	 *            array storing the number of tiles left for each tile code
	 */
	public Bag(int[] remaining) {
		this.remaining = remaining;
		total = 0;
		for (int i : remaining) {
			total += i;
		}
	}

	/**
	 * Returns a random tile code taken out of the bag. The tile will be removed
	 * from the bag. If there are no more tiles left in the bag, the method
	 * returns -1.
	 * 
	 * @return a random tile code from the bag or -1 if the bag is empty
	 */
	public int getRandTile() {
		if (total > 0) {
			Random rand = new Random();
			int randInt = rand.nextInt(total);

			int i = -1, j = 0;
			while (j <= randInt) {
				i++;
				j += remaining[i];
			}

			total -= 1;
			remaining[i] -= 1;

			return i;
		} else {
			return -1;
		}
	}

	/**
	 * Puts back a tile with the given tile code into the bag.
	 * 
	 * @param code
	 *            the code of the tile that will be put into the bag
	 */
	public void putBack(int code) {
		remaining[code] += 1;
		total += 1;
	}

	/**
	 * Returns the number of tiles currently in the bag.
	 * 
	 * @return the number of tiles currently in the bag
	 */
	public int getNumOfTiles() {
		return total;
	}

	/**
	 * Returns the array in which the number of tiles left for each letter is
	 * stored. Should be used for testing only.
	 * 
	 * @return the array containing how many tiles are left for each letter
	 */
	public int[] getRemaining() {
		return remaining;
	}
}
