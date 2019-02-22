package controllers.main;

import controllers.tableview.TableView;
import alert.Alerts;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import beans.SQLSchema;
import com.jfoenix.controls.JFXTabPane;
import connection.SQLConnection;
import controllers.helper.HelperControllers;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.IOException;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import utils.FileUtil;

/**
 * FXML Controller class
 *
 * @author All - abdenour
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private TableView currentTable;
    private List<TableView> tables;

    @FXML
    private Tab SchemaTab;
    @FXML
    private Accordion tablesAccordion;
    @FXML
    private JFXSlider nullsRate;
    @FXML
    private JFXTextField howMuch;
    @FXML
    private JFXToggleButton activateUpdate;
    @FXML
    private JFXTextField tableName;
    @FXML
    private JFXTextField generationTime;
    @FXML
    private VBox insertsVBox;
    @FXML
    private VBox rapportVBox;
    @FXML
    private HBox drag;
    @FXML
    private AnchorPane connectionBox;
    @FXML
    private AnchorPane optionVBox;
    @FXML
    private JFXTabPane tabPane;

    /**
     * Check all field data type
     */
    public void checkFields() {
        howMuch.textProperty().addListener((o, newValue, old) -> {
            if (!newValue.matches("\\d*")) {
                howMuch.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * get tableview from a given sql schema table name
     *
     * @param name
     * @return
     */
    public TableView getTableByName(String name) {
        for (TableView t : tables) {
            if (t.get().getTableName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Create all the table views
     */
    public void createTablesView() {
        tablesAccordion.getPanes().clear();
        tables.forEach(t
                -> tablesAccordion.getPanes().add(new TitledPane(t.get().getTableName(), t.getTableView())));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            HelperControllers.addNodeToContainer("connection.fxml", connectionBox);
            HelperControllers.addNodeToContainer("option.fxml", optionVBox);
            HelperControllers.addNodeToContainer("report.fxml", rapportVBox);
        } catch (Exception ex) {
            Alerts.error(ex.getMessage());
        }
        activateUpdate.selectedProperty().addListener((observable) -> {
            boolean notChecked = !activateUpdate.isSelected();
            if (activateUpdate.isSelected()) {
                howMuch.setDisable(false);
                nullsRate.setDisable(false);
                currentTable.get().setHowMuch(Integer.parseInt(howMuch.getText()));
                currentTable.get().setNullsRate(Double.parseDouble(howMuch.getText()));
            } else {
                howMuch.setDisable(true);
                nullsRate.setDisable(true);
                updateTableConf();
            }
        });
        tablesAccordion.expandedPaneProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                this.currentTable = getTableByName(new_val.getText());
                tableName.setText(currentTable.get().getTableName());
                howMuch.setText(currentTable.get().getHowMuch() + "");
                nullsRate.setValue(currentTable.get().getNullsRate());
                refrechInserts();
            }
        });

        SchemaTab.setOnSelectionChanged(e -> {
            if (SQLConnection.getInstance().isNewConnection()) {
                tables = SQLSchema.getInstance().getTablesAsTablesView();
                createTablesView();
                SQLConnection.getInstance().isNewConnection(false);
            }
        });
    }

    @FXML
    private void onGenerate(ActionEvent event) {
        try {
            SQLSchema.getInstance().startToGenerateInstances();
            tabPane.getSelectionModel().select(1);
            generationTime.setText(SQLSchema.getInstance().getGenerationTime() + " ms");
            for (TableView t : tables) {
                t.updateTableViewInserts();
            }
            SQLConnection.getInstance().writeToDataBase();
            Alerts.done("Génération effectuée");
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }
    }

    /**
     * Refrsh inserts
     */
    public void refrechInserts() {
        insertsVBox.getChildren().clear();
        if (currentTable.getTableInserts() != null) {
            Label l = new Label("Table : " + currentTable.get().getTableName());
            l.setStyle("-fx-font-size:15px");
            insertsVBox.getChildren().addAll(l, currentTable.getTableInserts());
        }
    }

    @FXML
    private void onExport(ActionEvent event) {
        try {
            HelperControllers.showControllerOnAlert("export.fxml", "Exporter");
        } catch (IOException ex) {
            Alerts.error(ex.getMessage());
        }
    }

    @FXML
    private void onConnection(ActionEvent event) {
        tabPane.getSelectionModel().select(0);
    }

    @FXML
    private void onSave(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le répertoire d'enregistrement");
            fileChooser.setInitialDirectory(new File("."));
            File showSaveDialog = fileChooser.showSaveDialog(LuncherApp.getPrimaryStage());
            FileUtil.writeObjectInFile(showSaveDialog.getAbsolutePath(), SQLSchema.getInstance());
            Alerts.done("Projet a été enregistré");
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }

    }

    @FXML
    private void onOption(ActionEvent event) {
        try {
            HelperControllers.showControllerOnAlert("option.fxml", "Configuration");
        } catch (IOException ex) {
            Alerts.error(ex.getMessage());
        }
    }

    @FXML
    private void onOpen(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le répertoire d'enregistrement");
            fileChooser.setInitialDirectory(new File("."));
            File showOpenDialog = fileChooser.showOpenDialog(LuncherApp.getPrimaryStage());
            SQLSchema.setInstance((SQLSchema) FileUtil.readFileObject(showOpenDialog.getAbsolutePath()));
            tables = SQLSchema.getInstance().getTablesAsTablesView();
            tables.forEach(t -> t.updateTableViewInserts());
            createTablesView();
            Alerts.done("Projet a été chargé");
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }
    }

    private void updateTableConf() {
        this.currentTable.get().setHowMuch(Integer.parseInt(howMuch.getText()));
    }
}
