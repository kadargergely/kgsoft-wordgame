package hu.unideb.kgsoft.scrabble.view;

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

import hu.unideb.kgsoft.scrabble.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuBarController {
    
    @FXML
    private MenuItem newGameMenuItem;
    @FXML
    private MenuItem loadGameMenuItem;
    @FXML
    private MenuItem saveGameMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem switchUserMenuItem;
    
    private Controller mainCtr;
    private ScrabbleApp mainApp;
    
    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public MenuBarController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {        
    }
    
    @FXML
    private void newGameClicked() {
        mainCtr.startNewGame();
    }
    
    @FXML
    private void loadGameClicked() {
        mainCtr.loadGame();
    }
    
    @FXML
    private void saveGameClicked() {
        mainCtr.handleSaveGameClicked();
    }
    
    @FXML
    private void exitClicked() {
        Platform.exit();
    }
    
    @FXML
    private void switchUserClicked() {
        
    }
    
    public void setMainCtr(Controller mainCtr) {
        this.mainCtr = mainCtr;
    }

    public void setMainApp(ScrabbleApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void saveGameDisabled(boolean value) {
        saveGameMenuItem.setDisable(value);
    }
}
