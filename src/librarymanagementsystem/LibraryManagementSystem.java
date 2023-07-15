/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author AAR8
 */
public class LibraryManagementSystem extends Application {

    public static User user;
    public static Stage prevStage;

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("Borrowing.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        prevStage = stage;

        stage.show();
    }

    public static void navigateTo(String navigatorPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryManagementSystem.class.getResource(navigatorPath));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        prevStage.close();
        stage.setScene(scene);
        stage.show();
        prevStage = stage;
    }

    public static void newWindow(String navigatorPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryManagementSystem.class.getResource(navigatorPath));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
