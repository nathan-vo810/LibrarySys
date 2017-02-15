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
 *      Name: Success Information Display Window
 */

public class SuccessNotifierScreen {
    public static void displaySuccessInformScreen(String SuccessTitle, String SuccessNotification) {
        Scene SuccessDisplayScene;
        Stage SuccessDisplayWindow = new Stage();

        VBox SuccessDisplayLayout = new VBox();
        SuccessDisplayLayout.setPrefWidth(450.0);
        SuccessDisplayLayout.setPrefHeight(150.0);
        HBox hBox = new HBox();
        hBox.prefHeight(140.0);
        hBox.prefWidth(450.0);
        Pane leftpane = new Pane();
        leftpane.setId("leftpane");
        leftpane.setPrefHeight(140.0);
        leftpane.setPrefWidth(133.0);
        Image image = new Image(Profile.class.getResourceAsStream("img/success-icon.png"));
        ImageView success_icon = new ImageView(image);
        success_icon.setFitHeight(118.0);
        success_icon.setFitWidth(120.0);
        success_icon.setLayoutY(14.0);
        success_icon.setLayoutX(5.0);
        success_icon.isPreserveRatio();
        VBox rightpane = new VBox();
        rightpane.setId("rightpane");
        rightpane.setPrefWidth(331.0);
        rightpane.setPrefHeight(180.0);
        Label success_identification = new Label(SuccessNotification);
        success_identification.setId("error_identification");
        success_identification.setWrapText(true);
        Pane bottompane = new Pane();
        bottompane.setPrefHeight(47.0);
        bottompane.setPrefWidth(450.0);
        Button close = new Button("OK");
        close.setLayoutX(300.0);
        close.setLayoutY(-30.0);
        close.setPrefWidth(92.0);
        close.setPrefHeight(31.0);
            close.setOnAction(event -> {
                SuccessDisplayWindow.close();
            });

        SuccessDisplayWindow.setOnCloseRequest(event -> {
                SuccessDisplayWindow.close();
        });

        leftpane.getChildren().addAll(success_icon);
        rightpane.getChildren().addAll(success_identification);
        hBox.getChildren().addAll(leftpane,rightpane);
        bottompane.getChildren().addAll(close);
        SuccessDisplayLayout.getChildren().addAll(hBox,bottompane);

        SuccessDisplayScene = new Scene(SuccessDisplayLayout);
        SuccessDisplayWindow.initModality(Modality.APPLICATION_MODAL);
        SuccessDisplayWindow.setTitle(SuccessTitle);
        SuccessDisplayWindow.setScene(SuccessDisplayScene);
        SuccessDisplayWindow.setResizable(false);
        SuccessDisplayLayout.getStylesheets().add(Error.class.getResource("css/Error.css").toExternalForm());
        SuccessDisplayWindow.getIcons().add(new Image( Error.class.getResourceAsStream( "img/success-icon.png" )));
        SuccessDisplayWindow.show();
    }
}
