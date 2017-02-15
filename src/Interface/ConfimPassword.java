package Interface;

import DB.hsqldb.HSQLDB;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.PrivateKey;
import java.sql.ResultSet;
import java.sql.SQLException;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Confirm Password Window
 */
public class ConfimPassword {
    public static void displayConfirmPasswordScreen(String usernameInput, String passwordInput, HSQLDB user, String newPassword, String newPhone, String newAddress){
        Label label = new Label("Please re-enter your current password: ");
        PasswordField passwordField = new PasswordField();
        Button okBtn = new Button("Confirm");
        VBox mainPane = new VBox();
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setSpacing(10);
        mainPane.getChildren().addAll(label,passwordField,okBtn);
        Scene scene = new Scene(mainPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Verification Step");
        stage.getIcons().add(new Image( ConfimPassword.class.getResourceAsStream( "img/verification.jpg" )));
        stage.show();
        okBtn.setOnAction(event -> {
            boolean checkException = false;
            if(passwordField.getText().compareTo(passwordInput)==0){
                try {
                    ResultSet Student_query = user.query("SELECT * FROM STUDENT WHERE LID = '" + usernameInput + "'");
                    ResultSet Faculty_query = user.query("SELECT * FROM FACULTY WHERE LID = '" + usernameInput + "'");
                    HSQLDB admin = new HSQLDB("SA", "admin@vgu");
                    if (Student_query.next()) {
                        admin.query("UPDATE STUDENT SET PHONE = '" + newPhone + "'" + ", ADDRESS = '" + newAddress + "'");
                    } else if (Faculty_query.next()){
                        admin.query("UPDATE FACULTY SET PHONE = '" + newPhone + "'" + ", ADDRESS = '" + newAddress + "'");
                    }
                    admin.shutdown();
                } catch (SQLException DatabaseConnectionException) {
                    //No catching error so far
                    Error.displayErrorInformScreen("Cannot access to the library database", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                    DatabaseConnectionException.printStackTrace();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                if(newPassword!=null&&newPassword.length() >= 6 && newPassword.matches(".*\\d.*")){
                    try {
                        HSQLDB admin = new HSQLDB("SA", "admin@vgu");
                        admin.query("ALTER USER " + usernameInput + " SET PASSWORD " + "\"" + CryptWithMD5.cryptWithMD5(newPassword) + "\"");
                        admin.shutdown();
                        Platform.exit();
                        System.exit(0);
                    } catch (SQLException DatabaseConnectionException) {
                        // Error can be
                        // Alter password of another administrator. Permission Denied.
                        checkException = true;
                        Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                        DatabaseConnectionException.printStackTrace();
                    } catch (Exception AdminException) {
                        // No error catching so far
                        checkException = true;
                        Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                        AdminException.printStackTrace();
                    }
                }
                stage.close();
                EditProfile.close();
                if(!checkException) SuccessNotifierScreen.displaySuccessInformScreen("Information","Your profile editing is successful");
            }
            else{
                label.setText("Password incorrect. Please type again."); //Password verification is incorrect.
            }
        });
    }
}
