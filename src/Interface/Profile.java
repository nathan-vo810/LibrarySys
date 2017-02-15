package Interface;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import DB.hsqldb.HSQLDB;
import Models.Material;
import Models.Faculty;
import Models.Member;
import Models.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Display Profile Window
 */

public class Profile {

    private static Stage ProfileWindow = new Stage();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static TableView<Material> borrowedMaterials;

    //display window function
    public static void displayProfileScene(String usernameInput, String passwordInput, HSQLDB user) {
        //get all information from the user
        //<editor-fold desc="HSQLDB Instance">
        ResultSet Student_query = null;
        ResultSet Faculty_query = null;
        try {
            Student_query = user.query("SELECT * FROM STUDENT WHERE LID = '" + usernameInput + "'");
            Faculty_query = user.query("SELECT * FROM FACULTY WHERE LID = '" + usernameInput + "'");
        } catch (SQLException DatabaseConnectionException) {
            Error.displayErrorInformScreen("Cannot access to the library database", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
            DatabaseConnectionException.printStackTrace();
        }

        Member currentMember = null;
        try {
            if (Student_query.next()) {
                Student_query.beforeFirst();
                currentMember = new Student(Student_query);
            }
            else if (Faculty_query.next()) {
                Faculty_query.beforeFirst();
                currentMember = new Faculty(Faculty_query);
            }
        } catch (Exception DatabaseConnectionException) {
            Error.displayErrorInformScreen("Cannot create instance","Try again","Click close");
            DatabaseConnectionException.printStackTrace();
        }

        //Information Panel
        String Information = "";
        String UserID = "", name = "", DOB = "", Phone = "", subject = "", room = "", building = "";

        if (currentMember != null) {
            UserID = currentMember.getUserID();
            if (currentMember instanceof Student) {
                name = currentMember.getFName() + " " + currentMember.getLName();
                Phone = currentMember.getPhone();
                DOB = currentMember.getDOB();
                Information = "Name: " + name + "\nMatrNr: " + UserID + "\nBirthday: " + DOB + "\nPhone: " + Phone;
            } else {
                name = currentMember.getFName() + " " + currentMember.getLName();
                Phone = currentMember.getPhone();
                subject = currentMember.getSubject();
                room = currentMember.getRoom();
                building = currentMember.getBuilding();
                Information = "Name: " + name + "\nID: " + UserID + "\nSubject: " + subject + "\nRoom: " + building + "-" + room + "\nPhone: " + Phone;
            }
        }

        //</editor-fold>

        ProfileWindow.setTitle(name);
        ProfileWindow.setWidth(724);
        ProfileWindow.setHeight(492);
        ProfileWindow.setResizable(false);
        //<editor-fold desc="MainPane - SplitPane">
        SplitPane ProfileLayout = new SplitPane();
        ProfileLayout.setId("profileLayout");
        ProfileLayout.setDividerPositions(0.2979797979797979796);
        ProfileLayout.setLayoutX(-4.0);
        ProfileLayout.setLayoutY(-5.0);

        //</editor-fold>
        //<editor-fold desc="LeftPane - AnchorPane: Profile Information">
        AnchorPane leftPane = new AnchorPane();
        leftPane.setId("leftPane");

        //Profile Picture
        Image image = new Image(Profile.class.getResourceAsStream("img/sample.jpg"));
        ImageView imageView = new ImageView(image);
        final Circle clip = new Circle(60, 50, 50);
        imageView.setClip(clip);
        imageView.setId("Profile_picture");
        imageView.setFitHeight(100.0);
        imageView.setFitWidth(163.0);
        imageView.setLayoutX(50.0);
        imageView.setLayoutY(40.0);
        //Label - Student Information
        Label Current_day = new Label();
        Current_day.setId("Today");
        Current_day.setLayoutX(43.0);
        Current_day.setLayoutY(279.0);

        Label Student_Information = new Label();
        Student_Information.setId("Student_info");
        Student_Information.setLayoutX(45.0);
        Student_Information.setLayoutY(318.0);

        leftPane.getChildren().addAll(imageView,Current_day,Student_Information);
        //</editor-fold>
        //<editor-fold desc="Right Pane - AnchorPane: TableView of Borrowing Materials">
        AnchorPane rightPane = new AnchorPane();
        rightPane.setId("rightPane");
        rightPane.setLayoutY(15.0);
        rightPane.setLayoutX(11.0);

        //The table
        AnchorPane holder = new AnchorPane();
        holder.setId("table");
        holder.setLayoutX(11.0);
        holder.setLayoutY(15.0);

        Button openMaterialSearch = new Button("Searching Window");
        openMaterialSearch.setId("OpenBookSearch");
        openMaterialSearch.setLayoutY(405.0);
        openMaterialSearch.setLayoutX(22.0);
        openMaterialSearch.setOnAction(e -> MaterialSearch.displayMaterialSearch(usernameInput,user));

        Button returnMaterials = new Button("Return");
        returnMaterials.setId("ReturnBooks");
        returnMaterials.setLayoutY(405.0);
        returnMaterials.setLayoutX(390.0);

        Button renewMaterials = new Button("Renew");
        renewMaterials.setId("RenewBooks");
        renewMaterials.setLayoutX(310.0);
        renewMaterials.setLayoutY(405.0);

        borrowedMaterials = new TableView<>();
        TableColumn<Material, Integer> MIDColumn = new TableColumn<>("Material ID");
        TableColumn<Material, String> titleColumn = new TableColumn<>("Title");
        TableColumn<Material, String> duedateColumn = new TableColumn<>("Due Date");
        MIDColumn.setPrefWidth(111.0);
        titleColumn.setPrefWidth(249.0);
        duedateColumn.setPrefWidth(113.0);
        MIDColumn.setCellValueFactory(new PropertyValueFactory<>("material_id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        duedateColumn.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        borrowedMaterials.getColumns().addAll(MIDColumn,titleColumn,duedateColumn);
        borrowedMaterials.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        borrowedMaterials.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        borrowedMaterials.setEditable(true);
        borrowedMaterials.setLayoutX(-3.0);
        borrowedMaterials.setLayoutY(-3.0);
        borrowedMaterials.prefHeight(500.0);
        borrowedMaterials.prefWidth(474.0);

        try {
            displayBorrowed(user, currentMember);
        } catch (Exception DatabaseConnectionException) {
            //No catching error so far
            Error.displayErrorInformScreen("Cannot access to the library database", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
            DatabaseConnectionException.printStackTrace();
        }

        Member finalCurrentMember = currentMember;
        returnMaterials.setOnAction(event -> { //return some materials
            returnMaterials(user, finalCurrentMember);
            MaterialSearch.searchMaterial(user);
        });

        renewMaterials.setOnAction(event -> {
            renewMaterials(user, finalCurrentMember);
            try {
                displayBorrowed(user, finalCurrentMember); //renew some materials
            } catch (Exception DatabaseConnectionException) {
                //Error can be derived from the displayBorrowed()
                Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                DatabaseConnectionException.printStackTrace();
            }
        });

        //Adding from inside-out
        holder.getChildren().addAll(borrowedMaterials,openMaterialSearch, returnMaterials, renewMaterials);
        rightPane.getChildren().add(holder);
        //</editor-fold>
        ProfileLayout.getItems().addAll(leftPane,rightPane);

        Scene Student_ProfileScene = new Scene(ProfileLayout);
        Student_ProfileScene.getStylesheets().add(Profile.class.getResource("css/Profile.css").toExternalForm());
        ProfileWindow.setScene(Student_ProfileScene);;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds(); //set location for the window base on the notebook resolution
        ProfileWindow.setX(primaryScreenBounds.getMinX() + 150 + 500);
        ProfileWindow.setY(primaryScreenBounds.getMinY() + (primaryScreenBounds.getMaxY() - 700)/2);
        ProfileWindow.show();

        //<editor-fold desc="Button set on action">
        String finalInformation = Information;
        //Toggle Button and Button
        if(currentMember!=null) { //Only student and faculty have profile
            Button EditProfileBtn = new Button("Edit Profile");
            EditProfileBtn.setId("EditProfile");
            EditProfileBtn.setLayoutX(66.0);
            EditProfileBtn.setLayoutY(177.0);
            EditProfileBtn.setOnAction(event -> {
                try {
                    EditProfile.displayEditProfileScreen(usernameInput, passwordInput, user);
                } catch (SQLException PasswordConfirmation) {
                    Error.displayErrorInformScreen("Password Incorrect", "Your password is incorrect. The profile edition is unsuccessful", "Click close to try again.");
                    PasswordConfirmation.printStackTrace();
                }
            });

            ToggleButton Display_Profile = new ToggleButton();
            Display_Profile.setId("Toggle_button");
            Display_Profile.setText("");
            Display_Profile.setLayoutX(57.0);
            Display_Profile.setLayoutY(227.0);
            Display_Profile.setOnAction(event -> {
                if(Student_Information.getText()==""){ //Toggle button on-off display
                    Display_Profile.getStyleClass().remove("off");
                    Display_Profile.getStyleClass().add("on");
                    Student_Information.setText(finalInformation);
                }
                else{
                    Display_Profile.getStyleClass().remove("on");
                    Display_Profile.getStyleClass().add("off");
                    Student_Information.setText("");
                }
            });

            leftPane.getChildren().addAll(EditProfileBtn,Display_Profile);
        }

        ProfileWindow.setOnCloseRequest(event -> {
            try{
                ProfileWindow.close();
            } catch (Exception CannotCloseException){
                //No catching error so far
                Error.displayErrorInformScreen("Error", "The Profile Window cannot close.\n Please try again later.", "Click close to exit the program.");
                CannotCloseException.printStackTrace();
            }
        });
        //</editor-fold>

        Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
                        Date date = new Date();
                        Current_day.setText(dateFormat.format(date));
                    }
                });
            }
        }, 0, 1000); //The clock resets every 1 seconds make it is runnable.
    }

    //Return material function
    public static void returnMaterials(HSQLDB user, Member currentMember) {
        Integer[] integers = new Integer[borrowedMaterials.getSelectionModel().getSelectedIndices().size()]; //Run returning materials query as array
        borrowedMaterials.getSelectionModel().getSelectedIndices().toArray(integers);

        String UserID = "";
        if (currentMember == null) //current Member = admin
            UserID = "0000000";
        else UserID = currentMember.getUserID(); //current Member = student or faculty

        for(Integer integer : integers){ //Run returning materials query as array
            try {
                user.query("DELETE FROM BORROW WHERE MATERIAL_ID = " + borrowedMaterials.getItems().get(integer).getMaterial_id()
                        + " AND USERID = " + UserID); // delete borrowing material connection from user to that material
                user.query("UPDATE MATERIAL SET REMAIN = REMAIN + 1 WHERE MATERIAL_ID = " + borrowedMaterials.getItems().get(integer).getMaterial_id());
                //update the available quantity by plus one
            } catch (SQLException DatabaseConnectionException) {
                //Error can be
                //Cannot find the material id to update in the material table
                Error.displayErrorInformScreen("Database error", "Seems like there is a problem with the database connection", "Click close to exit program.");
                DatabaseConnectionException.printStackTrace();
            }
        }
        borrowedMaterials.getItems().removeAll(borrowedMaterials.getSelectionModel().getSelectedItems());
    }

    //Renew material function
    public static void renewMaterials(HSQLDB user, Member currentMember) {
        Integer[] integers = new Integer[borrowedMaterials.getSelectionModel().getSelectedIndices().size()]; //Run renewing materials query as array
        borrowedMaterials.getSelectionModel().getSelectedIndices().toArray(integers);
        for(Integer integer : integers){ //Run renewing materials query as array
            try {
                //update the borrow date in borrow table to today
                user.query("UPDATE BORROW SET BORROWDATE = '" + sdf.format(new Date()) + "' WHERE MATERIAL_ID = " + borrowedMaterials.getItems().get(integer).getMaterial_id());
            } catch (SQLException DatabaseConnectionException) {
                //No catching error found so far
                Error.displayErrorInformScreen("Database error", "Seems like there is a problem with the database connection", "Click close to exit program.");
                DatabaseConnectionException.printStackTrace();
            }
        }
    }

    //Display list of loaned materials
    public static void displayBorrowed(HSQLDB user, Member currentMember) throws Exception {
        String UserID = "";
        if (currentMember == null) UserID = "0000000"; //current Member = admin
        else UserID = currentMember.getUserID(); //current Member = student or faculty
        //Result Set list of book that user is currently borrowing
        ResultSet BorrowingQuery = user.query("SELECT * FROM MATERIAL JOIN BORROW ON (MATERIAL.MATERIAL_ID=BORROW.MATERIAL_ID) WHERE USERID = " + UserID);
        try {
            borrowedMaterials.setItems(getData(BorrowingQuery)); //Add the result set to the table view
        } catch (Exception DatabaseConnectionException) {
            //No catching error found so far
            //Error.displayErrorInformScreen("Database error", "Seems like there are some problems with the database connection", "Click close to exit program.");
            //DatabaseConnectionException.printStackTrace();
        }
    }

    //Query data
    public static ObservableList<Material> getData(ResultSet BorrowingQuery) {
        //Initialize Material list
        ObservableList<Material> materialList = FXCollections.observableArrayList();

        try {
            while (BorrowingQuery.next()) {

                //Get attributes
                Integer mid= BorrowingQuery.getInt("material_id");
                String title = BorrowingQuery.getString("name");

                //Calculating due date
                String duration = BorrowingQuery.getString("borrow_duration");
                String borrowDate = BorrowingQuery.getString("borrowdate");
                String duedate = "";

                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(borrowDate));
                    c.add(Calendar.DATE, Integer.parseInt(duration));  // number of days to add
                    duedate = sdf.format(c.getTime());  // dt is now the new date
                } catch (ParseException DatabaseConnectionException) {
                    Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\nPlease try again later.", "Click close to exit the program.");
                    DatabaseConnectionException.printStackTrace();
                }

                //Add to list
                materialList.add(new Material(title, null, null, null, mid, duedate));
            }
        } catch (SQLException DatabaseConnectionException) {
            Error.displayErrorInformScreen("Database error", "Seems like there are some problems with the database connection", "Click close to exit program.");
            DatabaseConnectionException.printStackTrace();
        }

        return materialList;

    }

}

