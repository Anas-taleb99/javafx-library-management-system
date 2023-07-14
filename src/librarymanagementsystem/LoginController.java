/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author AAR8
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        login.setOnAction((event) -> {
            if (name.getText().length() > 0
                    && password.getCharacters().length() > 0) {
                DatabaseConnector connector = new DatabaseConnector();
                connector.connect();
                try {
                    String query = "SELECT * FROM user WHERE name = ? AND password = ?";
                    connector.pst = connector.con.prepareStatement(query);
                    connector.pst.setString(1, name.getText());
                    connector.pst.setString(2, password.getCharacters().toString());
                    ResultSet rs = connector.pst.executeQuery();

                    if (!rs.next()) {
                        errorLabel.setText("The user name or the password is incorrect !");
                    } else {
                        errorLabel.setText("");

                        while (rs.next()) {
                            User.getInstance().setId(rs.getInt("id"));
                            User.getInstance().setName(rs.getString("name"));
                            User.getInstance().setAddress(rs.getString("address"));
                            User.getInstance().setRole(rs.getString("role"));
                            User.getInstance().setNationalNo(rs.getString("nationalNo"));

                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            } else {
                errorLabel.setText("All fields are required");
            }
        });
    }

}
