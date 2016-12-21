package Interface;


import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import DB.hsqldb.HSQLDB;
import Models.Books;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.xml.internal.bind.v2.runtime.property.ValueProperty;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public static void displayStudentProfileScene(String usernameInput, String passwordInput, HSQLDB user, BookSearch bookSearch) throws Exception {
        Scene Student_ProfileScene;
        //<editor-fold desc="HSQLDB Student Instance">
        ResultSet Student_query = user.query("SELECT * FROM STUDENT WHERE LID = '" + usernameInput + "'");
        String Matrnr = "";
        String Sname = "";
        String Information = "";
        while (Student_query.next()) {
            Sname = Student_query.getString("FName");
            Matrnr = Student_query.getString("Matrnr");
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
        imageView.setLayoutX(50.0);
        imageView.setLayoutY(40.0);
        //Toggle Button and Button
        Button Signout = new Button("SIGN OUT");
        Signout.setId("Signout");
        ToggleButton Display_Profile = new ToggleButton();
        Display_Profile.setId("Toggle_button");
        Display_Profile.setText("");
        Display_Profile.setLayoutX(70.0);
        Display_Profile.setLayoutY(227.0);
        Display_Profile.setMaxWidth(111.0);
        Display_Profile.setMaxHeight(31.0);
        Signout.setLayoutX(66.0);
        Signout.setLayoutY(177.0);
        Signout.setText("SIGN OUT");
        //Label - Student Information
        Label Current_day = new Label();
        Current_day.setId("Today");
        Current_day.setLayoutX(43.0);
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

        //The table
        AnchorPane table = new AnchorPane();
        table.setLayoutX(11.0);
        table.setLayoutY(15.0);
        table.minHeight(0.0);
        table.minWidth(0.0);
        table.prefHeight(400.0);
        table.prefWidth(469.0);
        Button OpenBookSearch = new Button("Searching Window");
        OpenBookSearch.setId("OpenBookSearch");
        OpenBookSearch.setLayoutY(405.0);
        OpenBookSearch.setLayoutX(22.0);
        OpenBookSearch.setOnAction(event -> {
            //if(BookSearch)
        });
        Button ReturnBooks = new Button("Return");
        ReturnBooks.setId("ReturnBooks");
        ReturnBooks.setLayoutY(405.0);
        ReturnBooks.setLayoutX(380.0);
        TableView<Books> BorrowingBooks = new TableView<>();
        TableColumn<Books, Integer> MIDColumn = new TableColumn<>("Material ID");
        TableColumn<Books, String> titleColumn = new TableColumn<>("Title");
        TableColumn<Books, String> duedateColumn = new TableColumn<>("Due Date");
        MIDColumn.setPrefWidth(111.0);
        titleColumn.setPrefWidth(249.0);
        duedateColumn.setPrefWidth(113.0);
        MIDColumn.setCellValueFactory(new PropertyValueFactory<>("material_id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        duedateColumn.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        BorrowingBooks.getColumns().addAll(MIDColumn,titleColumn,duedateColumn);
        BorrowingBooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        BorrowingBooks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        BorrowingBooks.setEditable(true);
        BorrowingBooks.setLayoutX(-3.0);
        BorrowingBooks.setLayoutY(-3.0);
        BorrowingBooks.prefHeight(500.0);
        BorrowingBooks.prefWidth(474.0);

        ResultSet BorrowingQuery = user.query("SELECT * FROM MATERIAL JOIN BORROW ON (MATERIAL.MATERIAL_ID=BORROW.MATERIAL_ID) WHERE MATRNR = " + Matrnr);
        try {
            BorrowingBooks.setItems(getData(BorrowingQuery));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String finalMatrnr = Matrnr;
        ReturnBooks.setOnAction(event -> {
            Integer[] integers = new Integer[BorrowingBooks.getSelectionModel().getSelectedIndices().size()];
            BorrowingBooks.getSelectionModel().getSelectedIndices().toArray(integers);
            for(Integer integer : integers){
                try {
                    user.query("UPDATE MATERIAL SET REMAIN = REMAIN + 1 WHERE MATERIAL_ID = " + BorrowingBooks.getItems().get(integer).getMaterial_id());
                    user.query("DELETE FROM BORROW WHERE MATERIAL_ID = " + BorrowingBooks.getItems().get(integer).getMaterial_id()
                    + " AND MATRNR = " + finalMatrnr);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            BorrowingBooks.getItems().removeAll(BorrowingBooks.getSelectionModel().getSelectedItems());
        });

            //Adding from inside-out
        table.getChildren().addAll(BorrowingBooks,OpenBookSearch, ReturnBooks);
        Rightside.getChildren().add(table);
        //</editor-fold>
        Student_ProfileLayout.getItems().addAll(Leftside,Rightside);
        Student_ProfileScene = new Scene(Student_ProfileLayout);
        Student_ProfileScene.getStylesheets().add(StudentProfileScreen.class.getResource("StudentProfile.css").toExternalForm());
        Student_ProfileWindow.setScene(Student_ProfileScene);;
        Student_ProfileWindow.show();
        //<editor-fold desc="Button set on action">
        Signout.setOnAction(event -> {
            try{
                user.shutdown();
                Platform.exit();
            } catch (Exception e){
                e.printStackTrace();
            }
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
            try{
                user.shutdown();
            } catch (Exception e){
                e.printStackTrace();
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
        }, 0, 1000);

    }

    public static ObservableList<Books> getData(ResultSet BorrowingQuery) {
        //Initialize Books list
        ObservableList<Books> booksList = FXCollections.observableArrayList();

        try {
            while (BorrowingQuery.next()) {

                //Get attributes
                Integer mid= BorrowingQuery.getInt("material_id");
                String title = BorrowingQuery.getString("name");
                String duedate = BorrowingQuery.getString("borrow_duration");

                //Add to list
                booksList.add(new Books(title, null, null, null, mid, duedate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;

    }
}

