package Interface;

import DB.hsqldb.HSQLDB;
import Models.Material;
import Models.Member;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Borrow Material Confirmation Box
 */
public class BorrowMaterialConfirmationBox {
    public static void displayBorrowBookConfirmation(TableView<Material> materialsTableView, HSQLDB user, Member currentMember){
        VBox mainPane = new VBox();
        mainPane.setId("mainPane");
        mainPane.setPadding(new Insets(10,10,10,10));
        mainPane.setSpacing(10);
        Label confirmationLabel = new Label("Are you sure you want to borrow the following materials?");
        confirmationLabel.setId("confirmation_identification");
        Label ListOfSeletingBooks = new Label();
        ListOfSeletingBooks.setId("confirmation_list");
        Label BlankLabel = new Label();
        Integer[] indices = new Integer[materialsTableView.getSelectionModel().getSelectedIndices().size()];
        materialsTableView.getSelectionModel().getSelectedIndices().toArray(indices);
        Integer counter = indices.length;
        for (Integer index : indices) {
            String materialName = materialsTableView.getItems().get(index).getTitle();
            ListOfSeletingBooks.setText("\t" + counter + ". " + materialName + "\n" + ListOfSeletingBooks.getText());
            counter--;
        }
        Button YesBtn = new Button("Yes");
        YesBtn.setMinWidth(100);
        Button NoBtn = new Button("No");
        NoBtn.setMinWidth(100);
        HBox ButtonBox = new HBox(YesBtn,NoBtn);
        ButtonBox.setSpacing(10);
        ButtonBox.setPadding(new Insets(10,10,10,10));
        ButtonBox.setAlignment(Pos.CENTER_RIGHT);
        mainPane.getChildren().addAll(confirmationLabel,ListOfSeletingBooks,BlankLabel,ButtonBox);
        Scene scene = new Scene(mainPane);
        Stage stage = new Stage();
        scene.getStylesheets().add(Error.class.getResource("css/BorrowMaterialConfirmationBox.css").toExternalForm());
        stage.getIcons().add(new Image( Error.class.getResourceAsStream( "img/confirmation.jpg" )));
        stage.setTitle("Confirm");
        stage.setScene(scene);
        stage.setResizable(false);
        YesBtn.setOnAction(event -> {
            reserveMaterial(materialsTableView,user,currentMember);
            try {
                Profile profile = new Profile();
                profile.displayBorrowed(user, currentMember);
            } catch (Exception DatabaseConnectionException) {
                //Error can be derived from exception throw in displayBorrowed()
                Error.displayErrorInformScreen("Error", "Seems like there is a problem with the database connection.\n Please try again later.", "Click close to exit the program.");
                DatabaseConnectionException.printStackTrace();
            }
            MaterialSearch.searchMaterial(user); // refresh book search window
            stage.close();
        });
        NoBtn.setOnAction(event -> {
            stage.close();
        });
        stage.show();
    }

    //Reserve material function
    public static void reserveMaterial(TableView<Material> resultTable, HSQLDB user, Member currentMember) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Integer[] indices = new Integer[resultTable.getSelectionModel().getSelectedIndices().size()];
        resultTable.getSelectionModel().getSelectedIndices().toArray(indices);
        for (Integer index : indices) {
            try {
                int materialID = resultTable.getItems().get(index).getMaterial_id();
                int remain = resultTable.getItems().get(index).getRemain();
                String UserID = "";
                if (currentMember != null) UserID = currentMember.getUserID();
                else UserID = "0000000"; // Identifier for administrator.
                if(remain>0) {
                    user.query("INSERT INTO BORROW VALUES (" + UserID + "," + materialID + "," + "'" + dateFormat.format(today).toString() + "'" + ")");
                    user.query("UPDATE MATERIAL SET REMAIN = REMAIN - 1 WHERE MATERIAL_ID = " + materialID);
                }
                else{
                    String materialName = resultTable.getItems().get(index).getTitle();
                    Error.displayErrorInformScreen("Error","There is no remaining material of this title : " + materialName + ". Please select another one.","Click close to try again");
                }
            } catch (Exception DatabaseConnectionException) {
                //Error can be
                //The user has already borrow this book.
                Error.displayErrorInformScreen("Database error", "Seems like there are some problems with the database connection", "Click close to try again.");
                DatabaseConnectionException.printStackTrace();
            }
        }
    }
}
