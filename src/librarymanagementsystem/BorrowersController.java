/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author AAR8
 */
public class BorrowersController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Borrower> bookTable;

    @FXML
    private TableColumn<Borrower, Integer> idColumn;

    @FXML
    private TableColumn<Borrower, String> nameColumn;

    @FXML
    private TableColumn<Borrower, String> bookColumn;

    @FXML
    private TableColumn<Borrower, LocalDate> borrowColumn;

    @FXML
    private TableColumn<Borrower, LocalDate> returnColumn;

    @FXML
    private TextField nameField;

    @FXML
    private Button search;

    @FXML
    void searchBorrower(ActionEvent event) {
        DatabaseConnector connector = new DatabaseConnector();
        connector.connect();
        ObservableList<Borrower> borrowers = FXCollections.observableArrayList();

        
        try {
//            connector.pst = connector.con.prepareStatement("select id, name, author, publisher, releaseDate, createdOn from book");
            connector.pst = connector.con.prepareStatement("SELECT br.id, u.name as userName, bk.name as bookName, borrowAt FROM borrow br inner JOIN user u on userId = br.userId INNER JOIN book bk on bookId = bk.id where u.name like ?");
            connector.pst.setString(1, "%" + User.getInstance().getName() + "%");

            ResultSet rs = connector.pst.executeQuery();
            while (rs.next()) {
                Borrower borrower = new Borrower();
                borrower.id = rs.getInt("id");
                borrower.name = rs.getString("userName");
                borrower.book = rs.getString("bookName");
                borrower.borrowAt = rs.getDate("borrowAt").toLocalDate();
                borrowers.add(borrower);
            }
            bookTable.setItems(borrowers);
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            bookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook()));
//            publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
            borrowColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowAt()));
////            createdOnColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCreatedOn()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void table() {
        DatabaseConnector connector = new DatabaseConnector();
        connector.connect();
        ObservableList<Borrower> borrowers = FXCollections.observableArrayList();

        try {
//            connector.pst = connector.con.prepareStatement("select id, name, author, publisher, releaseDate, createdOn from book");
            connector.pst = connector.con.prepareStatement("SELECT br.id, u.name as userName, bk.name as bookName, borrowAt FROM borrow br inner JOIN user u on userId = br.userId INNER JOIN book bk on bookId = bk.id");

            ResultSet rs = connector.pst.executeQuery();
            while (rs.next()) {
                Borrower borrower = new Borrower();
                borrower.id = rs.getInt("id");
                borrower.name = rs.getString("userName");
                borrower.book = rs.getString("bookName");
                borrower.borrowAt = rs.getDate("borrowAt").toLocalDate();
                borrowers.add(borrower);
            }
            bookTable.setItems(borrowers);
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            bookColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook()));
//            publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
            borrowColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowAt()));
////            createdOnColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCreatedOn()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table();
    }

}
