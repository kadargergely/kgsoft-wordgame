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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.kgsoft.scrabble.view.JavaVersionMessage;
import hu.unideb.kgsoft.scrabble.view.ScrabbleApp;

public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        logger.info("Application started.");
        String javaVersion = System.getProperty("java.runtime.version");
        logger.info("Using java version " + javaVersion);        
    	int majorVersion = Integer.parseInt(String.valueOf(javaVersion.charAt(2)));
    	String versionAfterUnderline = javaVersion.substring(javaVersion.indexOf("_") + 1);
    	StringBuilder minorVersionString = new StringBuilder();
    	for (int i = 0; i < versionAfterUnderline.length(); i++) {
    		if (Character.isDigit(versionAfterUnderline.charAt(i))) {
    			minorVersionString.append(versionAfterUnderline.charAt(i));
    		} else {
    			break;
    		}
    	}
    	if (!(majorVersion == 8  && Integer.parseInt(minorVersionString.toString()) >= 40)) {
    		logger.error("Java version 1.8.0_40 or later required, please update Java.");
    		JavaVersionMessage.showMessage(args);
    	} else {
    		String dictFile = "/hu_HU.dic";
            try {        	
                logger.info("Initializing Controller class with dictionary file '" + dictFile + "'...");
                Controller ctr = new Controller(new InputStreamReader(Main.class.getResourceAsStream(dictFile), "UTF-8"));            
                logger.info("Controller class initialized.");            
                ScrabbleApp.setMainCtr(ctr);            

                logger.info("Starting GUI...");
                ScrabbleApp.runApp(args);
                logger.info("GUI exited.");

            } catch (FileNotFoundException e) {
                logger.error(String.format("Dictionary file '%s' not found!", dictFile));
            } catch (UnsupportedEncodingException e) {
            	logger.error(String.format("Unsupported encoding for the dictionaty file '%s'.", dictFile));
    			e.printStackTrace();
    		}
    	}        
    }

}
