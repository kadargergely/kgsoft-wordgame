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

import hu.unideb.kgsoft.scrabble.model.SavedGame;
import hu.unideb.kgsoft.scrabble.view.GameWindowController;
import hu.unideb.kgsoft.scrabble.view.LoginWindowController;
import hu.unideb.kgsoft.scrabble.view.MenuBarController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import static hu.unideb.kgsoft.scrabble.Main.logger;

/**
 * The <code>Controller</code> class represents the controller of the MVC
 * pattern. Controls the data flow into model object and updates the view
 * whenever data changes. It is the core of the application, controls the logic
 * and the flow of the game. Handles all user interactions. A single
 * <code>Controller</code> object exists at the same time.
 * <p>
 * The <code>Controller</code> object contains references to objects of other
 * classes that help to define the application logic and provide basic services.
 * These classes are:
 * <ul>
 * <li>{@link Dictionary}: contains the list of the words that can be formed.</li>
 * <li>{@link Gameboard}: represent the game board on which the tiles are
 * placed; can also check the validity of itself.</li>
 * <li>{@link Bag}: represents the bag from which tiles can be drawn.</li>
 * <li>{@link Player}: represents the human player.</li>
 * <li>{@link Computer}: represents the computer opponent.</li>
 * <li>{@link GameState}: informations about the game are transfered to the view
 * classes through the object of this class.</li>
 * </ul>
 * 
 * @author gergo
 *
 */
public class Controller {

    // private static Logger logger = LoggerFactory.getLogger(Controller.class);

    private Dictionary dict;
    private Gameboard gameBoard;
    private Bag bag;
    private Player player;
    private Computer computer;
    private GameState gameState;

    private GameWindowController view;
    private LoginWindowController loginView;
    private MenuBarController menuBarCtr;

    private UserDAO userDAO;
    private SavedGameDAO savedGameDAO;

    private String playerName;
    private boolean gameStarted;
    private boolean playersTurn;
    private boolean firstTurn;
    private int numOfPasses;

    /**
     * Constructor for creating a <code>Controller</code> object. The file
     * provided as parameter will be used to construct a {@link Dictionary}
     * object. All fields are set to default values.
     * 
     * @param inputStream
     *            the dictionary file containing a list of words
     * @throws FileNotFoundException
     *             if the file couldn't be found
     */
    public Controller(InputStreamReader inputStream) throws FileNotFoundException {
        logger.info("Controller class initialization...");
        userDAO = new UserDAO();
        savedGameDAO = new SavedGameDAO();
        dict = new Dictionary(inputStream);
        player = new Player();
        computer = new Computer();
        gameBoard = new Gameboard();
        gameState = new GameState();
        bag = new Bag();
        gameStarted = false;
        playerName = "Játékos";
    }

    /**
     * Initializes a new game. This consists of setting the fields of the board
     * and the scores to default values, replacing the bag with a filled one,
     * then drawing letters for the player then for the computer.
     */
    public void initNewGame() {
        logger.info("Initializing new game...");
        gameBoard = new Gameboard();
        player.setScore(0);
        computer.setScore(0);
        bag = new Bag();
        gameState = new GameState();
        player.getTray().reset();
        player.setTileInHand(Field.NOTILE);
        computer.getTray().reset();

        drawTilesFromBag(player.getTray());
        drawTilesFromBag(computer.getTray());

        gameStarted = true;
        playersTurn = true;
        firstTurn = true;
        numOfPasses = 0;

        gameState.setBoardLegal(false);
        gameState.setMovableTiles(false);
        gameState.setComputerScore(0);
        gameState.setPlayerScore(0);
        gameState.setPlayersTurn(playersTurn);
        gameState.setTilesInBag(bag.getNumOfTiles());
        gameState.setGameStarted(gameStarted);
        gameState.setPlayerName(playerName);
        menuBarCtr.saveGameDisabled(true);

        view.updateGameStateInfo(gameState);
        updateGameView();
    }

    /**
     * This method is called when the user moves the mouse. Updates the view so
     * that the tile in hand will follow the cursor.
     * 
     * @param mouseX
     *            the x coordinate of the cursor in the game canvas
     * @param mouseY
     *            the y coordinate of the cursor in the game canvas
     */
    public void handleMouseMotion(double mouseX, double mouseY) {
        if (player.getTileInHand() != -1) {
            updateGameView();
            view.drawTileInHand(player.getTileInHand(), mouseX, mouseY);
        }
    }

    /**
     * Called when the user clicks on the game board. Row and column indices are
     * provided as parameters. Mouse coordinates are needed to update the
     * position of the tile in hand. A tile can be placed on the board or picked
     * up from it. The legality of the board is checked and the played words,
     * their correctness, the number of points obtainable are also determined,
     * then requests a view update.
     * 
     * @param row
     *            the row index of the cell on which the user clicked
     * @param col
     *            the column index of the cell on which the user clicked
     * @param mouseX
     *            the x coordinate of the mouse in the game canvas
     * @param mouseY
     *            the y coordinate of the mouse in the game canvas
     */
    public void handleMousePressedOnBoard(int row, int col, double mouseX,
            double mouseY) {
        if (playersTurn) {
            if (player.getTileInHand() == -1) {
                player.setTileInHand(gameBoard.pickUpTile(row, col));
            } else if (gameBoard.setTile(row, col, player.getTileInHand())) {
                if (player.getTileInHand() == Letters.JOKER_CODE) {
                    gameBoard.getFields()[row][col].setJokerTileCode(Letters
                            .getC(view.getLetterFromUser()));
                    player.setTileInHand(-1);
                } else {
                    player.setTileInHand(-1);
                }
            }

            boolean isLegal = gameBoard.isLegal(firstTurn);
            gameState.setBoardLegal(isLegal);
            if (isLegal) {
                gameState.setCurrentWords(gameBoard.getPlayedWords());
                gameState.setCurrentWordsPoints(gameBoard.calcPoints());
                gameState.setWordsCorrect(gameBoard.wordsAreCorrect(dict));
            }
            gameState.setTileInHand(player.getTileInHand() != -1);
            gameState.setMovableTiles(gameBoard.getMovableTiles() > 0);
            view.updateGameStateInfo(gameState);

            updateGameView();
            if (player.getTileInHand() != -1) {
                view.drawTileInHand(player.getTileInHand(), mouseX, mouseY);
            }
        }
    }

    /**
     * Called when the user clicks on the tray. The index position on which the
     * user clicked and the mouse coordinates are provided as parameters. Mouse
     * coordinates are needed to update the position of the tile in hand. A tile
     * can be placed on the tray or picked up from it. Requests a view update.
     * 
     * @param index
     *            the index position at which the user clicked on the tray
     * @param mouseX
     *            the x coordinate of the mouse in the game canvas
     * @param mouseY
     *            the y coordinate of the mouse in the game canvas
     */
    public void handleMousePressedOnTray(int index, double mouseX, double mouseY) {
        if (playersTurn) {
            if (player.getTileInHand() == -1) {
                player.setTileInHand(player.getTray().pickTile(index));
            } else if (player.getTray().setTile(player.getTileInHand(), index)) {
                player.setTileInHand(-1);
            }

            gameState.setTileInHand(player.getTileInHand() != -1);
            view.updateGameStateInfo(gameState);

            updateGameView();
            if (player.getTileInHand() != -1) {
                view.drawTileInHand(player.getTileInHand(), mouseX, mouseY);
            }
        }
    }

    /**
     * Called when the computer finished thinking, placed a word if could, and
     * ends its turn by drawing new tiles from the bag and checking if the game
     * ended. Requests a view update.
     */
    public void computerReady() {

        List<String> playedWords = gameBoard.getPlayedWords();
        if (playedWords == null) {            
            if (bag.getNumOfTiles() == 0) {
                logger.info("Computer passes its turn.");
                numOfPasses++;
                logger.info(String.format("Number of subsequent passes is %d",
                        numOfPasses));
                checkGameEnd();
            } else {
                numOfPasses = 0;
                logger.info("Computer redraws its tiles.");
                redrawTiles(computer.getTray());
            }
        } else {
            numOfPasses = 0;
            drawTilesFromBag(computer.getTray());
            logger.info("Computer finished its turn, by placing letters on board.");
            logger.info("Computer draws new tiles from bag.");
        }
        playersTurn = true;
        gameState.setPlayersTurn(playersTurn);
        int points = gameBoard.calcPoints();
        computer.setScore(computer.getScore() + points);
        gameState.setComputerScore(computer.getScore());
        gameState.setLastWords(gameBoard.getPlayedWords());
        gameState.setLastWordsPoints(points);
        gameState.setTilesInBag(bag.getNumOfTiles());

        gameBoard.finalizeFields();
        gameState.setBoardLegal(gameBoard.isLegal(firstTurn));
        gameState.setMovableTiles(gameBoard.getMovableTiles() > 0);

        updateGameView();
        view.updateGameStateInfo(gameState);
    }

    /**
     * Draws new tiles from the bag instead of the current ones. The current
     * tiles are put back into the bag. The tray on which the operation should
     * be performed is provided as parameter.
     * 
     * @param tray
     *            the <code>Tray</code> object on which the operation should be
     *            performed
     */
    void redrawTiles(Tray tray) {
        List<Integer> newTiles = new ArrayList<Integer>();
        for (int i = 0; i < Tray.TRAY_SIZE; i++) {
            int randTile = bag.getRandTile();
            if (randTile != -1) {
                newTiles.add(randTile);
            } else {
                break;
            }
        }
        for (int i = 0; i < Tray.TRAY_SIZE; i++) {
            int pickedTile = tray.pickTile(i);
            if (pickedTile != -1) {
                bag.putBack(pickedTile);
            }
        }
        for (int tile : newTiles) {
            tray.addTile(tile);
        }
    }

    /**
     * Draws new tiles from the bag, to fill the tray. The tray on which the
     * operation should be performed is provided as parameter.
     * 
     * @param tray
     *            the <code>Tray</code> object on which the operation should be
     *            performed
     */
    void drawTilesFromBag(Tray tray) {
        while (tray.getNumOfLetters() < Tray.TRAY_SIZE) {
            int randTile = bag.getRandTile();
            if (randTile == -1) {
                break;
            } else {
                tray.addTile(randTile);
            }
        }
    }

    /**
     * Called when the user wants to end his turn and clicks on the done button.
     * Passes the turn to the computer and the computer thinking thread starts.
     * Requests view update.
     */
    public void handleDoneButton() {
        logger.info(String.format(
                "%s finished his/her turn by placing tiles on the board.",
                playerName));
        numOfPasses = 0;
        playersTurn = false;
        if (firstTurn) {
            firstTurn = false;
        }
        gameState.setPlayersTurn(playersTurn);
        int points = gameBoard.calcPoints();
        player.setScore(player.getScore() + points);
        gameState.setPlayerScore(player.getScore());
        gameState.setLastWords(gameBoard.getPlayedWords());
        gameState.setLastWordsPoints(points);
        logger.info(String.format("%s draws tiles from the bag.", playerName));
        drawTilesFromBag(player.getTray());
        gameState.setTilesInBag(bag.getNumOfTiles());
        gameBoard.finalizeFields();
        gameState.setBoardLegal(gameBoard.isLegal(firstTurn));
        view.updateGameStateInfo(gameState);
        updateGameView();
        menuBarCtr.saveGameDisabled(false);

        // TODO debug
        // StringJoiner sj = new StringJoiner(", ", "Computer's letters: ", "");
        // for (int code : computer.getTray().getLetters()) {
        // sj.add(Letters.getL(code));
        // }
        // System.out.println(sj);
        // System.out.println();
        // System.out.println();

        view.computerThread(computer, gameBoard, dict);
    }

    /**
     * Called when the user wants to pass his turn and clicks on the pass
     * button. Checks game end. Starts computer thinking thread. Requests view
     * update.
     */
    public void handlePassButton() {
        logger.info(String.format("%s passes his/her turn.", playerName));
        numOfPasses++;
        checkGameEnd();
        playersTurn = false;
        if (firstTurn) {
            firstTurn = false;
        }
        gameState.setPlayersTurn(playersTurn);
        gameState.setLastWords(gameBoard.getPlayedWords());
        gameState.setLastWordsPoints(0);
        view.updateGameStateInfo(gameState);
        updateGameView();
        menuBarCtr.saveGameDisabled(false);        

        view.computerThread(computer, gameBoard, dict);
    }

    /**
     * Called when the user wants to draw new tiles instead of the current ones
     * and clicks on the redraw button. Starts computer thinking thread.
     * Requests view update.
     */
    public void handleRedrawButton() {
        logger.info(String.format("%s redraws his/her tiles.", playerName));
        numOfPasses = 0;
        playersTurn = false;
        if (firstTurn) {
            firstTurn = false;
        }
        gameState.setPlayersTurn(playersTurn);
        gameState.setLastWords(gameBoard.getPlayedWords());
        gameState.setLastWordsPoints(0);
        redrawTiles(player.getTray());
        gameState.setTilesInBag(bag.getNumOfTiles());
        menuBarCtr.saveGameDisabled(false);

        view.updateGameStateInfo(gameState);
        updateGameView();        

        view.computerThread(computer, gameBoard, dict);
    }

    /**
     * Checks if the game has ended. If the game ended, a message is shown.
     */
    private void checkGameEnd() {
        if (numOfPasses == 4) {
            logger.info(String.format("Game ended. %s won.",
                    player.getScore() >= computer.getScore() ? playerName
                            : "Computer"));
            System.out.println("viszontlatasra kivanok!");
        }
    }

    /**
     * Updates the game view. Updates the appropriate methods of the view class
     * to achieve this. The board and the tiles will be painted.
     */
    public void updateGameView() {
        view.drawBoard();
        for (int i = 0; i < Gameboard.BOARD_SIZE; i++) {
            for (int j = 0; j < Gameboard.BOARD_SIZE; j++) {
                if (gameBoard.getFields()[i][j].getTileCode() != Field.NOTILE) {
                    if (gameBoard.getFields()[i][j].getTileCode() == Letters.JOKER_CODE) {
                        view.drawTileOnBoard(
                                gameBoard.getFields()[i][j].getJokerTileCode()
                                        + Letters.getNum(), i, j);
                    } else {
                        view.drawTileOnBoard(
                                gameBoard.getFields()[i][j].getTileCode(), i, j);
                    }
                }
            }
        }
        for (int i = 0; i < Tray.TRAY_SIZE; i++) {
            if (player.getTray().getTileCodeAt(i) != -1) {
                view.drawTileOnTray(player.getTray().getTileCodeAt(i), i);
            }
        }
    }

    /**
     * Sets up the view so that it shows a "pre-game" state.
     */
    public void setupInitialView() {
        gameState.setPlayerName(playerName);
        gameState.setPlayerScore(0);
        gameState.setComputerScore(0);
        gameState.setCurrentWords(null);
        gameState.setLastWords(null);
        gameState.setPlayersTurn(true);
        gameState.setGameStarted(false);
        view.updateGameStateInfo(gameState);
        menuBarCtr.saveGameDisabled(true);
    }

    /**
     * Tries to log in a user by calling the appropriate method of the DAO
     * object. The user name and password are provided as parameters. If the
     * database is unreachable or the user name and password are not correct, an
     * error message is shown. Returns <code>true</code> on success.
     * 
     * @param username
     *            the name of the user who wants to log in
     * @param passwd
     *            the password of the user
     * @return <code>true</code> if the user successfully logged in,
     *         <code>false</code> otherwise
     */
    public boolean login(String username, String passwd) {
        try {
            if (userDAO.authenticateUser(username, passwd)) {
                logger.info(String.format(
                        "User with user name '%s' successfully logged in.",
                        username));
                playerName = username;
            } else {
                logger.info("Coludn't log in! Wrong user name or password.");
                loginView.showErrorMessage("Hiba a bejelentkezésnél!",
                        "Hibás felhasználónév vagy jelszó.");
                return false;
            }
        } catch (IOException e) {
            logger.error("Login error! Database user name and password not available.");
            loginView
                    .showErrorMessage("Hiba a bejelentkezésnél!",
                            "Nem elérhetőek a központi adatbázishoz való csatlakozáshoz szükséges adatok.");
            return false;
        }

        return true;
    }

    /**
     * Registers a new user by calling the appropriate method of the DAO object.
     * The user name and password are provided as parameters. If the password
     * and the retyped password are not the same, an error message is shown. An
     * error message is shown also when the user name already exists in the
     * database, or the database server is unreachable.
     * 
     * @param username
     *            the user name to register
     * @param passwd
     *            the password of the new user
     * @param passwdAgain
     *            the retyped password
     * @return <code>true</code> if the new user was registered successfully,
     *         <code>false</code> otherwise
     */
    public boolean newUser(String username, String passwd, String passwdAgain) {
        try {
            if (passwd.equals(passwdAgain)) {
                if (userDAO.registerUser(username, passwd)) {
                    logger.info(String.format(
                            "New user %s successfully registered.", username));
                    playerName = username;
                } else {
                    logger.warn(String
                            .format("Couldn't register new user. Database is unreachable."));
                    loginView
                            .showErrorMessage(
                                    "Nem lehet léltrehozni az új felhasználót!",
                                    "Nem sikerült kapcsolódni a központi adatbázishoz.");
                    return false;
                }
            } else {
                logger.info("Couldn't register new user. Retyped password doesn't match the intial one.");
                loginView.showErrorMessage("Elírtad a jelszót.",
                        "A két jelszó mező tartalma nem egyezik.");
                return false;
            }
        } catch (ExistingUserException e) {
            logger.info(String
                    .format("Couldn't register new user. User name '%s' already exists.",
                            username));
            loginView.showErrorMessage(
                    "Nem lehet léltrehozni az új felhasználót!",
                    "A felhasználónév már létezik.");
            return false;
        } catch (IOException e) {
            logger.error("Couldn't register new user. Database user name and password not available.");
            loginView
                    .showErrorMessage(
                            "Nem lehet léltrehozni az új felhasználót!",
                            "Nem elérhetőek a központi adatbázishoz való csatlakozáshoz szükséges adatok.");
            return false;
        }

        return true;
    }

    /**
     * Called when a user wants to start a new game. A confirmation message is
     * displayed, then a new game is initialized if the user confirms the
     * operation.
     */
    public void startNewGame() {
        if (gameStarted) {
            if (view.showAlertToUser("Új játék",
                    "Ha nem mentetted el a játékot, elveszíted.\n"
                            + "Biztos új játékot kezdesz?", "Új játék")) {
                initNewGame();
            }
        } else {
            initNewGame();
        }
    }

    /**
     * Called when the user wants to load a game. A confirmation message is
     * displayed. Loads a game by calling the appropriate method of a DAO
     * object. If there is no saved game, or the database is unreachable, an
     * error message is shown. Requests view update.
     */
    public void loadGame() {
        if (gameStarted) {
            if (!view.showAlertToUser("Játék betöltése",
                    "Ha nem mentetted el a játékot, elveszíted.\n"
                            + "Biztos betöltöd a játékot?", "Betöltés")) {
                return;
            }
        }
        SavedGame savedGame = null;
        try {
            savedGame = savedGameDAO.getSavedGameByUserName(playerName);
            logger.info("Saved game successfully loaded.");
        } catch (IOException e) {
            view.showErrorMessage("Játék betöltése",
                    "Nem lehet elérni az adatbázist.");
            logger.warn("Loading game failed. Database is unreachable.");
            e.printStackTrace();
            return;
        } catch (NoSavedGameException e) {
            view.showErrorMessage("Játék betöltése", "Nincs elmentett játék.");
            logger.info("Couldn't load game. No saved game to load.");
            return;
        }
        if (savedGame != null) {
            gameBoard = new Gameboard(savedGame.getFields());
            player.setScore(savedGame.getPlayerScore());
            computer.setScore(savedGame.getComputerScore());
            bag = new Bag(savedGame.getBagRemaining());
            player.getTray().setLetters(savedGame.getPlayerTray());
            player.setTileInHand(savedGame.getTileInHand());
            computer.getTray().setLetters(savedGame.getComputerTray());

            gameStarted = true;
            playersTurn = savedGame.isPlayersTurn();
            firstTurn = false;
            numOfPasses = 0;

            gameState.setBoardLegal(gameBoard.isLegal(firstTurn));
            gameState.setMovableTiles(gameBoard.getMovableTiles() > 0);
            gameState.setComputerScore(computer.getScore());
            gameState.setPlayerScore(player.getScore());
            gameState.setPlayersTurn(playersTurn);
            gameState.setTilesInBag(bag.getNumOfTiles());
            gameState.setGameStarted(gameStarted);
            gameState.setPlayerName(playerName);
            gameState.setLastWords(Arrays.asList(savedGame.getLastWords()));
            gameState.setLastWordsPoints(savedGame.getLastWordsPoints());
            gameState.setCurrentWords(gameBoard.getPlayedWords());
            gameState.setCurrentWordsPoints(gameBoard.calcPoints());
            gameState.setWordsCorrect(gameBoard.wordsAreCorrect(dict));
            view.updateGameStateInfo(gameState);
            updateGameView();
        }
    }

    /**
     * Called when the user wants to save the game. A confirmation message is
     * shown. A new thread is started for the game saving operation.
     */
    public void handleSaveGameClicked() {
        if (view.showAlertToUser("Játék mentése",
                "Ha van előző játékmentésed, akkor felülíródik.\n"
                        + "Biztos elmented a játékot?", "Mentés")) {
            view.saveGameThread();
        }
    }

    /**
     * Saves the current game by calling the appropriate method of a DAO object.
     * If the database is unreachable, an error message is shown.
     */
    public void saveGame() {
        int[] playerTray = new int[Tray.TRAY_SIZE];
        for (int i = 0; i < playerTray.length; i++) {
            playerTray[i] = player.getTray().getTileCodeAt(i);
        }
        int[] computerTray = new int[Tray.TRAY_SIZE];
        for (int i = 0; i < computerTray.length; i++) {
            computerTray[i] = computer.getTray().getTileCodeAt(i);
        }

        String[] lastWordsArray = gameState.getLastWords().toArray(
                new String[gameState.getLastWords().size()]);

        SavedGame savedGame = new SavedGame(gameBoard.getFields(), playerTray,
                computerTray, player.getTileInHand(), (playersTurn ? 1 : 0),
                player.getScore(), computer.getScore(), lastWordsArray,
                gameState.getLastWordsPoints(), bag.getRemaining());
        try {
            savedGameDAO.newSavedGame(playerName, savedGame);
            logger.info("Game successfully saved.");
        } catch (IOException e) {
            logger.warn("Saving game failed. Database is unreachable.");
            view.showErrorMessage("Játék mentése",
                    "Nem lehet elérni az adatbázist.");
            e.printStackTrace();
        }
    }

    /**
     * Called when the game saving operation finished. The user receives a
     * message.
     */
    public void gameSaved() {
        view.showMessage("Játékmentés", "A játékállás sikeresen elmentve.");
    }

    /**
     * Sets the reference to the <code>GameWindowController</code> object which
     * represents the view.
     * 
     * @param windowCtr
     *            the windowCtr to set
     */
    public void setWindowCtr(GameWindowController windowCtr) {
        this.view = windowCtr;
    }

    /**
     * Sets the reference to the <code>LoginWindowController</code> object,
     * which represents the login window.
     * 
     * @param loginCtr
     *            the loginCtr to set
     */
    public void setLoginController(LoginWindowController loginCtr) {
        this.loginView = loginCtr;
    }

    /**
     * Sets the reference to the <code>setMenuBarController</code> object, which
     * represents the menu bar.
     * 
     * @param menuBarCtr
     *            the menuBarCtr to set
     */
    public void setMenuBarController(MenuBarController menuBarCtr) {
        this.menuBarCtr = menuBarCtr;
    }

    /**
     * Returns the <code>Player</code> object which represents the human player.
     * 
     * @return the <code>Player</code> object which represents the human player
     */
    Player getPlayer() {
        return player;
    }

    /**
     * Returns the <code>Bag</code> object which represents the bag from which
     * the players can draw tiles.
     * 
     * @return the <code>Bag</code> object
     */
    Bag getBag() {
        return bag;
    }

    /**
     * Sets a new value for the boolean field which determines if the current
     * turn is the human player's.
     * 
     * @param value
     *            the boolean value to set
     */
    void setPlayersTurn(boolean value) {
        this.playersTurn = value;
    }
}
