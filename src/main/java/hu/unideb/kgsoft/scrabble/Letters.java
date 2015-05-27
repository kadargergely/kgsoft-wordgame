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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Letters} class stores informations about the letters that exist in
 * the game. These informations are the number of the different letters at the
 * beginning of the game, their point value and their code. Provides methods for
 * getting tile codes for strings containing a letter and vice versa.
 * 
 * @author kadar_000
 *
 */
public class Letters {

	private static final String[] letters = { "A", "Á", "B", "C", "CS", "D",
			"E", "É", "F", "G", "GY", "H", "I", "Í", "J", "K", "L", "LY", "M",
			"N", "NY", "O", "Ó", "Ö", "Ő", "P", "R", "S", "SZ", "T", "TY", "U",
			"Ú", "Ü", "Ű", "V", "Z", "ZS", "_" };
	private static final Map<String, Integer> letterNum;
	static {
		Map<String, Integer> tmpMap = new HashMap<String, Integer>();
		tmpMap.put("a", 0);
		tmpMap.put("á", 1);
		tmpMap.put("b", 2);
		tmpMap.put("c", 3);
		tmpMap.put("cs", 4);
		tmpMap.put("d", 5);
		tmpMap.put("e", 6);
		tmpMap.put("é", 7);
		tmpMap.put("f", 8);
		tmpMap.put("g", 9);
		tmpMap.put("gy", 10);
		tmpMap.put("h", 11);
		tmpMap.put("i", 12);
		tmpMap.put("í", 13);
		tmpMap.put("j", 14);
		tmpMap.put("k", 15);
		tmpMap.put("l", 16);
		tmpMap.put("ly", 17);
		tmpMap.put("m", 18);
		tmpMap.put("n", 19);
		tmpMap.put("ny", 20);
		tmpMap.put("o", 21);
		tmpMap.put("ó", 22);
		tmpMap.put("ö", 23);
		tmpMap.put("ő", 24);
		tmpMap.put("p", 25);
		tmpMap.put("r", 26);
		tmpMap.put("s", 27);
		tmpMap.put("sz", 28);
		tmpMap.put("t", 29);
		tmpMap.put("ty", 30);
		tmpMap.put("u", 31);
		tmpMap.put("ú", 32);
		tmpMap.put("ü", 33);
		tmpMap.put("ű", 34);
		tmpMap.put("v", 35);
		tmpMap.put("z", 36);
		tmpMap.put("zs", 37);
		tmpMap.put("_", 38);

		letterNum = Collections.unmodifiableMap(tmpMap);
	}

	/**
	 * The tile code of the joker tile.
	 */
	public static final int JOKER_CODE = 38;

	private static final int values[] = { 1, 1, 2, 5, 7, 2, 1, 3, 4, 2, 4, 3,
			1, 5, 4, 1, 1, 8, 1, 1, 5, 1, 2, 4, 7, 4, 1, 1, 3, 1, 10, 4, 7, 4,
			7, 3, 4, 8, 0 };
	private static final int quantities[] = { 6, 4, 3, 1, 1, 3, 6, 3, 2, 3, 2,
			2, 3, 1, 2, 6, 4, 1, 3, 4, 1, 3, 3, 2, 1, 2, 4, 3, 2, 5, 1, 2, 1,
			2, 1, 2, 2, 1, 2 };

	/**
	 * Returns a string containing the letter which is represented by the tile
	 * code given. If the code doesn't belong to any tile, the return value id
	 * {@code null}.
	 * 
	 * @param code
	 *            the tile code
	 * @return the string containing the letter which is represented by the tile
	 *         code given or {@code null} if the code isn't valid
	 */
	public static String getL(int code) {
		try {
			return letters[code];
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	/**
	 * Returns the code which represents the letter given as a string. If the
	 * string doesn't contain a valid letter, -1 is returned.
	 * 
	 * @param letter
	 *            the letter as a string
	 * @return the code which represents the letter, or -1 if the letter is not
	 *         valid
	 */
	public static int getC(String letter) {
		if (letterNum.containsKey(letter)) {
			return letterNum.get(letter);
		} else {
			return -1;
		}
	}

	/**
	 * Returns the point value of the letter with the given code. If the code
	 * doesn't belong to any letter, -1 is returned.
	 * 
	 * @param code
	 *            the code of the letter
	 * @return the point value of the letter with the given code, or -1 if the
	 *         code is not legal
	 */
	public static int getValue(int code) {
		try {
			return values[code];
		} catch (IndexOutOfBoundsException ex) {
			return -1;
		}
	}

	/**
	 * Returns the point value of the letter given as a string. If the letter
	 * isn't valid, -1 is returned.
	 * 
	 * @param letter
	 *            the letter as a string
	 * @return the point value of the letter, or -1 if the letter is not valid
	 */
	public static int getValue(String letter) {
		try {
			return values[getC(letter)];
		} catch (IndexOutOfBoundsException ex) {
			return -1;
		}
	}

	/**
	 * Returns the number of letters in the bag with the given code, before the
	 * start of the game.
	 * 
	 * @param code
	 *            the code of the letter
	 * @return the number of letters in the bag with the given code
	 */
	public static int getQuantity(int code) {
		return quantities[code];
	}

	/**
	 * Returns how many of the given letter are in the bag, before the start of
	 * the game.
	 * 
	 * @param letter
	 *            the letter as a string
	 * @return the quantity of the given letter in the bag
	 */
	public static int getQuantity(String letter) {
		return quantities[getC(letter)];
	}

	/**
	 * Returns the number of different letters that exist in the game.
	 * 
	 * @return the number of different letters that exist in the game
	 */
	public static int getNum() {
		return letters.length;
	}
}
