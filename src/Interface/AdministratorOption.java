package Interface;


import DB.hsqldb.HSQLDB;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Administrator Option Window
 */
public class AdministratorOption {

    private static Stage AdminOptionWindow = new Stage();
    private static Scene AdminOptionScene;
    private static SplitPane scenepane;
    private static AnchorPane leftpane;
    private static AnchorPane rightpane;
    private static Button ManageBorrowedMaterialsBtn = new Button("Managing\nBorrowing\nMaterial");

    //display window function
    public static void displayAdministratorOptionScreen(HSQLDB user){

        //Buttons Declaration
        Button AddMaterialsBtn = new Button("Adding\nMaterial");
        AddMaterialsBtn.setId("addbooks");
        AddMaterialsBtn.setLayoutX(20.0); AddMaterialsBtn.setLayoutY(5.0);

        Button DeleteMaterialsBtn = new Button("Deleting\nMaterial");
        DeleteMaterialsBtn.setId("deletebooks");
        DeleteMaterialsBtn.setLayoutX(20.0); DeleteMaterialsBtn.setLayoutY(5.0);

        Button AddMembersBtn = new Button("Adding\nMembers");
        AddMembersBtn.setId("addmembers");
        AddMembersBtn.setLayoutX(20.0); AddMembersBtn.setLayoutY(5.0);

        Button DeleteMembersBtn = new Button("Deleting\nMembers");
        DeleteMembersBtn.setId("deletemembers");
        DeleteMembersBtn.setLayoutX(20.0); DeleteMembersBtn.setLayoutY(5.0);

        ManageBorrowedMaterialsBtn.setId("manageborrowingbooks");
        ManageBorrowedMaterialsBtn.setLayoutX(20.0); ManageBorrowedMaterialsBtn.setLayoutY(5.0);

        //Add button to left pane
        VBox optionBox = new VBox();
        optionBox.getChildren().addAll(AddMaterialsBtn, DeleteMaterialsBtn, AddMembersBtn, DeleteMembersBtn, ManageBorrowedMaterialsBtn);
        optionBox.setId("optionbox");
        leftpane = new AnchorPane(optionBox);
        leftpane.setPrefHeight(160.0);
        leftpane.setPrefWidth(100.0);

        //Empty rightPane
        rightpane = new AnchorPane();
        scenepane = new SplitPane(leftpane,rightpane);
        scenepane.setDividerPositions(0.27);
        scenepane.setPrefHeight(400.0);
        scenepane.setPrefWidth(680.0);

        //Button behaviors
        AddMaterialsBtn.setOnAction(event -> {
            AddMembersBtn.getStyleClass().removeAll("choose"); //remove style class
            DeleteMembersBtn.getStyleClass().removeAll("choose"); //remove style class
            AddMaterialsBtn.getStyleClass().add("choose"); //add style class to chosing button
            DeleteMaterialsBtn.getStyleClass().removeAll("choose"); //remove style class
            ManageBorrowedMaterialsBtn.getStyleClass().removeAll("choose"); //remove style class

            addMaterial(user); //pass user to addMaterial function
        });

        DeleteMaterialsBtn.setOnAction(event -> {
            AddMembersBtn.getStyleClass().removeAll("choose");
            DeleteMembersBtn.getStyleClass().removeAll("choose");
            AddMaterialsBtn.getStyleClass().removeAll("choose");
            DeleteMaterialsBtn.getStyleClass().add("choose");
            ManageBorrowedMaterialsBtn.getStyleClass().removeAll("choose");

            delMaterial(user);
        });

        AddMembersBtn.setOnAction(event -> {
            AddMembersBtn.getStyleClass().add("choose");
            DeleteMembersBtn.getStyleClass().removeAll("choose");
            AddMaterialsBtn.getStyleClass().removeAll("choose");
            DeleteMaterialsBtn.getStyleClass().removeAll("choose");
            ManageBorrowedMaterialsBtn.getStyleClass().removeAll("choose");

            addMem(user);

        });

        DeleteMembersBtn.setOnAction(event -> {
            AddMembersBtn.getStyleClass().removeAll("choose");
            DeleteMembersBtn.getStyleClass().add("choose");
            AddMaterialsBtn.getStyleClass().removeAll("choose");
            DeleteMaterialsBtn.getStyleClass().removeAll("choose");
            ManageBorrowedMaterialsBtn.getStyleClass().removeAll("choose");

            delMem(user);
        });

        ManageBorrowedMaterialsBtn.setOnAction(event -> {
            AddMembersBtn.getStyleClass().removeAll("choose");
            DeleteMembersBtn.getStyleClass().removeAll("choose");
            AddMaterialsBtn.getStyleClass().removeAll("choose");
            DeleteMaterialsBtn.getStyleClass().removeAll("choose");
            ManageBorrowedMaterialsBtn.getStyleClass().add("choose");

            manageMaterials(user);
        });

        AdminOptionScene = new Scene(scenepane);
        AdminOptionWindow.setResizable(false);
        AdminOptionScene.getStylesheets().add(AdministratorOption.class.getResource("css/AdminOption.css").toExternalForm());
        AdminOptionWindow.setScene(AdminOptionScene);
        AdminOptionWindow.setTitle("Library Managing Settings");
        AdminOptionWindow.show();
    }
    //adding material
    public static void addMaterial(HSQLDB user) {
        //Adding new Material
        TextField Titlefield = new TextField();
        Titlefield.setPromptText("Material title");
        Titlefield.setLayoutX(27.0); Titlefield.setLayoutY(55); Titlefield.setPrefHeight(31); Titlefield.setPrefWidth(347);

        TextField Material_Typefield = new TextField();
        Material_Typefield.setPromptText("Material type");
        Material_Typefield.setLayoutX(27.0); Material_Typefield.setLayoutY(96); Material_Typefield.setPrefHeight(31); Material_Typefield.setPrefWidth(347);

        TextField ISBNfield = new TextField();
        ISBNfield.setPromptText("ISBN");
        ISBNfield.setLayoutX(27.0); ISBNfield.setLayoutY(137); ISBNfield.setPrefHeight(31); ISBNfield.setPrefWidth(347);

        TextField MaterialIDfield = new TextField();
        MaterialIDfield.setPromptText("Material ID");
        MaterialIDfield.setLayoutX(27.0); MaterialIDfield.setLayoutY(178); MaterialIDfield.setPrefHeight(31); MaterialIDfield.setPrefWidth(347);

        TextField Authorfield = new TextField();
        Authorfield.setPromptText("Author");
        Authorfield.setLayoutX(27.0); Authorfield.setLayoutY(219); Authorfield.setPrefHeight(31); Authorfield.setPrefWidth(347);

        TextField Borrow_Durationfield = new TextField();
        Borrow_Durationfield.setPromptText("Borrow Duration");
        Borrow_Durationfield.setLayoutX(27.0); Borrow_Durationfield.setLayoutY(260); Borrow_Durationfield.setPrefHeight(31); Borrow_Durationfield.setPrefWidth(347);

        TextField Publisherfield = new TextField();
        Publisherfield.setPromptText("Publisher company");
        Publisherfield.setLayoutX(27.0); Publisherfield.setLayoutY(301); Publisherfield.setPrefHeight(31); Publisherfield.setPrefWidth(347);

        TextField Remainfield = new TextField();
        Remainfield.setPromptText("Quantity");
        Remainfield.setLayoutX(27.0); Remainfield.setLayoutY(342); Remainfield.setPrefHeight(31); Remainfield.setPrefWidth(347);

        Button addmaterialBtn = new Button("Add material");
        addmaterialBtn.setLayoutX(309.0); addmaterialBtn.setLayoutY(383);

        AnchorPane addBookPane = new AnchorPane();
        addBookPane.getChildren().addAll(Titlefield,Material_Typefield,ISBNfield,MaterialIDfield,Authorfield,Borrow_Durationfield,Publisherfield,Remainfield,addmaterialBtn);

        addmaterialBtn.setOnAction(event -> {
            try {
                user.query("INSERT INTO MATERIAL VALUES ('" + Titlefield.getText() + "','"
                            + Material_Typefield.getText() + "',"
                            + ISBNfield.getText() + ","
                            + MaterialIDfield.getText() + ",'"
                            + Authorfield.getText() + "',"
                            + Borrow_Durationfield.getText() + ",'"
                            + Publisherfield.getText() + "',"
                            + Remainfield.getText() + ")");
                SuccessNotifierScreen.displaySuccessInformScreen("Information","Operation Successful");
            } catch (SQLException DatabaseConnectionException) {
                //Error can be:
                //1. The Material ID is already exist. Cannot insert the same Material ID because it is primary key.
                //2. The borrow duration or quantity is not fulfilled since they are not null value.
                Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                DatabaseConnectionException.printStackTrace();
            }
        });

        scenepane.getItems().removeAll(leftpane,rightpane);
        scenepane.setDividerPositions(0.27);
        rightpane = addBookPane;
        scenepane.getItems().addAll(leftpane,rightpane);
    }
    //deleting material
    public static void delMaterial(HSQLDB user) {
        //Delete Book
        Label description = new Label("Enter the Material ID:");
        description.setLayoutX(27.0); description.setLayoutY(137); description.setPrefHeight(31); description.setPrefWidth(347);

        TextField MaterialIDfield = new TextField();
        MaterialIDfield.setPromptText("Material ID");
        MaterialIDfield.setLayoutX(27.0); MaterialIDfield.setLayoutY(178); MaterialIDfield.setPrefHeight(31); MaterialIDfield.setPrefWidth(347);

        Button deletematerialBtn = new Button("Delete material");
        deletematerialBtn.setLayoutX(309.0); deletematerialBtn.setLayoutY(219);

        AnchorPane delBookPane = new AnchorPane();
        delBookPane.getChildren().addAll(description,MaterialIDfield,deletematerialBtn);

        deletematerialBtn.setOnAction(event -> {
            try {
                ResultSet DeletingCheck = user.query("SELECT * FROM BORROW WHERE MATERIAL_ID = " + MaterialIDfield.getText());
                if(!DeletingCheck.next()) {
                    user.query("DELETE FROM MATERIAL WHERE MATERIAL_ID = " + MaterialIDfield.getText());
                    SuccessNotifierScreen.displaySuccessInformScreen("Information", "Operation Successful");
                }
                else{
                    Error.displayErrorInformScreen("Error", "Material is currently borrowed.\n Cannot executing delete.", "Click close to try again.");
                }
            } catch (SQLException DatabaseConnectionException) {
                //Not catching this exception so far
                Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                DatabaseConnectionException.printStackTrace();
            }
        });

        scenepane.getItems().removeAll(leftpane,rightpane);
        scenepane.setDividerPositions(0.27);
        rightpane = delBookPane;
        scenepane.getItems().addAll(leftpane,rightpane);

    }
    //adding member
    public static void addMem(HSQLDB user) {
        //Adding Members
        TextField LIDfield = new TextField();
        LIDfield.setPromptText("Library ID");
        LIDfield.setLayoutX(27.0); LIDfield.setLayoutY(55); LIDfield.setPrefHeight(31); LIDfield.setPrefWidth(347);

        TextField FNamefield = new TextField();
        FNamefield.setPromptText("First Name");
        FNamefield.setLayoutX(27.0); FNamefield.setLayoutY(96); FNamefield.setPrefHeight(31); FNamefield.setPrefWidth(100);

        TextField LNamefield = new TextField();
        LNamefield.setPromptText("Last Name");
        LNamefield.setLayoutX(130.0); LNamefield.setLayoutY(96); LNamefield.setPrefHeight(31); LNamefield.setPrefWidth(244);

        TextField PIDfield = new TextField();
        PIDfield.setPromptText("Faculty ID");
        PIDfield.setLayoutX(27.0); PIDfield.setLayoutY(137); PIDfield.setPrefHeight(31); PIDfield.setPrefWidth(347);

        TextField TeachingSubjectfield = new TextField();
        TeachingSubjectfield.setPromptText("Teaching Subject");
        TeachingSubjectfield.setLayoutX(27.0); TeachingSubjectfield.setLayoutY(178); TeachingSubjectfield.setPrefHeight(31); TeachingSubjectfield.setPrefWidth(347);

        TextField Roomfield = new TextField();
        Roomfield.setPromptText("Office number");
        Roomfield.setLayoutX(27.0); Roomfield.setLayoutY(219); Roomfield.setPrefHeight(31); Roomfield.setPrefWidth(347);

        TextField Buildingfield = new TextField();
        Buildingfield.setPromptText("Building number");
        Buildingfield.setLayoutX(27.0); Buildingfield.setLayoutY(260); Buildingfield.setPrefHeight(31); Buildingfield.setPrefWidth(347);

        TextField Phonefield = new TextField();
        Phonefield.setPromptText("Phone number");
        Phonefield.setLayoutX(27.0); Phonefield.setLayoutY(301); Phonefield.setPrefHeight(31); Phonefield.setPrefWidth(347);

        TextField Addressfield = new TextField();
        Addressfield.setPromptText("Address field");
        Addressfield.setLayoutX(27.0); Addressfield.setLayoutY(342); Addressfield.setPrefHeight(31); Addressfield.setPrefWidth(347);

        Button addmemberBtn = new Button("Add member");
        addmemberBtn.setLayoutX(309.0); addmemberBtn.setLayoutY(383);

        AnchorPane addMemberPane = new AnchorPane();
        addMemberPane.getChildren().addAll(LIDfield,FNamefield,LNamefield,PIDfield,TeachingSubjectfield,Roomfield,Buildingfield,Phonefield,Addressfield,addmemberBtn);

        addmemberBtn.setOnAction(event -> {
            try {
                if(Integer.parseInt(PIDfield.getText())!=0) {
                    user.query("INSERT INTO FACULTY VALUES ('"
                            + LNamefield.getText() + "','"
                            + FNamefield.getText() + "',"
                            + PIDfield.getText() + ",'"
                            + LIDfield.getText() + "','"
                            + TeachingSubjectfield.getText() + "',"
                            + Roomfield.getText() + ","
                            + Buildingfield.getText() + ",'"
                            + Phonefield.getText() + "','"
                            + Addressfield.getText() + "')");
                    user.query("CREATE USER " + LIDfield.getText() + " PASSWORD \"" + CryptWithMD5.cryptWithMD5(TeachingSubjectfield.getText() + Roomfield.getText() + Buildingfield.getText()) + "\"");
                    user.query("GRANT FACULTYROLE TO " + LIDfield.getText());
                    SuccessNotifierScreen.displaySuccessInformScreen("Information", "Operation Successful");
                }
                else Error.displayErrorInformScreen("Error", "Please enter correct PID.", "Click close to try again.");
            } catch (SQLException DatabaseConnectionException) {
                //Error can be
                //Same primary key typed in.
                Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                DatabaseConnectionException.printStackTrace();
            }
        });

        scenepane.getItems().removeAll(leftpane,rightpane);
        scenepane.setDividerPositions(0.27);
        rightpane = new AnchorPane(addMemberPane);
        scenepane.getItems().addAll(leftpane,rightpane);
    }
    //deleting member
    public static void delMem(HSQLDB user) {

        //Delete Member (Student or Faculty)
        Label description = new Label("Enter Library ID:");
        description.setLayoutX(27.0); description.setLayoutY(137); description.setPrefHeight(31); description.setPrefWidth(347);

        TextField LIDfield_fordelete = new TextField();
        LIDfield_fordelete.setPromptText("Library ID/Matriculation number"); //only need to enter the primary key.
        LIDfield_fordelete.setLayoutX(27.0); LIDfield_fordelete.setLayoutY(178); LIDfield_fordelete.setPrefHeight(31); LIDfield_fordelete.setPrefWidth(347);

        Button deletememberBtn = new Button("Delete member");
        deletememberBtn.setLayoutX(309.0); deletememberBtn.setLayoutY(219);

        AnchorPane delMemPane = new AnchorPane();
        delMemPane.getChildren().addAll(description,LIDfield_fordelete,deletememberBtn);

        deletememberBtn.setOnAction(event -> {
            try {
                ResultSet DeletingCheck = user.query("SELECT * FROM BORROW WHERE USERID = " + LIDfield_fordelete.getText());
                if(!DeletingCheck.next()) {
                    ResultSet DeleteStudentInfo = user.query("SELECT * FROM STUDENT WHERE MATRNR = " + LIDfield_fordelete.getText());
                    ResultSet DeleteFacultyInfo = user.query("SELECT * FROM FACULTY WHERE PID = " + LIDfield_fordelete.getText());
                    user.query("DELETE FROM FACULTY WHERE PID = " + LIDfield_fordelete.getText());
                    user.query("DELETE FROM STUDENT WHERE MATRNR = " + LIDfield_fordelete.getText());
                    if(DeleteStudentInfo.next()) user.query("DROP USER " + DeleteStudentInfo.getString("LID"));
                    else if(DeleteFacultyInfo.next()) user.query("DROP USER " + DeleteFacultyInfo.getString("LID"));
                    SuccessNotifierScreen.displaySuccessInformScreen("Information", "Operation Successful");
                }
                else{
                    Error.displayErrorInformScreen("Error", "This person is currently borrowing some materials.\nCannot deleting at the moment.", "Click close to try again.");
                }
            } catch (SQLException DatabaseConnectionException) {
                // So far we have not catched this Exception yet.
                Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\nPlease try again later.", "Click close to exit the program.");
                DatabaseConnectionException.printStackTrace();
            }
        });

        scenepane.getItems().removeAll(leftpane,rightpane);
        scenepane.setDividerPositions(0.27);
        rightpane = delMemPane;
        scenepane.getItems().addAll(leftpane,rightpane);
    }
    //managing borrowing materials
    public static void manageMaterials(HSQLDB user) {
        scenepane.getItems().removeAll(leftpane,rightpane);
        scenepane.setDividerPositions(0.27);
        rightpane = MembersMaterials.displayMembersMaterials(user);
        scenepane.getItems().addAll(leftpane,rightpane);
    }
}
