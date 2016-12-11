package Interface;

import DB.hsqldb.HSQLDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
/**
 * Created by NhatAnh on 12/11/16.
 */
public class BookSearch {

    public static void displayBookSearch() {

        TextField searchBox = new TextField();
        searchBox.setPromptText("Name/ISBN/Author");

        Button searchBtn = new Button();
        searchBtn.setText("Search");

        HBox searchPane = new HBox();
        searchPane.setPadding(new Insets(3,3,3,3));
        searchPane.getChildren().addAll(searchBox,searchBtn);

        GridPane searchLayout = new GridPane();
        searchLayout.setPadding(new Insets(10, 10, 10, 10));
        searchLayout.setVgap(10);
        searchLayout.setHgap(8);

        GridPane.setConstraints(searchPane,0,0);

        searchLayout.getChildren().addAll(searchPane);

        Scene searchScene = new Scene(searchLayout);
        Stage searchWindow = new Stage();
        searchWindow.setTitle("Search");
        searchWindow.setScene(searchScene);
        searchWindow.setHeight(500);
        searchWindow.setWidth(500);
        searchWindow.setResizable(false);
        searchWindow.show();

    }
}
