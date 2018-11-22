package views.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import db.bean.Attribute;
import db.bean.SQLSchema;
import db.connection.SQLConnection;
import db.models.AttributeModel;
import static db.utils.FileUtil.readFileObject;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import views.connection.ConnectionController;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import views.export.ExportController;

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
    private AttributeModel currentAttribute;
    @FXML
    private HBox drag;
    @FXML
    private JFXTextField howMuch;
    @FXML
    private JFXSlider nullsRate;

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
        for (TableView table : tables) {
            TitledPane titledPane = new TitledPane(table.get().getTableName(), table.getTableView());
            tablesAccordion.getPanes().add(titledPane);
        }
    }

    public void init() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            tablesAccordion.expandedPaneProperty().addListener((ov, old_val, new_val) -> {
                if (new_val != null) {
                    this.currentTable = getTableByName(new_val.getText());
                    refrechInserts();
                    this.currentTable.getTableView().getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
                        this.currentAttribute = n.getValue();
                    });
                }
            });
            new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test2.sql", "SQLite", false);
            schema = new SQLSchema();
            tables = schema.getTablesAsTablesView();
            this.currentTable = tables.get(0);
            createTablesView();
            System.out.println(currentTable.getLines());
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onGenerate(ActionEvent event) {
        try {
            updateTableConf();
            schema.startToGenerateInstances();
            for (TableView t : tables) {
                t.updateTableViewInserts();
            }
        } catch (Exception e) {
            Alerts.error();
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
        controller.initData(tables);
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root1);
        scene.getStylesheets().add("styles/main.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exporter");
        primaryStage.show();
    }

    @FXML
    private void onConnection(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("fxml/connection.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        JFXAlert alert = new JFXAlert();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Connection");
        alert.setContent(root);
        alert.show();
        alert.setOnCloseRequest((e) -> {
            try {
                ConnectionController controller = fxmlLoader.<ConnectionController>getController();
                if (!controller.isServer()) {
                    new SQLConnection(controller.getFileString(), "SQLite", controller.isBinary());
                }
                schema = new SQLSchema();
                tables = schema.getTablesAsTablesView();
                this.currentTable = tables.get(0);
                howMuch.setText(currentTable.get().getHowMuch() + "");
                createTablesView();
                path = controller.getFilePath();
            } catch (Throwable ex) {
                Alerts.error();
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
    private void onOption(ActionEvent event) {
    }

    @FXML
    private void onOpen(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le répertoire d'enregistrement");
            fileChooser.setInitialDirectory(new File("."));
            File showOpenDialog = fileChooser.showOpenDialog(primaryStage);
            this.schema = (SQLSchema) readFileObject(showOpenDialog.getAbsolutePath());
            this.tables = schema.getTablesAsTablesView();
            this.currentTable = tables.get(0);
            refrechInserts();
        } catch (Exception e) {
            Alerts.error();
        }
    }

    @FXML
    private void onUpdateAttribute(ActionEvent event) {
        for (TableView t : tables) {
            t.updateAttributes();
        }
    }

    private void updateTableConf() {
        this.currentTable.get().setHowMuch(Integer.parseInt(howMuch.getText()));

    }
}
