/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author AAR8
 */
public class LandingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label userName;

    @FXML
    private Button editProfile;

    @FXML
    private Button allBooks;

    @FXML
    private Button borrowers;

    @FXML
    private Button borrowBook;

    @FXML
    private Button myBooks;

    @FXML
    private HBox employeeBox;

    @FXML
    private HBox userBox;

    @FXML
    void handleAllBooks(ActionEvent event) {

        try {
            LibraryManagementSystem.newWindow("FXMLDocument.fxml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void handleBorrowBook(ActionEvent event) {
        try {
            LibraryManagementSystem.newWindow("Borrowing.fxml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void handleBorrowers(ActionEvent event) {

        try {
            LibraryManagementSystem.newWindow("Borrowers.fxml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void handleEditProfile(ActionEvent event) {
        try {
            LibraryManagementSystem.newWindow("UserInfo.fxml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void handleMyBooks(ActionEvent event) {
        try {
            LibraryManagementSystem.newWindow("ReturnBook.fxml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        User user = User.getInstance();
        if (user.getRole().equals("employee")) {
            userBox.setVisible(false);
        } else {
            employeeBox.setVisible(false);
        }
        userName.setText(user.getName());
    }

}
