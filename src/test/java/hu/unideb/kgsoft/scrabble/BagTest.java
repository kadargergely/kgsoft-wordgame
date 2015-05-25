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
