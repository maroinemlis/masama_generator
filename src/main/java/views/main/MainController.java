package views.main;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXToggleButton;
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import static db.utils.FileUtil.readFileObject;
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
import javafx.stage.Modality;
import static views.main.LuncherApp.primaryStage;
import static db.utils.FileUtil.writeObjectInFile;
import db.validation.PreCondetion;
import views.connection.ConnectionController;
import java.io.IOException;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.export.ExportController;
import views.option.OptionController;
import views.report.ReportController;

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
    public static SQLSchema schema;
    TableView currentTable;
    List<TableView> tables;
    Path path;
    @FXML
    private VBox insertsVBox;
    @FXML
    private Accordion tablesAccordion;
    @FXML
    private HBox drag;
    @FXML
    private JFXTextField howMuch;
    @FXML
    private JFXSlider nullsRate;

    public Text chargement_en_cours;

    @FXML
    public JFXProgressBar progress_Bar;

    @FXML
    private JFXToggleButton activateUpdate;

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
        activateUpdate.selectedProperty().addListener((observable) -> {
            boolean notChecked = !activateUpdate.isSelected();
            if (activateUpdate.isSelected()) {
                howMuch.setDisable(false);
            } else {
                howMuch.setDisable(true);
                updateTableConf();
            }
        });
        tablesAccordion.expandedPaneProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                this.currentTable = getTableByName(new_val.getText());
                howMuch.setText(currentTable.get().getHowMuch() + "");
                refrechInserts();
            }
        });
    }

    private boolean isErrorInTable() {
        for (Table table : schema.getTables()) {
            if (table.getIsErrorInTable()) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void onGenerate(ActionEvent event) throws Exception {
        currentTable.get().setHowMuch(Integer.parseInt(howMuch.getText()));
        PreCondetion preCondetion = new PreCondetion(schema);
        boolean isNoContradiction = preCondetion.checkSqlSchema();

        if (isNoContradiction) {
            if (!isErrorInTable()) {
                System.out.println("we can generate");
                schema.startToGenerateInstances();
                for (TableView t : tables) {
                    t.updateTableViewInserts();
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur dans les contrainte");
            alert.setHeaderText(preCondetion.getMsgError());
            alert.showAndWait();
            System.out.println(preCondetion.getMsgError());
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
    private void onExport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/export.fxml"));
        Parent root1 = (Parent) loader.load();
        ExportController controller = loader.<ExportController>getController();
        controller.initData(tables, path);
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root1);
        scene.getStylesheets().add("styles/main.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exporter");
        primaryStage.show();
    }

    @FXML
    private void onReport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/report.fxml"));
        Parent root1 = (Parent) loader.load();
        ReportController controller = loader.<ReportController>getController();
        controller.initData(cnx);
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root1);
        scene.getStylesheets().add("styles/main.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rapport");
        primaryStage.show();
    }

    @FXML
    private void onConnection(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/connection.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ConnectionController controller = fxmlLoader.<ConnectionController>getController();
        JFXAlert alert = new JFXAlert();
        controller.setAlert(alert);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Connection");
        alert.setContent(root);
        alert.show();
        alert.setOnCloseRequest((e) -> {
            try {
                if (!controller.isServer()) {
                    cnx = new SQLConnection(controller.getFileString(), "SQLite", controller.isBinary());
                }
                schema = new SQLSchema();
                tables = schema.getTablesAsTablesView();
                this.currentTable = tables.get(0);
                howMuch.setText(currentTable.get().getHowMuch() + "");
                createTablesView();
                path = controller.getFilePath();

            } catch (Exception ex) {
                //Alerts.error();
            }

        });
    }

    @FXML
    private void onSave(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le répertoire d'enregistrement");
            fileChooser.setInitialDirectory(new File("."));
            File showSaveDialog = fileChooser.showSaveDialog(primaryStage);
            writeObjectInFile(showSaveDialog.getAbsolutePath(), schema);
        } catch (Exception e) {
            Alerts.error();
        }

    }

    @FXML
    private void onOption(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/option.fxml"));
        Parent root1 = (Parent) loader.load();
        OptionController controller = loader.<OptionController>getController();
        // controller.initData(tables);
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root1);
        scene.getStylesheets().add("styles/main.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Configuration");
        primaryStage.show();
    }

    @FXML
    private void onOpen(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le répertoire d'enregistrement");
            fileChooser.setInitialDirectory(new File("."));
            File showOpenDialog = fileChooser.showOpenDialog(primaryStage);

            schema = (SQLSchema) readFileObject(showOpenDialog.getAbsolutePath());
            tables = schema.getTablesAsTablesView();
            tables.forEach(t -> t.updateTableViewInserts());
            currentTable = tables.get(0);
            howMuch.setText(currentTable.get().getHowMuch() + "");
            createTablesView();
        } catch (Exception e) {
            Alerts.error();
        }
    }

    private void onUpdateAttribute(ActionEvent event) {
        tables.forEach(t -> t.updateAttributes());
    }

    private void updateTableConf() {
        this.currentTable.get().setHowMuch(Integer.parseInt(howMuch.getText()));
    }

}
