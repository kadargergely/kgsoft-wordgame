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
 * The {@code ExistingUserException} is thrown when a user name that needs to be
 * registered already exists in the database.
 * 
 * @author kadar_000
 *
 */
public class ExistingUserException extends RuntimeException {

	private static final long serialVersionUID = 4973496468100990589L;
	
	public ExistingUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExistingUserException(Throwable cause) {
		super(cause);
	}

	public ExistingUserException() {
		super();
	}
}
