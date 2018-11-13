package views.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import db.models.AttributeModel;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
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
    TableView currentTable;
    List<TableView> tables;
    @FXML
    private Label tableNameLabel;
    @FXML
    private VBox insertsVBox;
    @FXML
    private Accordion tablesAccordion;
    private AttributeModel currentAttribute;

    private TableView getTableByName(String name) {
        for (TableView t : tables) {
            if (t.get().getTableName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public void createTablesView() {
        for (TableView table : tables) {
            TitledPane titledPane = new TitledPane(table.get().getTableName(), table.getTableView());
            tablesAccordion.getPanes().add(titledPane);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //cnx = new SQLConnection("/home/amirouche/NetBeansProjects/MASAMA/mySQL/test.sql");
            //cnx = new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test.sql");
            cnx = new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test2.sql", "sqlite", false);
            schema = new SQLSchema();
            tables = schema.getTablesAsTablesView();
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.currentTable = tables.get(0);
        createTablesView();
        tablesAccordion.expandedPaneProperty().addListener((ObservableValue<? extends TitledPane> ov, TitledPane old_val, TitledPane new_val) -> {
            if (new_val != null) {
                this.currentTable = getTableByName(new_val.getText());
                refrechInserts();
                this.currentTable.getTableView().getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
                    this.currentAttribute = n.getValue();
                });
            }
        });
    }

    private void onOpenFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner un script sql");
        File fileUrl = fileChooser.showOpenDialog(null);
        try {
            cnx = new SQLConnection(fileUrl.getPath(), "sqlite", false);
            schema = new SQLSchema();
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        createTablesView();
    }

    @FXML
    private void onGenerate(ActionEvent event) {
        schema.startToGenerateInstances();
        refrechInserts();

    }

    private void refrechInserts() {
        insertsVBox.getChildren().clear();
        if (currentTable.getTableInserts() != null) {
            Label l = new Label("Table : " + currentTable.get().getTableName());
            l.setStyle("-fx-font-size:15px");
            insertsVBox.getChildren().addAll(l, currentTable.getTableInserts()
            );
        }
    }
}
