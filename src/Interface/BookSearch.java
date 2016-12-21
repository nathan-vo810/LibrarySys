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

import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NhatAnh on 12/11/16.
 */
public class BookSearch {

    public static void displayBookSearch(String username, String password, HSQLDB user) throws Exception {

        ResultSet studentInfo = user.query("SELECT * FROM STUDENT WHERE LID = '" + username + "'");
        Students currentStudent = new Students(studentInfo);

        //Search Panel
        TextField searchBox = new TextField();
        searchBox.setMinWidth(650);

        Button searchBtn = new Button();
        searchBtn.setText("Search");

        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(10,10,10,10));
        searchPane.setSpacing(10);
        searchPane.getChildren().addAll(searchBox,searchBtn);

        Label note = new Label("Search by Title, ISBN or Author");

        //Result table
        TableView<Books> resultTable = new TableView<>();
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

        searchBtn.setOnAction(e->searchBook(searchBox, username, password, resultTable, user));

        Button reserveBtn = new Button();
        reserveBtn.setText("Reserve");

        reserveBtn.setOnAction(e->reserveBook(resultTable,user,currentStudent));

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
        Stage searchWindow = new Stage();
        searchWindow.setTitle("Search");
        searchWindow.setScene(searchScene);
        searchWindow.setHeight(700);
        searchWindow.setWidth(800);
        searchWindow.setResizable(false);
        searchWindow.show();
    }

    public static void searchBook(TextField searchBox, String username, String password, TableView<Books> resultTable, HSQLDB user) {
        try {
            String inputData = searchBox.getText();
            ResultSet bookQuery = user.query("SELECT * FROM MATERIAL WHERE ISBN = '" + inputData +"'\n" +" OR NAME = '" + inputData +"'\n" +" OR AUTHOR = '" + inputData + "'");
            resultTable.setItems(getData(bookQuery));
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


    //public static void reserveBook(TableView<Books> resultTable) {}


    public static void reserveBook(TableView<Books> resultTable, HSQLDB user, Students currentStudent) {

        Integer[] indices = new Integer[resultTable.getSelectionModel().getSelectedIndices().size()];
        resultTable.getSelectionModel().getSelectedIndices().toArray(indices);
        for(Integer index : indices){
            try {
                int bookID = resultTable.getItems().get(index).getMaterial_id();
                String MatrNr = currentStudent.getMatrNr();
                user.query("UPDATE MATERIAL SET REMAIN = REMAIN -1 WHERE MATERIAL_ID = " + bookID);
                user.query("INSERT INTO BORROW VALUES (" + MatrNr + "," + bookID +")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    /*
    ObservableList<Books> list = resultTable.getSelectionModel().getSelectedItems();
    for (Books single : list) {
        System.out.print(single);
    }
    */
    }

}
