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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author AAR8
 */
public class UserInfoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Text userName;

    @FXML
    private TextField nationalNo;

    @FXML
    private TextField address;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Button save;

    @FXML
    void handleSave(ActionEvent event) {
        if (nationalNo.getText().length() > 0
                && address.getText().length() > 0
                && dateOfBirth.getValue().toString().length() > 0) {
            DatabaseConnector connector = new DatabaseConnector();
            connector.connect();
            User user = User.getInstance();
            try {
                connector.pst = connector.con.prepareStatement("update user set nationalNo = ? , address = ?, dateOfBirth = ? where id = ?");
                connector.pst.setString(1, nationalNo.getText());
                connector.pst.setString(2, address.getText());
                connector.pst.setDate(3, Date.valueOf(dateOfBirth.getValue().toString()));

                connector.pst.setInt(4, user.getId());

                connector.pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Updating data");

                alert.setHeaderText("Data updated Successfully");
                alert.setContentText("Data updated Successfully!");
                alert.showAndWait();

                user.setNationalNo(nationalNo.getText());
                user.setAddress(address.getText());
                user.setBirthDate(dateOfBirth.getValue());

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
//                            errorLabel.setText("Password and confirm password are not the same");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        User user = User.getInstance();
        nationalNo.setText(user.getNationalNo());
        address.setText(user.getAddress());
        dateOfBirth.setValue(user.getBirthDate());
    }

}
