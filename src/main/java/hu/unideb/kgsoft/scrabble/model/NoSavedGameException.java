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
 * The {@code NoSavedGameException} is thrown when there is no saved game in the
 * central database, and an instance of the {@code SavedGameDAO} class is trying
 * to load one.
 * 
 * @author gergo
 *
 */
public class NoSavedGameException extends RuntimeException {

	private static final long serialVersionUID = 510758124854850237L;

	public NoSavedGameException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSavedGameException(Throwable cause) {
		super(cause);
	}

	public NoSavedGameException() {
		super();
	}
}
