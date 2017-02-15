package Interface;

import DB.hsqldb.HSQLDB;
import Models.Faculty;
import Models.Member;
import Models.Student;
import javafx.geometry.Insets;
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
 *      Name: Edit Profile Window
 */
public class EditProfile {
    static Stage EditProfileStage = new Stage();
    public static void displayEditProfileScreen(String usernameInput, String passwordInput, HSQLDB user) throws SQLException {

        ResultSet Student_query = null;
        ResultSet Faculty_query = null;
        try {
            Student_query = user.query("SELECT * FROM STUDENT WHERE LID = '" + usernameInput + "'");
            Faculty_query = user.query("SELECT * FROM FACULTY WHERE LID = '" + usernameInput + "'");
        } catch (SQLException DatabaseConnectionException) {
            //No error catching yet.
            Error.displayErrorInformScreen("Database error", "The connection is currently inaccessible or this student does not exist in the database", "Click close to try again.");
            DatabaseConnectionException.printStackTrace();
        }

        Member currentMember = null;
        try {
            if (Student_query.next()) { //if edit profile is a student profile
                Student_query.beforeFirst();
                currentMember = new Student(Student_query);
            }
            else if (Faculty_query.next()) { //if edit profile is a faculty profile
                Faculty_query.beforeFirst();
                currentMember = new Faculty(Faculty_query);
            }
        } catch (Exception DatabaseConnectionException) {
            //No error catching yet.
            Error.displayErrorInformScreen("Error","Cannot create instance","Click close to try again.");
            DatabaseConnectionException.printStackTrace();
        }

        //Information Panel

        String UserID = currentMember.getUserID();
        String name = currentMember.getFName() + " " + currentMember.getLName();
        String Phone = currentMember.getPhone();
        String Address = currentMember.getAddress();

        AnchorPane leftPane = new AnchorPane();
        VBox ContentBox = new VBox();
        ContentBox.setPadding(new Insets(15, 15, 15, 15));
        ContentBox.setSpacing(20);
        Label IDLabel = new Label("ID Number");
        Label Fnamelabel = new Label("First name");
        Label Lnamelabel = new Label("Last name");
        Label Changepasswordlabel = new Label("New Password:");
        Label Phonelabel = new Label("Phone");
        Label Addresslabel = new Label("Address");
        ContentBox.getChildren().addAll(IDLabel,Fnamelabel,Lnamelabel,Changepasswordlabel,Phonelabel,Addresslabel);
        leftPane.getChildren().addAll(ContentBox);

        AnchorPane rightPane = new AnchorPane();
        VBox ProfileInfoBox = new VBox();
        ProfileInfoBox.setPadding(new Insets(10, 10, 10, 10));
        ProfileInfoBox.setSpacing(10);
        TextField ID = new TextField(UserID);
        ID.setEditable(false);
        TextField Fnamefield = new TextField(currentMember.getFName());
        Fnamefield.setEditable(false);
        TextField Lnamefield = new TextField(currentMember.getLName());
        Lnamefield.setEditable(false);
        PasswordField Changepasswordfield = new PasswordField();
        TextField Phonefield = new TextField(Phone);
        TextField Addressfield = new TextField(Address);
        Button changeBtn = new Button("Apply");
        ProfileInfoBox.getChildren().addAll(ID,Fnamefield,Lnamefield,Changepasswordfield,Phonefield,Addressfield,changeBtn);
        rightPane.getChildren().addAll(ProfileInfoBox);

        SplitPane mainPane = new SplitPane(leftPane,rightPane);
        mainPane.setDividerPositions(0.297979797979797);

        Scene EditProfileScene = new Scene(mainPane);

        changeBtn.setOnAction(event -> {
            ConfimPassword.displayConfirmPasswordScreen(usernameInput, passwordInput, user, Changepasswordfield.getText(), Phonefield.getText(), Addressfield.getText());
        });
        EditProfileStage.setTitle("Change your profile");
        EditProfileStage.setResizable(false);
        EditProfileStage.setScene(EditProfileScene);
        EditProfileStage.show();
    }

    public static void close(){
        EditProfileStage.close();
    }
}
