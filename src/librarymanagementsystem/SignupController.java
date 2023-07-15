/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author AAR8
 */
public class SignupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField name;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField nationalNo;

    @FXML
    private TextField address;

    @FXML
    private ChoiceBox<String> role;

    @FXML
    private Button signup;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Hyperlink login;

    @FXML
    private Label errorLabel;

    @FXML
    void handleLogin(ActionEvent event) {
        try {
            LibraryManagementSystem.navigateTo("login.fxml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        ObservableList<String> items = role.getItems();
        items.add("employee");
        items.add("borrower");

        signup.setOnAction((event) -> {
            if (name.getText().length() > 0
                    && nationalNo.getText().length() > 0
                    && address.getText().length() > 0
                    && dateOfBirth.getValue().toString().length() > 0
                    && password.getCharacters().length() > 0
                    && confirmPassword.getCharacters().length() > 0) {
                if (!password.getCharacters().toString().equals(confirmPassword.getCharacters().toString())) {

                    errorLabel.setText("Password and confirm password are not the same");
                } else {
                    errorLabel.setText("");
                    DatabaseConnector connector = new DatabaseConnector();
                    connector.connect();
                    try {
                        connector.pst = connector.con.prepareStatement("insert into user (name, dateOfBirth, nationalNo, address, role, password) values (?, ?, ?, ?, ?, ?)");
                        connector.pst.setString(1, name.getText());
                        connector.pst.setDate(2, Date.valueOf(dateOfBirth.getValue().toString()));
                        connector.pst.setString(3, nationalNo.getText());
                        connector.pst.setString(4, address.getText());
                        connector.pst.setString(5, role.getValue());
                        connector.pst.setString(6, password.getCharacters().toString());

                        connector.pst.executeUpdate();

                        try {
                            LibraryManagementSystem.navigateTo("login.fxml");

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
//                        connector.con =
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

            } else {
                errorLabel.setText("All fields are required *");
            }
        });
    }

}
