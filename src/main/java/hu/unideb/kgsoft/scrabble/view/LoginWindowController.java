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
