package hu.unideb.kgsoft.scrabble.view;

import hu.unideb.kgsoft.scrabble.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginWindowController {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label passwordAgainLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordAgainField;
    @FXML
    private CheckBox newUserCheckBox;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;

    private Controller mainCtr;
    private ScrabbleApp mainApp;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public LoginWindowController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        passwordAgainLabel.setVisible(false);
        passwordAgainField.setVisible(false);
    }

    @FXML
    private void loginButtonClicked() {
        if (!newUserCheckBox.isSelected()) {
            if (mainCtr.login(usernameField.getText(), passwordField.getText())) {
                mainApp.switchToGameWindow();
                mainCtr.setupInitialView();
            }
        } else {
            if (mainCtr.newUser(usernameField.getText(),
                    passwordField.getText(), passwordAgainField.getText())) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Siker");
                alert.setHeaderText(null);
                alert.setContentText("Sikeresen létrehoztál egy új felhasználót "
                        + usernameField.getText() + " néven.");

                alert.showAndWait();
                mainApp.switchToGameWindow();
            }
        }
    }

    @FXML
    private void exitButtonClicked() {

    }

    @FXML
    private void checkBoxClicked() {
        if (newUserCheckBox.isSelected()) {
            passwordAgainLabel.setVisible(true);
            passwordAgainField.setVisible(true);
        } else {
            passwordAgainLabel.setVisible(false);
            passwordAgainField.setVisible(false);
        }
    }

    public void showErrorMessage(String header, String mesg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(header);
        alert.setContentText(mesg);

        alert.showAndWait();
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

    public void setMainApp(ScrabbleApp mainApp) {
        this.mainApp = mainApp;
    }
}
