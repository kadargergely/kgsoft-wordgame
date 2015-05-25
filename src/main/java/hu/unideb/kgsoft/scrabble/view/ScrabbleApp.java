package hu.unideb.kgsoft.scrabble.view;

import hu.unideb.kgsoft.scrabble.Controller;

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
    //TODO private AnchorPane loginRoot;
    
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
        } catch (IOException e) {
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
            
        } catch (IOException e) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void switchToGameWindow() {
        primaryStage.setTitle("Scrabble");
        
        initRootLayout();
        showScrabbleGameWindow();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void runApp(String[] args) {        
        launch(args);
    }
}
