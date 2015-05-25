package hu.unideb.kgsoft.scrabble;

import java.io.FileNotFoundException;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {    
    
    @Test
    public void testRedrawTiles() throws FileNotFoundException {
        Controller ctr = new Controller(this.getClass().getResourceAsStream("/hu_HU.dic"));
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
