package hu.unideb.kgsoft.scrabble;

import hu.unideb.kgsoft.scrabble.model.Dictionary;
import hu.unideb.kgsoft.scrabble.model.Field;
import hu.unideb.kgsoft.scrabble.model.Gameboard;
import hu.unideb.kgsoft.scrabble.model.Letters;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameboardTest {

    @Test
    public void testSetTile() {
        Gameboard board = new Gameboard();
        int movableTiles = board.getMovableTiles();

        assertEquals(true, board.setTile(7, 7, 7));
        assertEquals(Field.Status.MOVABLE, board.getFields()[7][7].getStatus());
        assertEquals(7, board.getFields()[7][7].getTileCode());
        assertEquals(movableTiles + 1, board.getMovableTiles());

        assertEquals(false, board.setTile(7, 7, 8));
        assertEquals(Field.Status.MOVABLE, board.getFields()[7][7].getStatus());
        assertEquals(7, board.getFields()[7][7].getTileCode());
        assertEquals(movableTiles + 1, board.getMovableTiles());

        board.finalizeFields();
        assertEquals(false, board.setTile(7, 7, 9));
    }

    @Test
    public void testPickUpTile() {
        Gameboard board = new Gameboard();
        board.setTile(7, 7, 7);
        int movableTiles = board.getMovableTiles();

        assertEquals(7, board.pickUpTile(7, 7));
        assertEquals(Field.Status.EMPTY, board.getFields()[7][7].getStatus());
        assertEquals(Field.NOTILE, board.getFields()[7][7].getTileCode());
        assertEquals(Field.NOTILE, board.getFields()[7][7].getJokerTileCode());
        assertEquals(movableTiles - 1, board.getMovableTiles());

        assertEquals(Field.NOTILE, board.pickUpTile(7, 7));
        assertEquals(Field.Status.EMPTY, board.getFields()[7][7].getStatus());
        assertEquals(Field.NOTILE, board.getFields()[7][7].getTileCode());
        assertEquals(Field.NOTILE, board.getFields()[7][7].getJokerTileCode());
        assertEquals(movableTiles - 1, board.getMovableTiles());

        board.setTile(7, 7, 7);
        board.finalizeFields();
        assertEquals(Field.NOTILE, board.pickUpTile(7, 7));
        assertEquals(Field.Status.FIXED, board.getFields()[7][7].getStatus());
        assertEquals(7, board.getFields()[7][7].getTileCode());
        assertEquals(Field.NOTILE, board.getFields()[7][7].getJokerTileCode());
        assertEquals(0, board.getMovableTiles());
    }

    @Test
    public void testSetJokerTile() {
        Gameboard board = new Gameboard();
        int movableTiles = board.getMovableTiles();

        assertEquals(true, board.setJokerTile(7, 7, 7));
        assertEquals(Field.Status.MOVABLE, board.getFields()[7][7].getStatus());
        assertEquals(Letters.JOKER_CODE, board.getFields()[7][7].getTileCode());
        assertEquals(7, board.getFields()[7][7].getJokerTileCode());
        assertEquals(movableTiles + 1, board.getMovableTiles());

        assertEquals(false, board.setJokerTile(7, 7, 7));
        assertEquals(Field.Status.MOVABLE, board.getFields()[7][7].getStatus());
        assertEquals(Letters.JOKER_CODE, board.getFields()[7][7].getTileCode());
        assertEquals(7, board.getFields()[7][7].getJokerTileCode());
        assertEquals(movableTiles + 1, board.getMovableTiles());

        board.finalizeFields();
        assertEquals(false, board.setJokerTile(7, 7, 7));
    }

    @Test
    public void testIsLegal() {
        Gameboard board = new Gameboard();

        assertEquals(false, board.isLegal(true));
        board.setTile(2, 2, 0);
        board.setTile(2, 3, 0);
        assertEquals(false, board.isLegal(true));
        board.pickUpTile(2, 2);
        board.pickUpTile(2, 3);
        board.setTile(7, 7, 0);
        assertEquals(true, board.isLegal(true));
        board.setTile(7, 8, 0);
        assertEquals(true, board.isLegal(true));
        board.pickUpTile(7, 8);
        board.setTile(7, 9, 0);
        assertEquals(false, board.isLegal(true));
        board.pickUpTile(7, 9);
        board.setTile(9, 7, 0);
        assertEquals(false, board.isLegal(true));
        board.pickUpTile(9, 7);
        board.setTile(8, 8, 0);
        assertEquals(false, board.isLegal(true));
        board.pickUpTile(8, 8);
        board.setTile(7, 8, 0);
        board.setTile(10, 2, 0);
        assertEquals(false, board.isLegal(true));
        board.pickUpTile(7, 8);
        board.pickUpTile(10, 2);
        board.setTile(8, 7, 0);
        board.setTile(4, 3, 0);
        assertEquals(false, board.isLegal(true));
        board.pickUpTile(4, 3);
        board.setTile(9, 7, 0);

        board.finalizeFields();
        board.setTile(7, 8, 0);
        board.setTile(7, 9, 0);
        assertEquals(true, board.isLegal(false));
        board.setTile(7, 6, 0);
        assertEquals(true, board.isLegal(false));
        board.setTile(7, 4, 0);
        assertEquals(false, board.isLegal(false));
        board.pickUpTile(7, 4);
        board.setTile(6, 4, 0);
        assertEquals(false, board.isLegal(false));
        board.pickUpTile(6, 4);
        board.pickUpTile(7, 8);
        board.pickUpTile(7, 9);
        board.setTile(6, 6, 0);
        assertEquals(true, board.isLegal(false));
        board.setTile(4, 6, 0);
        assertEquals(false, board.isLegal(false));
        board.pickUpTile(4, 6);
        board.setTile(4, 4, 0);
        assertEquals(false, board.isLegal(false));
        board.pickUpTile(4, 4);
        board.setTile(5, 6, 0);
        assertEquals(true, board.isLegal(false));
        board.pickUpTile(7, 6);
        assertEquals(false, board.isLegal(false));
        board.finalizeFields();
        
        Gameboard newBoard = new Gameboard(board.getFields());
        newBoard.setTile(14, 14, 0);
        assertEquals(false, board.isLegal(false));
    }

    @Test
    public void testGetPlayedWords() {
        Gameboard board = new Gameboard();

        assertEquals(null, board.getPlayedWords());

        board.setTile(4, 4, 0);
        board.setTile(4, 5, 1);
        board.setTile(4, 6, 2);
        board.setTile(4, 7, 3);
        List<String> words = board.getPlayedWords();
        assertTrue(words.size() == 1 && words.contains("AÁBC"));

        board.finalizeFields();

        board.setTile(5, 4, 4);
        board.setTile(6, 4, 5);
        words = board.getPlayedWords();
        assertTrue(words.size() == 1 && words.contains("ACSD"));

        board.finalizeFields();

        board.setTile(4, 8, 6);
        board.setTile(5, 8, 7);
        board.setTile(6, 8, 8);
        words = board.getPlayedWords();
        assertTrue(words.size() == 2 && words.contains("AÁBCE")
                && words.contains("EÉF"));

        board.finalizeFields();

        board.setTile(7, 8, 9);
        board.setTile(7, 9, 10);
        board.setTile(7, 7, 11);
        words = board.getPlayedWords();
        assertTrue(words.size() == 2 && words.contains("EÉFG")
                && words.contains("HGGY"));

        board.finalizeFields();

        board.setTile(8, 9, 12);
        words = board.getPlayedWords();
        assertTrue(words.size() == 1 && words.contains("GYI"));

        board.finalizeFields();

        board.setTile(7, 10, 13);
        words = board.getPlayedWords();
        assertTrue(words.size() == 1 && words.contains("HGGYÍ"));
    }

    @Test
    public void testCalcPoints() {
        Gameboard board = new Gameboard();
        assertEquals(0, board.calcPoints());
        board.setTile(5, 6, 0);
        board.setTile(5, 7, 0);
        board.setTile(5, 8, 0);
        assertEquals(3, board.calcPoints());

        board = new Gameboard();
        board.setTile(0, 7, 0);
        board.setTile(1, 7, 0);
        board.setTile(2, 7, 0);
        assertEquals(9, board.calcPoints());
        board.setTile(3, 7, 0);
        assertEquals(15, board.calcPoints());

        board = new Gameboard();
        board.setTile(1, 10, 0);
        board.setTile(1, 11, 0);
        board.setTile(1, 12, 0);
        board.setTile(1, 13, 0);
        assertEquals(8, board.calcPoints());
        board.setTile(1, 9, 0);
        assertEquals(14, board.calcPoints());
        board.finalizeFields();
        board.setTile(1, 8, 0);
        assertEquals(6, board.calcPoints());

        board = new Gameboard();
        board.setTile(0, 0, 0);
        board.setTile(0, 1, 0);
        board.setTile(0, 2, 0);
        board.setTile(0, 3, 0);
        board.setTile(0, 4, 0);
        board.setTile(0, 5, 0);
        board.setTile(0, 6, 0);
        board.setTile(0, 7, 0);
        assertEquals(81, board.calcPoints());
        board.finalizeFields();
        board.setTile(0, 8, 0);
        assertEquals(9, board.calcPoints());
        board.setTile(1, 8, 0);
        assertEquals(11, board.calcPoints());
        board.finalizeFields();
        board.setTile(2, 8, 0);
        board.setTile(2, 9, 0);
        assertEquals(7, board.calcPoints());
    }    

    @Test
    public void testWordsAreCorrect() throws UnsupportedEncodingException {
        Dictionary dict = null;
        try {
            dict = new Dictionary(new InputStreamReader(this.getClass().getResourceAsStream(
                    "/hu_HU.dic"), "UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Dictionary file not found.");
        }

        Gameboard board = new Gameboard();
        assertEquals(false, board.wordsAreCorrect(dict));
        board.setTile(7, 7, 0);
        board.setTile(7, 8, 16);
        board.setTile(7, 9, 18);
        board.setTile(7, 10, 0);
        assertEquals(true, board.wordsAreCorrect(dict));
        board.finalizeFields();
        board.setTile(6, 8, 0);
        board.setTile(8, 8, 18);
        board.setTile(9, 8, 0);
        assertEquals(true, board.wordsAreCorrect(dict));
        board.finalizeFields();
        board.setTile(6, 9, 14);
        board.setTile(6, 10, 29);
        board.setTile(6, 11, 22);
        assertEquals(false, board.wordsAreCorrect(dict));

        board = new Gameboard();
        board.setTile(7, 7, 0);
        board.setTile(7, 8, 18);
        board.setTile(7, 9, 16);
        board.setTile(7, 10, 0);
        assertEquals(false, board.wordsAreCorrect(dict));
    }

    @Test
    public void testFinalizeFields() {
        Gameboard board = new Gameboard();
        board.setTile(7, 7, 0);
        board.setTile(7, 8, 16);
        board.setTile(7, 9, 18);
        board.setTile(7, 10, 0);
        board.finalizeFields();
        Field[][] fields = board.getFields();
        for (int i = 0; i < Gameboard.BOARD_SIZE; i++) {
            for (int j = 0; j < Gameboard.BOARD_SIZE; j++) {
                assertTrue(fields[i][j].getStatus() == Field.Status.FIXED
                        || fields[i][j].getStatus() == Field.Status.EMPTY);
            }
        }
    }
}
