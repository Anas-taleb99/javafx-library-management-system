/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.print.Book;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import java.time.ZoneId;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableRow;

/**
 *
 * @author AAR8
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Pane bookForm;

    @FXML
    private TextField bookName;

    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookPublisher;

    @FXML
    private DatePicker bookRelease;

    @FXML
    private TableView<MyBook> bookTable;

    @FXML
    private TableColumn<MyBook, Integer> idColumn;

    @FXML
    private TableColumn<MyBook, String> nameColumn;

    @FXML
    private TableColumn<MyBook, String> authorColumn;

    @FXML
    private TableColumn<MyBook, String> publisherColumn;

    @FXML
    private TableColumn<MyBook, LocalDate> releaseColumn;

    @FXML
    private TableColumn<MyBook, LocalDate> createdOnColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    public boolean bookValidation() {
        if (bookName.getText().trim().length() > 1 && bookAuthor.getText().trim().length() > 1
                && bookPublisher.getText().trim().length() > 1 && (!bookRelease.getValue().toString().trim().isEmpty())) {
            return true;
        }
        return false;

    }

    @FXML
    void AddBook(ActionEvent event) {
        String bName, bAuthor, bPublisher;
        LocalDate bRelease;
        connect();
        bName = bookName.getText();
        bAuthor = bookAuthor.getText();
        bPublisher = bookPublisher.getText();
        bRelease = bookRelease.getValue();
        try {
            pst = con.prepareStatement("insert into book (name, author, publisher, releaseDate, createdOn) values (?, ?, ?, ?, ?)");
            pst.setString(1, bName);
            pst.setString(2, bAuthor);
            pst.setString(3, bPublisher);
            pst.setDate(4, Date.valueOf(bRelease));
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            pst.setDate(5, Date.valueOf(currentDate));

            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test connection");
            alert.setHeaderText("Adding book");
            alert.setContentText("Added!");

            alert.showAndWait();

            bookName.setText("");
            bookAuthor.setText("");
            bookPublisher.setText("");
            bookRelease.setValue(null);
            table();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void table() {
        connect();
        ObservableList<MyBook> books = FXCollections.observableArrayList();

        try {
            pst = con.prepareStatement("select id, name, author, publisher, releaseDate, createdOn from book");
            ResultSet rs = pst.executeQuery();
            System.out.println("here ?");
            while (rs.next()) {
                MyBook book = new MyBook();
                System.out.println("here " + rs.getInt("id"));
                book.id = rs.getInt("id");
                book.name = rs.getString("name");
                book.author = rs.getString("author");
                book.publisher = rs.getString("publisher");
                book.releaseDate = rs.getDate("releaseDate").toLocalDate();
                book.createdOn = rs.getDate("createdOn").toLocalDate();
                System.out.println("ayooo " + book.createdOn);

                books.add(book);
            }
            bookTable.setItems(books);
            idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
            publisherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
            releaseColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getReleaseColumn()));
//            createdOnColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCreatedOn()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("arrrarn");

        bookTable.setRowFactory(tv -> {
            TableRow<MyBook> myRow = new TableRow<>();
            myRow.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = bookTable.getSelectionModel().getSelectedIndex();

                    id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getId()));
                    bookName.setText(bookTable.getItems().get(myIndex).getName());
                    bookAuthor.setText(bookTable.getItems().get(myIndex).getAuthor());
                    bookPublisher.setText(bookTable.getItems().get(myIndex).getPublisher());
                    bookRelease.setValue(bookTable.getItems().get(myIndex).getReleaseColumn());

                }
            });
            return myRow;
        });
    }

    @FXML
    void deleteBook(ActionEvent event) {
        myIndex = bookTable.getSelectionModel().getSelectedIndex();

        id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getId()));

        try {
            pst = con.prepareStatement("delete from book where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleting a Book");

            alert.setHeaderText("Deleting A book");
            alert.setContentText("Deleted !");
            alert.showAndWait();
            table();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void updateBook(ActionEvent event) {
        String name, author, publisher;
        LocalDate releaseDate;

        myIndex = bookTable.getSelectionModel().getSelectedIndex();

        id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getId()));

        name = bookName.getText();
        author = bookAuthor.getText();
        publisher = bookPublisher.getText();
        releaseDate = bookRelease.getValue();
        try {
            pst = con.prepareStatement("update book set name = ?,author = ? ,publisher = ?, releaseDate = ? where id = ? ");
            pst.setString(1, name);
            pst.setString(2, author);
            pst.setString(3, publisher);
            pst.setDate(4, Date.valueOf(releaseDate));

            pst.setInt(5, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Student Registationn");

            alert.setHeaderText("Student Registation");
            alert.setContentText("Updateddd!");
            alert.showAndWait();
            table();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/librarymanagementsystem", "root", "");

        } catch (ClassNotFoundException ex) {

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connect();
        table();
    }

}
