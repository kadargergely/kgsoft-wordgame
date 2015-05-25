package hu.unideb.kgsoft.scrabble;

import java.io.FileNotFoundException;

import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {
    
    @Test
    public void test() throws FileNotFoundException {
        Dictionary dict = new Dictionary(this.getClass().getResourceAsStream("/hu_HU.dic"));
        assertFalse(dict.isCorrect("Duna"));
        assertFalse(dict.isCorrect("FTC"));
        assertFalse(dict.isCorrect("yard"));
        assertTrue(dict.getPlayableWords(DictWord.newWord("shakde_")).size() > 0);
    }
}
