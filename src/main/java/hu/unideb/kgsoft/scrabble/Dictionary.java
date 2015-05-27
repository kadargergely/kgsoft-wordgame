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
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Dictionary {

    private List<DictWord> dict = new ArrayList<DictWord>();

    /**
     * Constructor for creating a <code>Dictionary</code> object based on a
     * dictionary file.
     * 
     * @param inputStream
     *            a dictionary file, containing words
     * @throws FileNotFoundException
     *             if the dictionary file couldn't be found
     */
    public Dictionary(InputStreamReader inputStream) throws FileNotFoundException {

        Scanner in = new Scanner(inputStream);        

        String line = null;
        while (in.hasNext()) {
            line = in.nextLine();
            String word = (line.split("\\s+")[0]).split("/")[0];
            word = word.trim();

            DictWord dword = DictWord.newWord(word);
            if (dword != null) {
                dict.add(dword);
            }
        }

        Collections.sort(dict);

        in.close();
    }

    public void print(PrintStream out) {
        for (DictWord word : dict) {
            out.println(word);
        }
    }

    /**
     * Returns a list of words, that can be formed using the given letters.
     * 
     * @param letters
     *            the letters, represented as a <code>DictWord</code> object
     * @return the list of the playable words, as a list of string lists
     */
    public List<List<String>> getPlayableWords(DictWord letters) {
        List<List<String>> retList = new ArrayList<List<String>>();

        for (DictWord word : dict) {
            if (word.playable(letters)) {
                retList.add(word.toStringList());
            }
        }

        return retList;
    }

    /**
     * Tests if the dictionary includes the given word.
     * 
     * @param word
     *            the word, represented as a <code>String</code>
     * @return <code>true</code> if the word is part of the dictionary,
     *         <code>false</code> otherwise
     */
    public boolean isCorrect(String word) {
        for (DictWord dword : dict) {
            if (dword.equals(word.toLowerCase())) {
                return true;
            }
        }        
        
        return false;
    }
}
