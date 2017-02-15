package Interface;

import DB.hsqldb.HSQLDB;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.ResultSet;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Control Panel Abstract Class
 */
public abstract class ControlPanel {

    Stage controlPanelStage;
    Scene controlPanelScene;
    HBox controlBox;

    public ControlPanel(String username, String password, HSQLDB user) {
        Button searchBtn = new Button("Search\nMaterial");
        searchBtn.setId("searchBtn");
        searchBtn.setOnAction(e -> MaterialSearch.displayMaterialSearch(username, user));

        Button profileBtn = new Button("Display\nProfile");
        profileBtn.setId("profileBtn");
        profileBtn.setOnAction(e -> Profile.displayProfileScene(username, password, user));

        Button signoutBtn = new Button("Sign\nOut");
        signoutBtn.setId("signoutBtn");
        signoutBtn.setOnAction(event -> {
            try{
                ResultSet Student_query = user.query("SELECT * FROM STUDENT WHERE LID = '" + username + "'");
                ResultSet Faculty_query = user.query("SELECT * FROM FACULTY WHERE LID = '" + username + "'");
                if(!Student_query.next()&&!Faculty_query.next()) user.shutdown();
                else{
                    HSQLDB admin = new HSQLDB("SA","admin@vgu");
                    admin.shutdown();
                }
                Platform.exit();    //Stop all running interfaces - windows
                System.exit(0);     //Stop the system return to 0
            } catch (Exception CannotSignoutException){
                //This error cannot be reached yet.
                //So far we did not catch this exception.
                Error.displayErrorInformScreen("Cannot exit the application", "Seems like there is a problem with the system.\n Please try again later.", "Click close to exit the program.");
                CannotSignoutException.printStackTrace();
            }
        });

        controlBox = new HBox();
        controlBox.setId("controlBox");
        controlBox.getChildren().addAll(searchBtn, profileBtn, signoutBtn);

        controlPanelStage = new Stage();
        controlPanelStage.setResizable(false);
        controlPanelStage.initStyle(StageStyle.UNDECORATED);
        controlPanelStage.getIcons().add(new Image( ControlPanel.class.getResourceAsStream( "img/panel.png" )));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        controlPanelStage.setX(primaryScreenBounds.getMinX() + 50);
        controlPanelStage.setY(primaryScreenBounds.getMinY() + 50);

      }

    abstract void displayControlPanel(HSQLDB user);

}
