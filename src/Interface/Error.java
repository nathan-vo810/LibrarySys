package Interface;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Error Information Window
 */
public class Error {
    public static void displayErrorInformScreen(String Error_title, String Error_identification, String Error_solution) {
        Scene ErrorDisplayScene;
        Stage Error_DisplayWindow = new Stage();

        VBox ErrorDisplayLayout = new VBox();
        ErrorDisplayLayout.setPrefWidth(450.0);
        ErrorDisplayLayout.setPrefHeight(220.0);
        HBox hBox = new HBox();
        hBox.prefHeight(140.0);
        hBox.prefWidth(450.0);
        Pane leftpane = new Pane();
        leftpane.setId("leftpane");
        Image image = new Image(Profile.class.getResourceAsStream("img/error.png"));
        ImageView error_icon = new ImageView(image);
        error_icon.setFitHeight(118.0);
        error_icon.setFitWidth(120.0);
        error_icon.setLayoutY(14.0);
        error_icon.setLayoutX(5.0);
        error_icon.isPreserveRatio();
        VBox rightpane = new VBox();
        rightpane.setId("rightpane");
        Label error_identification = new Label(Error_identification);
        error_identification.setId("error_identification");
        error_identification.setWrapText(true);
        Label error_solution = new Label(Error_solution);
        error_solution.setId("error_solution");
        error_solution.setWrapText(true);
        Pane bottompane = new Pane();
        bottompane.setPrefHeight(47.0);
        bottompane.setPrefWidth(450.0);
        Button close = new Button("Close");
        close.setLayoutX(356.0);
        close.setLayoutY(8.0);
        close.setPrefWidth(92.0);
        close.setPrefHeight(31.0);
        if(Error_solution.compareTo("Click close to exit the program.")==0){ //Click to exit program for some fatal errors.
            close.setOnAction(event -> {
                Platform.exit();
                System.exit(0);
            });
        } else {
            close.setOnAction(event -> { //Click to close the error window for minor errors.
                Error_DisplayWindow.close();
            });
        }
        Error_DisplayWindow.setOnCloseRequest(event -> {
            if(Error_solution.compareTo("Click close to exit program.")==0){
                    Platform.exit();
                    System.exit(0);
            } else {
                    Error_DisplayWindow.close();
            }
        });

        leftpane.getChildren().addAll(error_icon);
        rightpane.getChildren().addAll(error_identification,error_solution);
        hBox.getChildren().addAll(leftpane,rightpane);
        bottompane.getChildren().addAll(close);
        ErrorDisplayLayout.getChildren().addAll(hBox,bottompane);

        ErrorDisplayScene = new Scene(ErrorDisplayLayout);
        Error_DisplayWindow.initModality(Modality.APPLICATION_MODAL);
        Error_DisplayWindow.setTitle(Error_title);
        Error_DisplayWindow.setScene(ErrorDisplayScene);
        Error_DisplayWindow.setResizable(false);
        ErrorDisplayScene.getStylesheets().add(Error.class.getResource("css/Error.css").toExternalForm());
        Error_DisplayWindow.getIcons().add(new Image( Error.class.getResourceAsStream( "img/error_icon.png" )));
        Error_DisplayWindow.show();
    }
}
