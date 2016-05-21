package hu.unideb.kgsoft.scrabble.utils;

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

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hu.unideb.kgsoft.scrabble.Main.logger;

/**
 * The {@code Utils} class provides static methods for performing common tasks
 * used by other classes.
 * 
 * @author gergo
 *
 */
public class Utils {
	private static MessageDigest md;

	/**
	 * Returns a string containing the encrypted version of the string given as
	 * parameter. Encryption is performed using the MD5 algorithm. If a problem
	 * occurred during the process, {@code null} is returned.
	 * 
	 * @param passwd
	 *            the string to be encrypted
	 * @return the encrypted string, or {@code null} if the encryption failed
	 */
	public static String processPassword(String passwd) {
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = passwd.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("No algorithm for password encryption called MD5");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns an array of {@code Integers} objects containing the integers from
	 * the {@code int} array given.
	 * 
	 * @param primitiveArray
	 *            the array of primitive {@code int} type to be converted
	 * @return a new {@code Integer} array created using the array given
	 */
	public static Integer[] toIntegerArray(int[] primitiveArray) {
		Integer[] newArray = new Integer[primitiveArray.length];
		for (int i = 0; i < primitiveArray.length; i++) {
			newArray[i] = Integer.valueOf(primitiveArray[i]);
		}
		return newArray;
	}

	/**
	 * Returns a new primitive {@code int} array created from the
	 * {@code Integer} array given.
	 * 
	 * @param integerArray
	 *            an array of {@code Integer} objects
	 * @return a new {@code int} array created from the {@code Integer} array
	 *         given.
	 */
	public static int[] toIntArray(Integer[] integerArray) {
		int[] newArray = new int[integerArray.length];
		for (int i = 0; i < integerArray.length; i++) {
			newArray[i] = integerArray[i];
		}
		return newArray;
	}

	/**
	 * Returns a new primitive {@code int} array created from the
	 * {@code BigDecimal} array given. Note that this conversion can lose
	 * information.
	 * 
	 * @param bigDecimalArray
	 *            an array of {@code BigDecimal} objects
	 * @return a new {@code int} array created from the {@code BigDecimal} array
	 *         given.
	 */
	public static int[] toIntArray(BigDecimal[] bigDecimalArray) {
		int[] newArray = new int[bigDecimalArray.length];
		for (int i = 0; i < bigDecimalArray.length; i++) {
			newArray[i] = bigDecimalArray[i].intValue();
		}
		return newArray;
	}

	/**
	 * Similar to the {@code toString} method of lists. This method is used when
	 * a sequence of string has to be inserted into the database. Returns the
	 * strings from the array between apostrophes and separated by commas.
	 * 
	 * @param strArray
	 *            the array of strings
	 * @return the string containing the strings from the array between
	 *         apostrophes and separated by commas
	 */
	public static String stringArrayToString(String[] strArray) {
		StringJoiner sj = new StringJoiner("', '", "'", "'");
		for (String str : strArray) {
			sj.add(str);
		}
		return sj.toString();
	}

	/**
	 * Splits a string containing integers with a delimiter.
	 * 
	 * @param str
	 *            the string containing the integers
	 * @param delimiter
	 *            the string delimiting the integers in the string
	 * @return the {@code int} array containing the integers from the string
	 */
	public static int[] stringToIntArray(String str, String delimiter) {
		String[] splittedString = str.split(delimiter);
		int[] intArray = new int[splittedString.length];
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = Integer.valueOf(splittedString[i]);
		}
		return intArray;
	}
	
	/**
	 * Concatenates the elements of an {@code int[]} in a string.
	 * 
	 * @param array
	 *            the array containing the integers
	 * @param delimiter
	 *            the string delimiting the integers in the string
	 * @return the string containing the integers from the array
	 */
	public static String intArrayToString(int[] array, String delimiter) {
		StringJoiner sj = new StringJoiner(delimiter);
		for (int i : array) {
			sj.add(String.valueOf(i));
		}
		return sj.toString();
	}
}
