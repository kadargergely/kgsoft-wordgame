package hu.unideb.kgsoft.scrabble;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    public Dictionary(InputStream inputStream) throws FileNotFoundException {

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
