package hu.unideb.kgsoft.scrabble;

import org.junit.Test;
import static org.junit.Assert.*;

import hu.unideb.kgsoft.scrabble.Bag;

public class BagTest {        

    @Test
    public void testGetRandTile() {
        Bag bag;
        int[] remaining;
        int total, tileCode;
        
        bag = new Bag();
        int initialTotal = bag.getNumOfTiles();
        for (int i = 0; i < initialTotal; i++) {
            remaining = bag.getRemaining().clone();
            total = bag.getNumOfTiles();
            tileCode = bag.getRandTile();
//            System.out.println("old remaining[tileCode] = " + remaining[tileCode]);
//            System.out.println("new remaining[tileCode] = " + bag.getRemaining()[tileCode]);
            assertTrue(remaining[tileCode] > 0 && bag.getRemaining()[tileCode] == remaining[tileCode] - 1);
            assertTrue(bag.getNumOfTiles() == total - 1);
        }        

        tileCode = bag.getRandTile();
        assertEquals(-1, tileCode);
    }
    
    @Test
    public void testPutBack() {
        Bag bag = new Bag();
        int putBackTileCode = 37;
        int remaining = bag.getRemaining()[putBackTileCode];
        int total = bag.getNumOfTiles();
        bag.putBack(putBackTileCode);
        assertEquals(remaining + 1, bag.getRemaining()[putBackTileCode]);
        assertEquals(total + 1, bag.getNumOfTiles());
    }
}
