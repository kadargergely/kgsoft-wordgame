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

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {
    
    @Test
    public void test() throws FileNotFoundException, UnsupportedEncodingException {
        Dictionary dict = new Dictionary(new InputStreamReader(this.getClass().getResourceAsStream("/hu_HU.dic"), "UTF-8"));
        assertFalse(dict.isCorrect("Duna"));
        assertFalse(dict.isCorrect("FTC"));
        assertFalse(dict.isCorrect("yard"));
        assertTrue(dict.getPlayableWords(DictWord.newWord("shakde_")).size() > 0);
        assertTrue(dict.isCorrect("pék"));
    }
}
