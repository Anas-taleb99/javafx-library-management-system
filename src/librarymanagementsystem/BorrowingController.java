/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.Alert;
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
public class BorrowingController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private Button search;

    @FXML
    private Button borrow;
    @FXML
    private TextField nameField;

    int myIndex = 0;
    int selectedRowId;

    @FXML
    void BorrowBook(ActionEvent event) {

        if (selectedRowId > 0) {
            DatabaseConnector connector = new DatabaseConnector();
            connector.connect();
            try {
                connector.pst = connector.con.prepareStatement("insert into borrow (bookId, userId, borrowAt) values (?, ?, ?)");
                connector.pst.setInt(1, selectedRowId);
                connector.pst.setInt(2, User.getInstance().getId());
                LocalDate currentDate = LocalDate.now();

// convert the LocalDate to a java.sql.Date
                Date sqlDate = Date.valueOf(currentDate);
                connector.pst.setDate(3, Date.valueOf(sqlDate.toString()));

                connector.pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Borrowing a Book");

                alert.setHeaderText("Borrowing A book");
                alert.setContentText("Borrowed !");
                alert.showAndWait();
//                        connector.con =
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void SearchBook(ActionEvent event) {
        String content = nameField.getText();
        if (content.length() > 0) {
            DatabaseConnector connector = new DatabaseConnector();
            connector.connect();
            ObservableList<MyBook> books = FXCollections.observableArrayList();

            try {
                connector.pst = connector.con.prepareStatement("select id, name, author, publisher, releaseDate, createdOn from book where name like ?");
//                connector.pst.setString(1, content);
                connector.pst.setString(1, "%" + content + "%");
                ResultSet rs = connector.pst.executeQuery();

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
            bookTable.setRowFactory(tv -> {
                TableRow<MyBook> myRow = new TableRow<>();
                myRow.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 1 && (!myRow.isEmpty())) {
                        myIndex = bookTable.getSelectionModel().getSelectedIndex();
                        selectedRowId = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getId()));
                    }
                });
                return myRow;
            });
        } else {
            table();
        }
    }

    public void table() {
        DatabaseConnector connector = new DatabaseConnector();
        connector.connect();
        ObservableList<MyBook> books = FXCollections.observableArrayList();

        try {
            connector.pst = connector.con.prepareStatement("select id, name, author, publisher, releaseDate, createdOn from book");
            ResultSet rs = connector.pst.executeQuery();
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
                    selectedRowId = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getId()));
                }
            });
            return myRow;
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table();
    }

}