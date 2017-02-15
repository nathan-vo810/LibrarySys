package Interface;

import DB.hsqldb.HSQLDB;
import Models.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Material Searching Window
 */
public class MaterialSearch {

    private static Stage searchWindow = new Stage();
    private static TextField searchBox = new TextField();
    private static MaterialListView materialsTable = new MaterialListView();
    private static TableView<Material> resultTable = materialsTable.getMaterialsTable();

    public static void displayMaterialSearch(String username, HSQLDB user) {


        ResultSet Student_query = null;
        ResultSet Faculty_query = null;
        try {
            Student_query = user.query("SELECT * FROM STUDENT WHERE LID = '" + username + "'");
            Faculty_query = user.query("SELECT * FROM FACULTY WHERE LID = '" + username + "'");
        } catch (SQLException DatabaseConnectionException) {
            //No catching error found yet.
            Error.displayErrorInformScreen("Database error", "The connection is currently inaccessible or this student does not exist in the database", "Click close to try again.");
            DatabaseConnectionException.printStackTrace();
        }

        //Search Panel
        searchBox.setId("searchBox");
        searchBox.setPromptText("Search by Title, ISBN or Author");

        Button searchBtn = new Button();
        searchBtn.setText("Search");
        searchBtn.setDefaultButton(true);
        HBox searchPane = new HBox();
        searchPane.setId("searchPane");
        searchPane.getChildren().addAll(searchBox,searchBtn);

        searchBtn.setOnAction(e-> searchMaterial(user));

        Button reserveBtn = new Button();
        reserveBtn.setText("Reserve");

        Member currentMember = null;
        try {
            if (Student_query.next()) {
                Student_query.beforeFirst();
                currentMember = new Student(Student_query); //If user is a student
            }
            else if (Faculty_query.next()) {
                Faculty_query.beforeFirst();
                currentMember = new Faculty(Faculty_query); //If user is a faculty
            }
            else{
                currentMember = null; //If user is an admin
            }
        } catch (Exception CreateInstanceExcpetion) {
            //No catching error yet.
            Error.displayErrorInformScreen("Cannot create instance","Try again","Click close");
            CreateInstanceExcpetion.printStackTrace();
        }
        Member finalCurrentMember = currentMember;

        reserveBtn.setOnAction(e->{
            BorrowMaterialConfirmationBox.displayBorrowBookConfirmation(resultTable,user,finalCurrentMember);
        });

        GridPane searchLayout = new GridPane();
        searchLayout.setId("searchLayout");

        GridPane.setConstraints(searchPane,0,0);
        GridPane.setConstraints(resultTable,0,1);
        GridPane.setConstraints(reserveBtn,0,2);

        searchLayout.getChildren().addAll(searchPane,resultTable,reserveBtn);
        searchLayout.setAlignment(Pos.TOP_CENTER);

        Scene searchScene = new Scene(searchLayout);
        searchScene.getStylesheets().add(MaterialSearch.class.getResource("css/MaterialSearch.css").toExternalForm());
        searchWindow.setTitle("Search");
        searchWindow.setScene(searchScene);

        searchWindow.setResizable(false);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); //Appear base on the computer resolution
        searchWindow.setX(primaryScreenBounds.getMinX() + 150);
        searchWindow.setY(primaryScreenBounds.getMinY() + (primaryScreenBounds.getMaxY() - 700)/2);
        searchWindow.show();
    }

    //Search function
    public static void searchMaterial(HSQLDB user) {
        try {
            String inputData = searchBox.getText(); //get Text from search Textfield
            if(inputData.length()>=3) { //search for material
                ResultSet materialQuery = user.query("SELECT * FROM MATERIAL WHERE ISBN LIKE '%" + inputData + "%'\n" + " OR NAME LIKE '%" + inputData + "%'\n" + " OR AUTHOR LIKE '%" + inputData + "%'");
                resultTable.setItems(materialsTable.getData(materialQuery)); //add search data to display book search table
            }
        } catch (Exception DatabaseConnectionException) {
            //No catching error yet
            Error.displayErrorInformScreen("Database error", "The connection is currently inaccessible or the material is currently in a maintenance", "Click close to try again.");
            DatabaseConnectionException.printStackTrace();
        }
    }

}
