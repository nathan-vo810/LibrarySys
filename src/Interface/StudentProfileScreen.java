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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        Pane Student_ProfileLayout = (Pane) FXMLLoader.load(StudentProfileScreen.class.getResource("StudentProfileInterface.fxml"));
        Stage Student_ProfileWindow = new Stage();
        Student_ProfileScene = new Scene(Student_ProfileLayout);
        HSQLDB student = new HSQLDB(usernameInput,passwordInput);
        ResultSet Student_Information = student.query("SELECT FNAME FROM STUDENT WHERE MATRNR = 1148181");//just for testing
        //System.out.print(Student_Information.wasNull());
        //Student_Information = student.query("SELECT * FROM STUDENT");
        //RegisterScene.getStylesheets().add(RegisterScreen.class.getResource("Register.css").toExternalForm());

        String Sname ="";
        while (Student_Information.next()) {
            Sname = Student_Information.getString("FName");
        }
        Student_ProfileWindow.setTitle(Sname);
        Student_ProfileWindow.setScene(Student_ProfileScene);
        Student_ProfileWindow.setWidth(724);
        Student_ProfileWindow.setHeight(492);
        Student_ProfileWindow.setResizable(false);
        Student_ProfileWindow.show();

    }

}
