package Interface;

import DB.hsqldb.HSQLDB;
import Models.Books;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

import java.sql.ResultSet;

/**
 * Created by NhatAnh on 12/11/16.
 */
public class BookSearch {

    public static void displayBookSearch() {

        //Search Panel
        TextField searchBox = new TextField();
        searchBox.setMinWidth(650);

        Button searchBtn = new Button();
        searchBtn.setText("Search");
        searchBtn.setOnAction(e->searchBook(searchBox.getText()));

        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(10,10,10,10));
        searchPane.setSpacing(10);
        searchPane.getChildren().addAll(searchBox,searchBtn);

        Label note = new Label("Search by Title, ISBN or Author");

        //Result table
        TableView<Books> resultTable= new TableView<>();
        resultTable.setMinHeight(500);

        TableColumn<Books, String> isbn = new TableColumn<>("ISBN");
        TableColumn<Books, String> title = new TableColumn<>("Title");
        TableColumn<Books, String> author = new TableColumn<>("Author");
        TableColumn<Books, Integer> remain = new TableColumn<>("Remain");

        resultTable.getColumns().addAll(isbn,title,author,remain);
        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        GridPane searchLayout = new GridPane();
        searchLayout.setPadding(new Insets(10, 10, 10, 10));
        searchLayout.setVgap(10);
        searchLayout.setHgap(8);

        GridPane.setConstraints(searchPane,0,0);
        GridPane.setConstraints(note,0,1);
        GridPane.setConstraints(resultTable,0,2);
        //searchLayout.setHalignment(searchPane,HPos.CENTER);
        searchLayout.setHalignment(note,HPos.CENTER);
        //searchLayout.setHalignment(resultTable,HPos.CENTER);


        searchLayout.getChildren().addAll(searchPane,note,resultTable);
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

    public static void searchBook(String inputData) {
        //ResultSet bookQuery = database.query("SELECT * FROM WHERE ");
    }
}
