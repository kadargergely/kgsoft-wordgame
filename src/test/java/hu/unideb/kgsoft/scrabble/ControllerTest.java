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

public class ControllerTest {    
    
    @Test
    public void testRedrawTiles() throws FileNotFoundException, UnsupportedEncodingException {
        Controller ctr = new Controller(new InputStreamReader(this.getClass().getResourceAsStream("/hu_HU.dic"), "UTF-8"));
        Player player = ctr.getPlayer();
        Tray tray = player.getTray();
        Bag bag = ctr.getBag();
        
        ctr.drawTilesFromBag(tray);
        
        int[] remaining = bag.getRemaining().clone();
        int[] trayLetters = tray.getLetters().clone();        
        
        ctr.redrawTiles(tray);       
        
        for (int i = 0; i < Tray.TRAY_SIZE; i++) {
            int numOfSameOld = 0;
            int numOfSameNew = 0;
            for (int j = 0; j < Tray.TRAY_SIZE; j++) {
                if (tray.getLetters()[j] == trayLetters[i]) {
                    numOfSameNew++;
                }
                if (trayLetters[j] == trayLetters[i]) {
                    numOfSameOld++;
                }
            }            
            assertEquals(remaining[trayLetters[i]] + numOfSameOld - numOfSameNew, bag.getRemaining()[trayLetters[i]]);
        }
        
        for (int i = 0; i < Tray.TRAY_SIZE; i++) {
            int numOfSameOld = 0;
            int numOfSameNew = 0;
            for (int j = 0; j < Tray.TRAY_SIZE; j++) {
                if (tray.getLetters()[i] == trayLetters[j]) {
                    numOfSameOld++;
                }
                if (tray.getLetters()[i] == tray.getLetters()[j]) {
                    numOfSameNew++;
                }
            }            
            assertEquals(remaining[tray.getLetters()[i]] + numOfSameOld - numOfSameNew, bag.getRemaining()[tray.getLetters()[i]]);
        }
    }    
}
