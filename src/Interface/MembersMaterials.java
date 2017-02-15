package Interface;

import DB.hsqldb.HSQLDB;
import Models.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;

/**     Advanced Object-Oriented Programming with Java-Project - WS16
 *      Prof. Biemann
 *      GROUP G - LIBRARY MANAGEMENT SYSTEM
 *      Author:         Anh, Vo Nguyen Nhat
 *                      Hoang, Nguyen Phuoc Bao
 *      Name: Member Borrowing Material Window
 */
public class MembersMaterials {

    public static AnchorPane displayMembersMaterials(HSQLDB user) {
        TableView<Borrow> borrowTableView = new TableView<>();
        //Borrow Table View contains 2 column: UserID (who borrows) and Material ID (what is borrowed)
        TableColumn<Borrow, String> userIDCol = new TableColumn<>("User ID"); //User ID
        userIDCol.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getCurrentMember().getLID()));

        TableColumn<Borrow, String> materialIDCol = new TableColumn<>("Material ID"); // Material ID
        materialIDCol.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getCurrentMaterial().getTitle()));

        ObservableList<Borrow> borrowList = FXCollections.observableArrayList();
        try {
            ResultSet borrowData = user.query("SELECT * FROM BORROW"); //
            while (borrowData.next()) {
                String userID = borrowData.getString("UserID");
                String materialID = borrowData.getString("Material_ID");

                if (!userID.equals("0")) { // add admin as an exception.
                    Member member = null;
                    ResultSet memberQuery = user.query("SELECT * FROM STUDENT WHERE MATRNR = '" + userID + "'"); //Take all the information
                    if (!memberQuery.next()) {                                                                   //about that userID
                        memberQuery = user.query("SELECT * FROM FACULTY WHERE PID = '" + userID + "'");
                        member = new Faculty(memberQuery);
                    } else {
                        memberQuery.beforeFirst();
                        member = new Student(memberQuery);
                    }
                    ResultSet materialQuery = user.query("SELECT * FROM MATERIAL WHERE MATERIAL_ID ='" + materialID + "'"); //Take all the information
                    materialQuery.next();                                                                                   //about that material ID
                    Material material = new Material(materialQuery);
                    borrowList.add(new Borrow(member, material)); //Only need the primary keys from 2 tables.
                }                                                 //The rest of the information is not used.
            }                                                     //Stored for future purpose (e.g. Display detailed information)
        } catch (Exception DatabaseConnectionException) {
            //No catching error so far
            Error.displayErrorInformScreen("Database error", "Seems like there are some problems with the database connection", "Click close to try again.");
            DatabaseConnectionException.printStackTrace();
        }

        borrowTableView.setPrefWidth(500);
        borrowTableView.setPrefHeight(500);
        borrowTableView.getColumns().addAll(userIDCol, materialIDCol);
        borrowTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        borrowTableView.setItems(borrowList);

        AnchorPane membersBooksPane = new AnchorPane();
        membersBooksPane.getChildren().addAll(borrowTableView);
        return membersBooksPane;
    }
}
