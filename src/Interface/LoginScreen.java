package Interface;

import DB.hsqldb.HSQLDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.security.MessageDigest;
import java.sql.ResultSet;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Title : Login Window
 */


public class LoginScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene loginScene;
        Stage LoginWindow;

        //Login Scene
        Label loginStatus = new Label();
        loginStatus.setId("loginStatus");
        Label welcome = new Label("Welcome to the Library");
        welcome.setId("welcome");

        TextField usernameInput = new TextField();
        usernameInput.setId("usernameInput");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setId("passwordInput");

        //Register button
        Button register = new Button("REGISTER AS NEW USER");
        register.setId("registerBtn");
        register.setOnAction(e ->{
            RegisterScreen.displayRegisterScene();
        });

        //Login button
        Button loginButton = new Button("SIGN IN");
        loginButton.setId("loginBtn");
        loginButton.setOnAction(e -> {
                    String text = loginCheck(usernameInput, passwordInput); // check the validity of the username and password input
                                                                            // and return text which is status of the check
                    loginStatus.setText(text);

                    if (text.contains("Success")) {
                        try {
                            //Making case-insensitive
                            String usrName = usernameInput.getText().toLowerCase();

                            primaryStage.close();
                            HSQLDB user = new HSQLDB(usernameInput.getText(),passwordInput.getText());
                            ControlPanel controlPanel;
                            if(text.contains("Student")&&text.contains("Faculty")){ //the user is Student or Faculty - by identify the loginStatus
                                //Student and Faculty Panel
                                controlPanel = new MemberControlPanel(usrName, passwordInput.getText(), user);
                                controlPanel.displayControlPanel(user);
                            }
                            else{ //the user is indeed an administrator - by identify the loginStatus
                                //Admin Panel
                                controlPanel = new AdminControlPanel(usrName, passwordInput.getText(), user);
                                controlPanel.displayControlPanel(user);

                            }
                        } catch (Exception CannotAccessException) {
                            // Error here can be
                            // There exists query about that user in the DB but somehow the user command CREATE USER ... PASSWORD
                            // and GRANT ROLE ... is deleted. Therefore, cannot create Connection with DB.
                            Error.displayErrorInformScreen("Cannot access to the library database", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                            CannotAccessException.printStackTrace();
                        }
                    } else {
                        // Login Status send out message states that user types in wrong username or password
                        loginStatus.setText(text);
                    }
            }
        );
        //Bind to enter
        loginButton.setDefaultButton(true);

//Layout for the Login SCREEN
        GridPane loginLayout = new GridPane();
        loginLayout.setId("loginLayout");
        GridPane.setConstraints(welcome, 0, 0);
        GridPane.setConstraints(usernameInput, 0, 1);
        GridPane.setConstraints(passwordInput, 0, 2);
        GridPane.setConstraints(loginButton, 0, 3);
        GridPane.setConstraints(register, 0, 4);
        GridPane.setConstraints(loginStatus, 0, 5);
//-------------------------------------------------------------------------------------------//
        loginLayout.getChildren().addAll(welcome, usernameInput, passwordInput, loginButton, loginStatus, register);


        loginScene = new Scene(loginLayout);
        loginScene.getStylesheets().add(LoginScreen.class.getResource("css/Login.css").toExternalForm());
        LoginWindow = primaryStage;

        LoginWindow.getIcons().add(new Image( LoginScreen.class.getResourceAsStream( "img/tab_icon.jpg" )));
        LoginWindow.setTitle("Library Management System");
        LoginWindow.setScene(loginScene);
        LoginWindow.setResizable(false);
        LoginWindow.show();
    }

    //Check username and password
    private String loginCheck(TextField usr, PasswordField pass) {
        try {
            //Making case-insensitive
            String usrName = usr.getText().toLowerCase();
            HSQLDB database = new HSQLDB(usr.getText(),pass.getText()); // try to create connection with the DB
            ResultSet Student_query = database.query("SELECT * FROM STUDENT WHERE LID = '" + usrName + "'"); //check if there exists a student
            ResultSet Faculty_query = database.query("SELECT * FROM FACULTY WHERE LID = '" + usrName + "'"); // or a faculty with that username
            if(!Student_query.next()&&!Faculty_query.next()){
                database.shutdown();                    // No queries in Student and Faculty but got connection means
                return "Success for Administrator";     // this is the admin account
            }
            else{
                return "Success for Student or Faculty";
            }
        } catch (Exception CannotAccessException) {//Only occurs when cannot create connection with the DB
            return  "Wrong username or password!"; //means wrong username or password
        }
    }
}

