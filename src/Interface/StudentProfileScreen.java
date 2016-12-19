package Interface;


import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import DB.hsqldb.HSQLDB;
import com.sun.xml.internal.bind.v2.runtime.property.ValueProperty;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Date created:   30.11.2016
 *      Date modified:  02.12.2016
 *      Version:        1.0
 */

public class StudentProfileScreen {

    public static void displayStudentProfileScene(String usernameInput, String passwordInput) throws Exception {
        Scene Student_ProfileScene;
        //<editor-fold desc="HSQLDB Student Instance">
        HSQLDB student = new HSQLDB(usernameInput, passwordInput);
        ResultSet Student_query = student.query("SELECT * FROM STUDENT WHERE LID = '" + usernameInput + "'");
        String Sname = "";
        String Information = "";
        while (Student_query.next()) {
            Sname = Student_query.getString("FName");
            Information = "NAME: " + Student_query.getString("LName") + " " + Student_query.getString("FName") + "\n"
                    + "MAT: " + Student_query.getString("Matrnr") + "\n"
                    + "BIRTHDAY: " + Student_query.getString("DOB") + "\n"
                    + "PHONE: " + Student_query.getString("Phone");
        }
        //</editor-fold>
        Stage Student_ProfileWindow = new Stage();
        Student_ProfileWindow.setTitle(Sname);
        Student_ProfileWindow.setWidth(724);
        Student_ProfileWindow.setHeight(492);
        Student_ProfileWindow.setResizable(false);
        //<editor-fold desc="MainPane - SplitPane">
        SplitPane Student_ProfileLayout = new SplitPane();
        Student_ProfileLayout.setDividerPositions(0.2979797979797979796);
        Student_ProfileLayout.setLayoutX(-4.0);
        Student_ProfileLayout.setLayoutY(-5.0);
        Student_ProfileLayout.prefHeight(500.0);
        Student_ProfileLayout.prefHeight(732.0);
        //</editor-fold>
        //<editor-fold desc="LeftPane - AnchorPane: Student Profile Information">
        AnchorPane Leftside = new AnchorPane();
        Leftside.setMinWidth(214.0);
        Leftside.prefHeight(160.0);
        Leftside.setMaxWidth(214.0);
        //Profile Picture
        Image image = new Image(StudentProfileScreen.class.getResourceAsStream("sample.jpg"));
        ImageView imageView = new ImageView(image);
        final Circle clip = new Circle(60, 50, 50);
        imageView.setClip(clip);
        imageView.setId("Profile_picture");
        imageView.setFitHeight(100.0);
        imageView.setFitWidth(163.0);
        imageView.setLayoutX(47.0);
        imageView.setLayoutY(40.0);
        //Toggle Button and Button
        Button Signout = new Button("SIGN OUT");
        Signout.setId("Signout");
        ToggleButton Display_Profile = new ToggleButton();
        Display_Profile.setId("Toggle_button");
        Display_Profile.setText("");
        Display_Profile.setLayoutX(72.0);
        Display_Profile.setLayoutY(227.0);
        Display_Profile.setMaxWidth(111.0);
        Display_Profile.setMaxHeight(31.0);
        Signout.setLayoutX(66.0);
        Signout.setLayoutY(177.0);
        Signout.setText("SIGN OUT");
        //Label - Student Information
        Label Current_day = new Label();
        Current_day.setId("Today");
        Current_day.setLayoutX(46.0);
        Current_day.setLayoutY(279.0);
        Current_day.setMinHeight(39.0);
        Current_day.setMinWidth(168.0);
        Current_day.setTextAlignment(TextAlignment.CENTER);
        Label Student_Information = new Label();
        Student_Information.setId("Student_info");
        Student_Information.setLayoutX(45.0);
        Student_Information.setLayoutY(318.0);
        Student_Information.setMinHeight(39.0);
        Student_Information.setMinWidth(168.0);
        //Student_Information.setTextAlignment(TextAlignment.CENTER);
        Leftside.getChildren().addAll(imageView,Signout,Display_Profile,Current_day,Student_Information);
        //</editor-fold>
        //<editor-fold desc="Right Pane - AnchorPane: TableView of Borrowing Materials">
        AnchorPane Rightside = new AnchorPane();
        Rightside.setMinHeight(0.0);
        Rightside.setMinWidth(0.0);
        Rightside.prefHeight(160.0);
        Rightside.prefWidth(100.0);
        //Pane of the table.
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setLayoutX(11.0);
        scrollpane.setLayoutY(30.0);
        scrollpane.prefWidth(487.0);
        scrollpane.prefHeight(100.0);
        //The table
        AnchorPane table = new AnchorPane();
        table.minHeight(0.0);
        table.minWidth(0.0);
        table.prefHeight(400.0);
        table.prefWidth(469.0);
        TableColumn Material_ID = new TableColumn("Material ID");
        Material_ID.setId("Material_ID");
        Material_ID.setPrefWidth(111.0);
        TableColumn Material_name = new TableColumn("Title");
        Material_name.setId("Material_name");
        Material_name.setPrefWidth(249.0);
        TableColumn Duration = new TableColumn("Due Date");
        Duration.setId("Due_date");
        Duration.setPrefWidth(113.0);
        TableView BorrowingBooks = new TableView();
        BorrowingBooks.getColumns().addAll(Material_ID, Material_name, Duration);
        BorrowingBooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        BorrowingBooks.setLayoutX(-3.0);
        BorrowingBooks.setLayoutY(-3.0);
        BorrowingBooks.prefHeight(500.0);
        BorrowingBooks.prefWidth(474.0);
        //Adding from inside-out
        table.getChildren().addAll(BorrowingBooks);
        scrollpane.setContent(table);
        Rightside.getChildren().add(scrollpane);
        //</editor-fold>

        Student_ProfileLayout.getItems().addAll(Leftside,Rightside);

        Student_ProfileScene = new Scene(Student_ProfileLayout);
        Student_ProfileScene.getStylesheets().add(StudentProfileScreen.class.getResource("StudentProfile.css").toExternalForm());
        Student_ProfileWindow.setScene(Student_ProfileScene);;
        Student_ProfileWindow.show();

        //<editor-fold desc="Button set on action">
        Signout.setOnAction(event -> {
            try{
                student.shutdown();
            } catch (Exception e){
                e.printStackTrace();
            }
            Platform.exit();
        });

        String finalInformation = Information;
        Display_Profile.setOnAction(event -> {
            if(Student_Information.getText()==""){
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

        Student_ProfileWindow.setOnCloseRequest(event -> {
            Platform.exit();
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
        }, 0, 1000);
/*
        new Thread() {
            public void run() {
                //Do some stuff in another thread
                Platform.runLater(new Runnable() {
                    public void run() {
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
                        Date date = new Date();
                        Current_day.setText(date.toString());
                    }
                });
            }
        }.start();
*/
    }

}

