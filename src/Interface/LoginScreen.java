package Interface;

/**
 * Created by NhatAnh on 11/30/16.
 */

import DB.hsqldb.HSQLDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;

public class LoginScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

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

        Button loginButton = new Button("SIGN IN");
        loginButton.setMaxWidth(250);
        loginButton.setOnAction(e -> {
                String text = loginCheck(usernameInput,passwordInput);
                loginStatus.setText(text);
            }
        );

        GridPane loginLayout = new GridPane();
        loginLayout.setPadding(new Insets(10, 10, 10, 10));
        loginLayout.setVgap(10);

        GridPane.setConstraints(welcome, 0, 0);
        GridPane.setConstraints(usernameInput, 0, 1);
        GridPane.setConstraints(passwordInput, 0, 2);
        GridPane.setConstraints(loginButton, 0, 3);
        GridPane.setConstraints(loginStatus, 0, 4);
        GridPane.setHalignment(welcome, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(loginStatus, HPos.CENTER);

        loginLayout.getChildren().addAll(welcome, usernameInput, passwordInput, loginButton, loginStatus);
        loginLayout.setAlignment(Pos.CENTER_RIGHT);

        Scene loginScene = new Scene(loginLayout);
        loginScene.getStylesheets().add(LoginScreen.class.getResource("Login.css").toExternalForm());

        Stage LoginWindow = primaryStage;
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
            return "Success";
        } catch (Exception e) {
            return  "Wrong username or password!!!";
        }
    }
}

