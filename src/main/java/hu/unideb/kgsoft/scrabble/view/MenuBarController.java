package hu.unideb.kgsoft.scrabble.view;

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
