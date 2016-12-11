package Interface;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;

import DB.hsqldb.HSQLDB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Created by ngpbh on 12/11/2016.
 */
public class StudentProfileScreen {

    public static void displayStudentProfileScene(String usernameInput, String passwordInput) throws Exception {
        Scene Student_ProfileScene;
        Pane Student_ProfileLayout = (Pane) FXMLLoader.load(StudentProfileScreen.class.getResource("StudentProfileInterface.fxml"));
        Stage Student_ProfileWindow = new Stage();
        Student_ProfileScene = new Scene(Student_ProfileLayout);
        HSQLDB student = new HSQLDB(usernameInput,passwordInput);
        ResultSet Student_Information = student.query("SELECT * FROM STUDENT WHERE MATRNR = 1148181");//just for testing

        //Student_Information = student.query("SELECT * FROM STUDENT");
        //RegisterScene.getStylesheets().add(RegisterScreen.class.getResource("Register.css").toExternalForm());
        Student_ProfileWindow.setTitle(Student_Information.toString());
        Student_ProfileWindow.setScene(Student_ProfileScene);
        Student_ProfileWindow.setWidth(724);
        Student_ProfileWindow.setHeight(492);
        Student_ProfileWindow.setResizable(false);
        Student_ProfileWindow.show();

    }

}
