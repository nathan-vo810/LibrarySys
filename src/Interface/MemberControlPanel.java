package Interface;

import DB.hsqldb.HSQLDB;
import javafx.scene.Scene;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Member Control Panel Window
 */
public class MemberControlPanel extends ControlPanel {

    public MemberControlPanel(String username, String password, HSQLDB user) {
        super(username, password, user);
    }

    //Override an abstract function in Control Panel class
    @Override
    void displayControlPanel(HSQLDB user) {
        controlPanelScene = new Scene(controlBox);
        controlPanelScene.getStylesheets().add(ControlPanel.class.getResource("css/ControlPanel.css").toExternalForm());
        controlPanelStage.setScene(controlPanelScene);
        controlPanelStage.show();
    }
}
