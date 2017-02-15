package Interface;

import DB.hsqldb.HSQLDB;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name : Administrator Control Panel Window
 */

public class AdminControlPanel extends MemberControlPanel{

    public AdminControlPanel(String username, String password, HSQLDB user) {
        super(username, password, user);
    }

    //Override display function from control panel abstract class
    @Override
    void displayControlPanel(HSQLDB user) {
        Button adminBtn = new Button("Admin\nPanel");
        adminBtn.setId("adminBtn");
        adminBtn.setOnAction(e ->  AdministratorOption.displayAdministratorOptionScreen(user));

        controlBox.getChildren().add(0,adminBtn);

        controlPanelScene = new Scene(controlBox);
        controlPanelScene.getStylesheets().add(ControlPanel.class.getResource("css/ControlPanel.css").toExternalForm());
        controlPanelStage.setScene(controlPanelScene);
        controlPanelStage.show();
    }
}
