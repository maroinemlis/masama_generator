/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.connection;

import alert.Alerts;
import beans.SQLSchema;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import connection.SQLConnection;
import controllers.main.LuncherApp;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Maryem
 */
public class ConnectionController implements Initializable {

    @FXML
    private ToggleGroup cnxType;
    @FXML
    private JFXComboBox<String> driver;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXRadioButton r1;
    @FXML
    private JFXRadioButton r2;
    @FXML
    private JFXRadioButton r3;
    @FXML
    private HBox file;
    @FXML
    private AnchorPane auth;
    @FXML
    private JFXTextField url;
    @FXML
    private JFXTextField path;

    /**
     * Initialize the connection Setting to the database
     *
     * @param url link of database
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        driver.getItems().setAll("SQLite", "Derby");
        driver.getSelectionModel().select(0);
        auth.setVisible(false);
        r3.selectedProperty().set(true);
        cnxType.selectedToggleProperty().addListener(((observable, oldValue, new_toggle) -> {
            if (r1.isSelected()) {
                driver.getItems().setAll("MySQL", "Oracle", "SQLServer");
                driver.getSelectionModel().select(0);
                auth.setVisible(true);
                file.setVisible(false);
            } else if (r2.isSelected()) {
                driver.getItems().setAll("SQLite", "MySQL", "Oracle", "SQLServer", "Derby");
                driver.getSelectionModel().select(0);
                auth.setVisible(false);
                file.setVisible(true);
            } else {
                driver.getItems().setAll("SQLite", "Derby");
                driver.getSelectionModel().select(0);
                auth.setVisible(false);
                file.setVisible(true);
            }
        }));
    }

    /**
     * Open the Chooser to select the sql File
     *
     * @param event
     */
    @FXML
    private void onChose(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            if (r2.isSelected()) {
                fileChooser.setTitle("Choisir un fichier SQL binaire");
            } else if (r3.isSelected()) {
                fileChooser.setTitle("Choisir un script SQL");
            }
            File fileUrl = fileChooser.showOpenDialog(LuncherApp.getPrimaryStage());
            path.setText(fileUrl.getAbsolutePath());
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }

    }

    /**
     * Initialize the Connection.
     *
     * @param event
     */
    @FXML
    private void onConnect(ActionEvent event) {
        SQLSchema.setInstance(null);
        try {
            if (r1.isSelected()) {
                SQLConnection.getInstance().connect(url.getText(), user.getText(), password.getText(), getCnxType());
            }
            if (r2.isSelected()) {
                SQLConnection.getInstance().connect(path.getText(), getCnxType(), true);
            } else if (r3.isSelected()) {
                SQLConnection.getInstance().connect(path.getText(), getCnxType(), false);
            }
            SQLSchema.getInstance().constructSchema();
            Alerts.done("Connexion réussie");
        } catch (Exception e) {
            System.out.println(e);
            Alerts.error("Un probléme de connexion");
        }
    }

    /**
     * Return the type of Connection.
     *
     * @return
     */
    private String getCnxType() {
        return driver.getSelectionModel().getSelectedItem();
    }
}
