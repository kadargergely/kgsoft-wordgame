package hu.unideb.kgsoft.scrabble.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class JavaVersionMessageController {

	@FXML
	Button okButton;
	@FXML
	Label javaVersionMessageLabel;

	public JavaVersionMessageController() {
	}

	@FXML
	private void initialize() {
		javaVersionMessageLabel
				.setText("A játékhoz 1.8.0_40 vagy újabb Java verzió szükséges. Telepíts újabb Java verziót!");
	}

	@FXML
	private void okButtonClicked() {
		Platform.exit();
	}

	public void setMessageText(String message) {
		javaVersionMessageLabel.setText(message);
	}
}
