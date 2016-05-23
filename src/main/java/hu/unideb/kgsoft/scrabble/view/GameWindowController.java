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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import hu.unideb.kgsoft.scrabble.Controller;
import hu.unideb.kgsoft.scrabble.GameView;
import hu.unideb.kgsoft.scrabble.model.Computer;
import hu.unideb.kgsoft.scrabble.model.Dictionary;
import hu.unideb.kgsoft.scrabble.model.GameState;
import hu.unideb.kgsoft.scrabble.model.Gameboard;
import hu.unideb.kgsoft.scrabble.model.db.DBConnectionException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import static hu.unideb.kgsoft.scrabble.Main.logger;

public class GameWindowController implements GameView {    

    @FXML
    private Canvas gameCanvas;
    @FXML
    private Button doneButton;
    @FXML
    private Button redrawButton;
    @FXML
    private Button passButton;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label computerNameLabel;
    @FXML
    private Label playerScoreLabel;
    @FXML
    private Label computerScoreLabel;
    @FXML
    private Label lastWordOrWordsLabel;
    @FXML
    private Label lastWordOrWordsListingLabel;
    @FXML
    private Label lastWordOrWordsPointsLabel;
    @FXML
    private Label currWordOrWordsLabel;
    @FXML
    private Label currWordOrWordsListingLabel;
    @FXML
    private Label currWordOrWordsPointsLabel;
    @FXML
    private Label computerThinkingLabel;
    @FXML
    private ProgressIndicator computerThinkingIndicator;
    /**
     * The <code>GraphicsContext</code> of the canvas.
     */
    private GraphicsContext gc;

    /**
     * A list of the letter sprites that the game uses.
     */
    private List<Image> letterSprites;
    /**
     * The background image of the canvas.
     */
    private Image backgroundImage;
    /**
     * A reference to the <code>Controller</code> object.
     */
    private Controller mainCtr;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public GameWindowController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {    	
    	
        // Load letter sprites.
        letterSprites = new ArrayList<Image>();
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/A.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/A2.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/B.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/C.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/CS.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/D.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/E.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/E2.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/F.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/G.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/GY.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/H.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/I.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/I2.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/K.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/L.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/LY.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/M.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/N.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/NY.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/O.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/O2.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/O3.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/O4.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/P.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/R.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/S.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/SZ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/T.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/TY.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/U.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/U2.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/U3.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/U4.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/V.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/Z.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/ZS.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/Joker.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/AJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/A2J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/BJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/CJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/CSJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/DJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/EJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/E2J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/FJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/GJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/GYJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/HJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/IJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/I2J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/JJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/KJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/LJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/LYJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/MJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/NJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/NYJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/OJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/O2J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/O3J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/O4J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/PJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/RJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/SJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/SZJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/TJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/TYJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/UJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/U2J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/U3J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/U4J.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/VJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/ZJ.png").toString()));
        letterSprites.add(new Image(GameWindowController.class.getResource(
                "/Images/ZSJ.png").toString()));

        // Load background image.
        backgroundImage = new Image(GameWindowController.class.getResource(
                "/Images/scrabble_board.png").toString());

        // get the graphics context and update the canvas
        gc = gameCanvas.getGraphicsContext2D();
        drawBoard();

        // Add event handlers to the canvas.
        gameCanvas.addEventHandler(MouseEvent.MOUSE_MOVED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mainCtr.handleMouseMotion(event.getX(), event.getY());
                    }
                });
        gameCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        mainCtr.handleMouseMotion(event.getX(), event.getY());
                    }
                });
        gameCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getX() > 35 && event.getX() < 515
                                && event.getY() > 10 && event.getY() < 490) {
                            int row = ((int) event.getY() - 10) / 32;
                            int col = ((int) event.getX() - 35) / 32;
                            mainCtr.handleMousePressedOnBoard(row, col,
                                    event.getX(), event.getY());
                        } else if (event.getX() > 163 && event.getX() < 387
                                && event.getY() > 504 && event.getY() < 536) {
                            int index = ((int) event.getX() - 163) / 32;
                            mainCtr.handleMousePressedOnTray(index,
                                    event.getX(), event.getY());
                        }
                    }
                });        
        
    }

    public void computerThread(final Computer computer, final Gameboard gboard,
            final Dictionary dict) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {                
                computer.play(gboard, dict);
                return null;
            }
        };
        task.stateProperty().addListener(new ChangeListener<Worker.State>() {

            @Override
            public void changed(ObservableValue<? extends State> observable,
                    State oldValue, State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    logger.info("Computer finished thinking.");
                    mainCtr.computerReady();
                }
            }
        });

        logger.info("Computer started thinking...");
        new Thread(task).start();
    }
    
    public void saveGameThread() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws DBConnectionException {                
                mainCtr.saveGame();
                return null;
            }
        };        
        task.stateProperty().addListener(new ChangeListener<Worker.State>() {

            @Override
            public void changed(ObservableValue<? extends State> observable,
                    State oldValue, State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    logger.info("Game saving process finished.");
                    mainCtr.gameSaved();
                }
                if (newValue == Worker.State.FAILED) {
                	logger.info("Failed to save the game.");
                	showErrorMessage("Kapcsolódási hiba", "Nem sikerült elérni az adatbázis szervert.");
                }
            }
        });

        logger.info("Game saving process started...");
        new Thread(task).start();
    }

    @FXML
    private void doneButtonClicked() {
        mainCtr.handleDoneButton();
    }

    @FXML
    private void redrawButtonClicked() {
        mainCtr.handleRedrawButton();
    }

    @FXML
    private void passButtonClicked() {
        mainCtr.handlePassButton();
    }

    /**
     * Sets the reference to the <code>Controller</code> object.
     * 
     * @param mainCtr
     *            the mainCtr to set
     */
    public void setMainCtr(Controller mainCtr) {
        this.mainCtr = mainCtr;
    }

    @Override
    public void drawTileOnBoard(int tileCode, int row, int col) {
        gc.drawImage(letterSprites.get(tileCode), 35 + col * 32, 10 + row * 32);
    }

    @Override
    public void drawBoard() {
        gc.drawImage(backgroundImage, 0, 0);
        gc.setFill(Color.GREEN);
        gc.fillRect(153, 494, 244, 52);
    }    

    @Override
    public void updateGameStateInfo(GameState gameState) {
        // doneButton enable/disable
        if (doneButton.disableProperty().get()) {
            if (gameState.isBoardLegal() && !gameState.isTileInHand()
                    && gameState.isWordsCorrect() && gameState.isPlayersTurn()) {
                doneButton.setDisable(false);
            }
        } else if (!gameState.isBoardLegal() || gameState.isTileInHand()
                || !gameState.isWordsCorrect() || !gameState.isPlayersTurn()) {
            doneButton.setDisable(true);
        }

        // redrawButton enable/disable
        if (redrawButton.disabledProperty().get()) {
            if (!gameState.isMovableTiles() && !gameState.isTileInHand() && gameState.getTilesInBag() > 0
                    && gameState.isPlayersTurn() && gameState.isGameStarted()) {
                redrawButton.setDisable(false);
            }
        } else if (gameState.isMovableTiles() || gameState.isTileInHand() || gameState.getTilesInBag() == 0
                || !gameState.isPlayersTurn() || !gameState.isGameStarted()) {
            redrawButton.setDisable(true);
        }

        // passButton enable/disable
        if (passButton.disabledProperty().get()) {
            if (!gameState.isMovableTiles() && !gameState.isTileInHand() && gameState.isPlayersTurn() && gameState.isGameStarted()) {
                passButton.setDisable(false);
            }
        } else if (gameState.isMovableTiles() || gameState.isTileInHand() || !gameState.isPlayersTurn() || !gameState.isGameStarted()) {
            passButton.setDisable(true);
        }

        // Show Player and Computer names and scores
        if (gameState.isPlayersTurn() && gameState.isGameStarted()) {
            playerNameLabel.setText(gameState.getPlayerName() + " <--");
            computerNameLabel.setText("Számítógép");
        } else if (gameState.isGameStarted()) {
            playerNameLabel.setText(gameState.getPlayerName());
            computerNameLabel.setText("Számítógép <--");
        } else {
            playerNameLabel.setText(gameState.getPlayerName());
            computerNameLabel.setText("Számítógép");
        }
        playerScoreLabel.setText(Integer.toString(gameState.getPlayerScore()));
        computerScoreLabel.setText(Integer.toString(gameState
                .getComputerScore()));

        // Show last words.
        if (gameState.getLastWords() != null) {
            if (gameState.getLastWords().size() > 1) {
                lastWordOrWordsLabel.setText("Legutóbbi szavak:");
            } else {
                lastWordOrWordsLabel.setText("Legutóbbi szó:");
            }

            StringJoiner sjLastWords = new StringJoiner(", ");
            for (String w : gameState.getLastWords()) {
                sjLastWords.add(w);
            }
            lastWordOrWordsListingLabel.setText(sjLastWords.toString());
            lastWordOrWordsPointsLabel.setText(Integer.toString(gameState
                    .getLastWordsPoints()));
        }

        // Show current words.
        if (gameState.getCurrentWords() != null) {
            if (gameState.getCurrentWords().size() > 1) {
                currWordOrWordsLabel.setText("Éppen kirakott szavak:");
            } else {
                currWordOrWordsLabel.setText("Éppen kirakott szó:");
            }
        }

        if (gameState.isBoardLegal()) {
            StringJoiner sjCurrWords = new StringJoiner(", ");
            for (String w : gameState.getCurrentWords()) {
                sjCurrWords.add(w);
            }
            if (gameState.isWordsCorrect()) {
                currWordOrWordsListingLabel.setTextFill(Color.BLACK);
                currWordOrWordsListingLabel.setText(sjCurrWords.toString());
                currWordOrWordsPointsLabel.setText(Integer.toString(gameState
                        .getCurrentWordsPoints()));
            } else {
                currWordOrWordsListingLabel.setTextFill(Color.RED);
                currWordOrWordsListingLabel.setText(sjCurrWords.toString());
                currWordOrWordsPointsLabel.setText("");
            }
        } else {
            currWordOrWordsListingLabel.setText("");
            currWordOrWordsPointsLabel.setText("");
        }

        // Show computer is thinking message.
        if (gameState.isPlayersTurn() || !gameState.isGameStarted()) {
            if (computerThinkingLabel.isVisible()) {
                computerThinkingLabel.setVisible(false);
            }
            if (computerThinkingIndicator.isVisible()) {
                computerThinkingIndicator.setVisible(false);
            }
        } else {
            if (!computerThinkingLabel.isVisible()) {
                computerThinkingLabel.setVisible(true);
            }
            if (!computerThinkingIndicator.isVisible()) {
                computerThinkingIndicator.setVisible(true);
            }
        }        
    }

    public String getLetterFromUser() {
        List<String> choices = new ArrayList<String>();
        choices.add("A");
        choices.add("Á");
        choices.add("B");
        choices.add("C");
        choices.add("CS");
        choices.add("D");
        choices.add("E");
        choices.add("É");
        choices.add("F");
        choices.add("G");
        choices.add("GY");
        choices.add("H");
        choices.add("I");
        choices.add("Í");
        choices.add("J");
        choices.add("K");
        choices.add("L");
        choices.add("LY");
        choices.add("M");
        choices.add("N");
        choices.add("NY");
        choices.add("O");
        choices.add("Ó");
        choices.add("Ö");
        choices.add("Ő");
        choices.add("P");
        choices.add("R");
        choices.add("S");
        choices.add("SZ");
        choices.add("T");
        choices.add("TY");
        choices.add("U");
        choices.add("Ú");
        choices.add("Ü");
        choices.add("Ű");
        choices.add("V");
        choices.add("Z");
        choices.add("ZS");

        ChoiceDialog<String> dialog = new ChoiceDialog<String>("A", choices);
        dialog.setTitle("Betű kiválasztása");
        dialog.setHeaderText(null);
        dialog.setContentText("Válaszd ki, milyen betűt szeretnél helyettesíteni a dzsókerrel:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get().toLowerCase();
        } else {
            return null;
        }
    }
    
    public boolean showAlertToUser(String header, String text, String okText) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Megerősítés");
        alert.setHeaderText(header);
        alert.setContentText(text);

        alert.setHeight(300);
        
        ButtonType buttonTypeOK = new ButtonType(okText);        
        ButtonType buttonTypeCancel = new ButtonType("Mégsem", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            return true;        
        } else {
            return false;
        }
    }
    
    public void showErrorMessage(String header, String mesg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(header);
        alert.setContentText(mesg);

        alert.showAndWait();
    }
    
    public void showMessage(String title, String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    @Override
    public void drawTileOnTray(int tileCode, int index) {
        gc.drawImage(letterSprites.get(tileCode), 163 + index * 32, 504);

    }

    @Override
    public void drawTileInHand(int tileCode, double mouseX, double mouseY) {
        gc.drawImage(letterSprites.get(tileCode), mouseX, mouseY);
    }

}
