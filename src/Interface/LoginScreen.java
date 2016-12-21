package Interface;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Date created:   30.11.2016
 *      Date modified:  02.12.2016
 *      Version:        1.0
 */

import DB.hsqldb.HSQLDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

import java.awt.print.Book;

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
        loginStatus.setId("status");
        Label welcome = new Label("Welcome to the Library");
        welcome.setId("welcome");

        TextField usernameInput = new TextField();
        usernameInput.setMaxWidth(250);
        usernameInput.setPromptText("Username");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setMaxWidth(250);
        passwordInput.setPromptText("Password");

        Button register = new Button("REGISTER AS NEW USER");
        register.setId("registerBtn");
        register.setOnAction(e ->{
            RegisterScreen.displayRegisterScene();
        });

        Button loginButton = new Button("SIGN IN");
        loginButton.setId("loginBtn");
        loginButton.setOnAction(e -> {
                    String text = loginCheck(usernameInput, passwordInput);
                    loginStatus.setText(text);

                    if (text == "Success") {
                        try {
                            primaryStage.close();
                            HSQLDB user = new HSQLDB(usernameInput.getText(), passwordInput.getText());
                            BookSearch bookSearch = new BookSearch();
                            bookSearch.displayBookSearch(usernameInput.getText(), passwordInput.getText(), user);
                            StudentProfileScreen.displayStudentProfileScene(usernameInput.getText(), passwordInput.getText(), user, bookSearch);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        loginStatus.setText(text);
                    }
            }
        );
        //Bind to enter
        loginButton.setDefaultButton(true);

//Layout for the Login SCREEN
        GridPane loginLayout = new GridPane();
        loginLayout.setPadding(new Insets(10, 10, 10, 10));
        loginLayout.setVgap(10);
        loginLayout.setHgap(8);

        GridPane.setConstraints(welcome, 0, 0);
        GridPane.setConstraints(usernameInput, 0, 1);
        GridPane.setConstraints(passwordInput, 0, 2);
        GridPane.setConstraints(loginButton, 0, 3);
        GridPane.setConstraints(register, 0, 4);
        GridPane.setConstraints(loginStatus, 0, 5);
        GridPane.setHalignment(register, HPos.RIGHT);
        GridPane.setHalignment(welcome, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(register, HPos.CENTER);
        GridPane.setHalignment(loginStatus, HPos.CENTER);

        loginLayout.getChildren().addAll(welcome, usernameInput, passwordInput, loginButton, loginStatus, register);
        loginLayout.setAlignment(Pos.CENTER_RIGHT);

//-------------------------------------------------------------------------------------------//

        loginScene = new Scene(loginLayout);
        loginScene.getStylesheets().add(LoginScreen.class.getResource("Login.css").toExternalForm());
        LoginWindow = primaryStage;
        LoginWindow.getIcons().add(new Image( LoginScreen.class.getResourceAsStream( "img/tab_icon.jpg" )));
        LoginWindow.setTitle("Library Management System");
        LoginWindow.setScene(loginScene);
        LoginWindow.setWidth(550);
        LoginWindow.setHeight(400);
        LoginWindow.setResizable(false);
        LoginWindow.show();
    }

    private String loginCheck(TextField usr, PasswordField pass) {
        try {
            HSQLDB database = new HSQLDB(usr.getText(),pass.getText());
            database.shutdown();
            return "Success";
        } catch (Exception e) {
            return  "Wrong username or password!!!";
        }
    }
}

