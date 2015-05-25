package hu.unideb.kgsoft.scrabble;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.kgsoft.scrabble.view.ScrabbleApp;

public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        logger.info("Application started.");
        String dictFile = "/hu_HU.dic";
        try {            
            logger.info("Initializing Controller class with dictionary file '" + dictFile + "'...");
            Controller ctr = new Controller(
                    Main.class.getResourceAsStream(dictFile));            
            logger.info("Controller class initialized.");            
            ScrabbleApp.setMainCtr(ctr);

            logger.info("Starting GUI...");
            ScrabbleApp.runApp(args);
            logger.info("GUI exited.");

        } catch (FileNotFoundException e) {
            logger.error(String.format("Dictionary file '%s' not found!", dictFile));
        }
    }

}
