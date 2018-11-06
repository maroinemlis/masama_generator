package views.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import db.bean.Attribute;
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author amirouche
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    SQLConnection cnx;
    SQLSchema schema;
    @FXML
    VBox left;

    public JFXTreeTableView<AttributeModel> createTreeTable(Table table) {
        JFXTreeTableView<AttributeModel> treeTableView = new JFXTreeTableView<>();
        treeTableView.setShowRoot(false);
        JFXTreeTableColumn<AttributeModel, String> name = new JFXTreeTableColumn<>("Colonne");
        JFXTreeTableColumn<AttributeModel, String> type = new JFXTreeTableColumn<>("Type");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> pk = new JFXTreeTableColumn<>("Clé primaire");

        name.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        type.setCellValueFactory(new TreeItemPropertyValueFactory("dataType"));
        pk.setCellValueFactory(new TreeItemPropertyValueFactory("isPrimaryKey"));

        List<AttributeModel> collect = table.getAttributes().stream().
                map(att -> new AttributeModel(att)).
                collect(Collectors.toList());
        ObservableList<AttributeModel> observables = FXCollections.observableArrayList(collect);
        treeTableView.getColumns().addAll(name, type, pk);
        treeTableView.setRoot(new RecursiveTreeItem<>(observables, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        return treeTableView;
    }

    public void addTablesToView() {
        boolean first = true;
        for (Table table : schema.getTables()) {
            TitledPane titledPane = new TitledPane(table.getTableName(), createTreeTable(table));
            first = !first;
            titledPane.setExpanded(first);
            left.getChildren().add(titledPane);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            cnx = new SQLConnection("/home/amirouche/NetBeansProjects/MASAMA/mySQL/test.sql");
            //cnx = new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test.sql");
            schema = new SQLSchema();
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addTablesToView();
    }

}
