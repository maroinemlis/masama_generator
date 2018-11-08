package views.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXListView;
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import db.models.AttributeModel;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
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
    @FXML
    private VBox insertTab;
    @FXML
    private Accordion left;
    @FXML
    private Tab tabName;
    @FXML
    private TextField howMuch;
    @FXML
    private TextField nullsRate;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    private Table currentTable;
    @FXML
    private JFXListView<String> generatorTypeList;
    @FXML
    private JFXListView<String> specificTypeList;
    @FXML
    private TextField attributeField;
    @FXML
    private TextField tableField;
    private AttributeModel currentAttribute;

    public void createTablesView() {
        for (Table table : schema.getTables()) {
            TitledPane titledPane = new TitledPane(table.getTableName(), table.getTableView());
            left.getPanes().add(titledPane);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //cnx = new SQLConnection("/home/amirouche/NetBeansProjects/MASAMA/mySQL/test.sql");
            //cnx = new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test.sql");
            cnx = new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test2.sql");
            schema = new SQLSchema();
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.currentTable = schema.getTables().get(0);
        createTablesView();
        generatorTypeList.getItems().addAll(new Types().TYPES_MAPPING.keySet());
        generatorTypeList.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
            specificTypeList.getItems().clear();
            specificTypeList.getItems().addAll(Types.TYPES_MAPPING.get(n));
        });

        left.expandedPaneProperty().addListener((ObservableValue<? extends TitledPane> ov, TitledPane old_val, TitledPane new_val) -> {
            if (new_val != null) {
                this.currentTable = schema.getTableByName(new_val.getText());
                int _howMuch = Integer.parseInt(this.howMuch.getText());
                int _nullsRate = Integer.parseInt(this.nullsRate.getText());
                insertTab.getChildren().clear();
                tabName.setText(currentTable.getTableName());
                tableField.setText(currentTable.getTableName());
                if (currentTable.getInsertsView() != null) {
                    insertTab.getChildren().add(currentTable.getInsertsView());
                }
                this.currentTable.getTableView().getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
                    this.currentAttribute = n.getValue();
                    attributeField.setText(currentAttribute.getName());
                });

            }
        });
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
        createTablesView();
    }

    @FXML
    private void onGenerate(ActionEvent event) {
        schema.startToGenerateInstances();
    }

    @FXML
    private void onModifiyConfig(ActionEvent event) {
        this.currentAttribute.getAttribute().getDataFaker().setConfiguration(
                from.getText(),
                to.getText(),
                generatorTypeList.getSelectionModel().getSelectedItem(),
                specificTypeList.getSelectionModel().getSelectedItem()
        );
        this.currentTable.getTableView().refresh();
    }

    @FXML
    private void onModifiyConfigTable(ActionEvent event) {
        for (Table t : schema.getTables()) {
            t.setHowMuch(Integer.parseInt(howMuch.getText()));
            t.setNullsRate(Integer.parseInt(nullsRate.getText()));
        }

    }
}
