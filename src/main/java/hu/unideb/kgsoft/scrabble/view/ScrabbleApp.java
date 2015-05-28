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
import static hu.unideb.kgsoft.scrabble.Main.logger;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ScrabbleApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;    
    
    private static Controller mainCtr;    

    /**
     * Sets the reference to the <code>Controller</code> object.
     * @param mainCtr the mainCtr to set
     */
    public static void setMainCtr(Controller mainCtr) {
        ScrabbleApp.mainCtr = mainCtr;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Bejelentkezés");
        
        showLoginWindow();
        logger.info("GUI started.");
    }
    
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ScrabbleApp.class.getResource("/fxmls/RootLayout.fxml"));
            loader.setController(new MenuBarController());
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();            
            primaryStage.setResizable(false);
            
            MenuBarController menuBarController = loader.getController();
            menuBarController.setMainCtr(mainCtr);
            menuBarController.setMainApp(this);
            mainCtr.setMenuBarController(menuBarController);
            
            logger.info("/fxmls/RootLayout.fxml loaded.");
        } catch (IOException e) {
        	logger.error("/fxmls/RootLayout.fxml couldn't be loaded.");
            e.printStackTrace();
        }
    }
    
    public void showScrabbleGameWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ScrabbleApp.class.getResource("/fxmls/ScrabbleGameWindow.fxml"));
            loader.setController(new GameWindowController());
            AnchorPane scrabbleGameWindow = (AnchorPane) loader.load();
            
            rootLayout.setCenter(scrabbleGameWindow);
            
            GameWindowController windowCtr = loader.getController();
            windowCtr.setMainCtr(mainCtr);
            mainCtr.setWindowCtr(windowCtr);
            
            logger.info("/fxmls/ScrabbleGameWindow.fxml loaded.");
        } catch (IOException e) {
        	logger.error("/fxmls/ScrabbleGameWindow.fxml couldn't be loaded.");
            e.printStackTrace();
        }
    }
    
    public void showLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/fxmls/LoginWindow.fxml"));
            loader.setController(new LoginWindowController());
            AnchorPane loginWindow = (AnchorPane) loader.load();
            
            Scene scene = new Scene(loginWindow);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            primaryStage.setResizable(false);
            
            LoginWindowController loginCtr = loader.getController();
            loginCtr.setMainApp(this);
            loginCtr.setMainCtr(mainCtr);
            mainCtr.setLoginController(loginCtr);
            
            logger.info("/fxmls/LoginWindow.fxml loaded.");
        } catch (IOException e) {
        	logger.error("/fxmls/LoginWindow.fxml couldn't be loaded.");
            e.printStackTrace();
        }
    }
    
    public void switchToGameWindow() {
        primaryStage.setTitle("Scrabble");
        
        initRootLayout();
        showScrabbleGameWindow();
        logger.info("Switched to the game window.");
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void runApp(String[] args) {        
        launch(args);
    }
}
