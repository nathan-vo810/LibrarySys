package Interface;

import DB.hsqldb.HSQLDB;
import Models.Books;
import Models.Students;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

import javafx.application.Platform;

import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;

/**
 * Created by NhatAnh on 12/11/16.
 */
public class BookSearch {

    static Stage searchWindow = new Stage();
    static TextField searchBox = new TextField();
    static TableView<Books> resultTable = new TableView<>();

    public static void displayBookSearch(String username, String password, HSQLDB user) {


        ResultSet studentInfo = null;
        try {
            studentInfo = user.query("SELECT * FROM STUDENT WHERE LID = '" + username + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Students currentStudent = new Students(studentInfo);

        //Search Panel
        searchBox.setMinWidth(650);

        Button searchBtn = new Button();
        searchBtn.setText("Search");

        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(10,10,10,10));
        searchPane.setSpacing(10);
        searchPane.getChildren().addAll(searchBox,searchBtn);

        Label note = new Label("Search by Title, ISBN or Author");

        //Result table
        resultTable.setMinHeight(500);

        TableColumn<Books, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Books, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Books, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Books, Integer> remainCol = new TableColumn<>("Remain");
        remainCol.setCellValueFactory(new PropertyValueFactory<>("remain"));

        resultTable.getColumns().addAll(isbnCol,titleCol,authorCol,remainCol);
        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        resultTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        searchBtn.setOnAction(e-> searchBook(user));

        Button reserveBtn = new Button();
        reserveBtn.setText("Reserve");

        reserveBtn.setOnAction(e->{
            reserveBook(resultTable,user,currentStudent);
            try {
                StudentProfileScreen.displayBorrowed(user,currentStudent);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            searchBook(user);
        });

        GridPane searchLayout = new GridPane();
        searchLayout.setPadding(new Insets(10, 10, 10, 10));
        searchLayout.setVgap(10);
        searchLayout.setHgap(8);

        GridPane.setConstraints(searchPane,0,0);
        GridPane.setConstraints(note,0,1);
        GridPane.setConstraints(resultTable,0,2);
        GridPane.setConstraints(reserveBtn,0,3);
        //searchLayout.setHalignment(searchPane,HPos.CENTER);
        searchLayout.setHalignment(note,HPos.CENTER);
        //searchLayout.setHalignment(resultTable,HPos.CENTER);


        searchLayout.getChildren().addAll(searchPane,note,resultTable,reserveBtn);
        searchLayout.setAlignment(Pos.TOP_CENTER);

        Scene searchScene = new Scene(searchLayout);
        searchWindow.setTitle("Search");
        searchWindow.setScene(searchScene);
        searchWindow.setHeight(700);
        searchWindow.setWidth(800);
        searchWindow.setResizable(false);
        searchWindow.show();
    }


    public static void searchBook(HSQLDB user) {
        try {
            String inputData = searchBox.getText();
            if(inputData.length()>=3) {
                ResultSet bookQuery = user.query("SELECT * FROM MATERIAL WHERE ISBN LIKE '%" + inputData + "%'\n" + " OR NAME LIKE '%" + inputData + "%'\n" + " OR AUTHOR LIKE '%" + inputData + "%'");
                resultTable.setItems(getData(bookQuery));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Query data and then add to list
    public static ObservableList<Books> getData(ResultSet booksData) {
        //Initialize Books list
        ObservableList<Books> booksList = FXCollections.observableArrayList();

        try {
            while (booksData.next()) {

                //Get attributes
                String isbn = booksData.getString("isbn");
                String title = booksData.getString("name");
                String author = booksData.getString("author");

                Integer remain = booksData.getInt("remain");
                Integer material_id = booksData.getInt("material_id");

                //Add to list
                booksList.add(new Books(title, isbn, author, remain, material_id ,null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }


    public static void close(Stage searchWindow){
        searchWindow.close();
    }


    public static void reserveBook(TableView<Books> resultTable, HSQLDB user, Students currentStudent) {

        Integer[] indices = new Integer[resultTable.getSelectionModel().getSelectedIndices().size()];
        resultTable.getSelectionModel().getSelectedIndices().toArray(indices);
        for (Integer index : indices) {
            try {
                int bookID = resultTable.getItems().get(index).getMaterial_id();
                String MatrNr = currentStudent.getMatrNr();
                user.query("UPDATE MATERIAL SET REMAIN = REMAIN - 1 WHERE MATERIAL_ID = " + bookID);
                user.query("INSERT INTO BORROW VALUES (" + MatrNr + "," + bookID + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
