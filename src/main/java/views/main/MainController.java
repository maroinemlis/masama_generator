package views.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import db.models.AttributeModel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import db.bean.Attribute;
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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
    private VBox insertTab;
    @FXML
    private Accordion left;
    @FXML
    private Tab tabName;

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

    public JFXTreeTableView<AttributeModel> createTreeTableInsert(Table table) {
        JFXTreeTableView<AttributeModel> treeTableView = new JFXTreeTableView<>();
        treeTableView.setShowRoot(false);
        for (Attribute attribute : table.getAttributes()) {
            JFXTreeTableColumn<AttributeModel, String> col = new JFXTreeTableColumn<>(attribute.getName());
            treeTableView.getColumns().add(col);
        }
        return treeTableView;
    }

    public void addTablesToView() {
        for (Table table : schema.getTables()) {
            TitledPane titledPane = new TitledPane(table.getTableName(), createTreeTable(table));
            left.getPanes().add(titledPane);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onOpenFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner un script sql");
        File fileUrl = fileChooser.showOpenDialog(null);
        try {
            cnx = new SQLConnection(fileUrl.getPath());
            schema = new SQLSchema();
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addTablesToView();
        left.expandedPaneProperty().addListener((ObservableValue<? extends TitledPane> ov, TitledPane old_val, TitledPane new_val) -> {
            if (new_val != null) {
                Table tableByName = schema.getTableByName(new_val.getText());
                insertTab.getChildren().clear();
                insertTab.getChildren().add(createTreeTableInsert(tableByName));
                tabName.setText(tableByName.getTableName());
            }
        });
    }

}
