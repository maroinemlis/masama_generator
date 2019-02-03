package controllers.main;

import controllers.tableview.TableView;
import alert.Alerts;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import beans.SQLSchema;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTabPane;
import connection.SQLConnection;
import static utils.FileUtil.readFileObject;
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
import static controllers.main.LuncherApp.primaryStage;
import static utils.FileUtil.writeObjectInFile;
import java.io.IOException;
import controllers.helper.HelperControllers;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author amirouche
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

    public void checkFields() {
        howMuch.textProperty().addListener((o, newValue, old) -> {
            if (!newValue.matches("\\d*")) {
                howMuch.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private TableView getTableByName(String name) {
        for (TableView t : tables) {
            if (t.get().getTableName().equals(name)) {
                return t;
            }
        }
        return null;
    }

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
                currentTable = tables.get(0);
                createTablesView();
                SQLConnection.getInstance().isNewConnection(false);
            }
        });
    }

    @FXML
    private void onGenerate(ActionEvent event) {
        try {
            SQLSchema.getInstance().startToGenerateInstances();
            generationTime.setText(SQLSchema.getInstance().getGenerationTime() + " ms");
            for (TableView t : tables) {
                t.updateTableViewInserts();
            }
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }
    }

    private void refrechInserts() {
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
            HelperControllers.showControlllerOnAlert("export.fxml", "Exporter");
        } catch (IOException ex) {
            Alerts.error(ex.getMessage());
        }
    }

    @FXML
    private void onConnection(ActionEvent event) {
        try {
            HelperControllers.showControlllerOnAlert("connection.fxml", "Connexion");
        } catch (Exception ex) {
            Alerts.error(ex.getMessage());
        }
    }

    @FXML
    private void onSave(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le répertoire d'enregistrement");
            fileChooser.setInitialDirectory(new File("."));
            File showSaveDialog = fileChooser.showSaveDialog(primaryStage);
            writeObjectInFile(showSaveDialog.getAbsolutePath(), SQLSchema.getInstance());
            Alerts.done("Projet enregistré");
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }

    }

    @FXML
    private void onOption(ActionEvent event) {
        try {
            HelperControllers.showControlllerOnAlert("option.fxml", "Configuration");
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
            File showOpenDialog = fileChooser.showOpenDialog(primaryStage);
            SQLSchema.setInstance((SQLSchema) readFileObject(showOpenDialog.getAbsolutePath()));
            tables = SQLSchema.getInstance().getTablesAsTablesView();
            tables.forEach(t -> t.updateTableViewInserts());
            createTablesView();
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }
    }

    private void updateTableConf() {
        this.currentTable.get().setHowMuch(Integer.parseInt(howMuch.getText()));
    }
}
