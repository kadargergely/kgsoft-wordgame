package hu.unideb.kgsoft.scrabble.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static hu.unideb.kgsoft.scrabble.Main.logger;

public class JavaVersionMessage extends Application {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Java verzió");

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource(
					"/fxmls/updateJavaMessage.fxml"));
			loader.setController(new JavaVersionMessageController());
			AnchorPane loginWindow = (AnchorPane) loader.load();

			Scene scene = new Scene(loginWindow);
			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.setResizable(false);
			
			logger.info("/fxmls/updateJavaMessage.fxml file loaded.");
		} catch (IOException e) {
			logger.error("/fxmls/updateJavaMessage.fxml couldn't be loaded.");
			e.printStackTrace();
		}
	}

	public static void showMessage(String[] args) {
		launch(args);
	}
}
