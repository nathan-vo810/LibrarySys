package Models;

import Interface.Error;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ngpbh on 1/13/2017.
 */
public class MaterialListView {

    public MaterialListView() {}

    //Generate Table of materials
    public static TableView<Material> getMaterialsTable() {
        //Result table
        TableView<Material> resultTable = new TableView<>();
        resultTable.setMinHeight(500);

        TableColumn<Material, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Material, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Material, String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Material, Integer> remainCol = new TableColumn<>("Remain");
        remainCol.setCellValueFactory(new PropertyValueFactory<>("remain"));

        resultTable.getColumns().addAll(isbnCol,titleCol,authorCol,remainCol);
        resultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        resultTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        return  resultTable;
    }

    //Query Data
    public static ObservableList<Material> getData(ResultSet materialsQuery) {
        //Initialize Material list
        ObservableList<Material> materialList = FXCollections.observableArrayList();

        try {
            while(materialsQuery.next()) {
                materialList.add(new Material(materialsQuery));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materialList;
    }
}
