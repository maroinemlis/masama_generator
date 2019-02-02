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
import static controllers.main.LuncherApp.primaryStage;
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
 * @author Maroine
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        driver.getItems().setAll("SQLite", "Derby");
        driver.getSelectionModel().select(0);
        auth.setVisible(false);
        r3.selectedProperty().set(true);
        cnxType.selectedToggleProperty().addListener(((observable, oldValue, new_toggle) -> {
            if (r1.isSelected()) {
                driver.getItems().setAll("MySQL", "Oracle", "SQLServer");
                auth.setVisible(true);
                file.setVisible(false);
            } else if (r2.isSelected()) {
                driver.getItems().setAll("SQLite", "MySQL", "Oracle", "SQLServer", "Derby");
                auth.setVisible(false);
                file.setVisible(true);
            } else {
                driver.getItems().setAll("SQLite", "Derby");
                auth.setVisible(false);
                file.setVisible(true);
            }
        }));
    }

    @FXML
    private void onChose(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            if (r2.isSelected()) {
                fileChooser.setTitle("Choisir un fichier SQL binaire");
            } else if (r3.isSelected()) {
                fileChooser.setTitle("Choisir un script SQL");
            }
            File fileUrl = fileChooser.showOpenDialog(primaryStage);
            path.setText(fileUrl.getAbsolutePath());
        } catch (Exception e) {
            Alerts.error(e);
        }

    }

    @FXML
    private void onConnect(ActionEvent event) {
        SQLSchema.setInstance(null);
        try {
            if (r1.isSelected()) {
                SQLConnection.getInstance().connect(url.getText(), user.getText(), password.getText(), getCnxType());
            } else {
                SQLConnection.getInstance().connect(path.getText(), getCnxType(), r2.isSelected());
            }
            SQLSchema.getInstance().constructSchema();

        } catch (Exception e) {
            Alerts.error(e);
        }
    }

    private String getCnxType() {
        return driver.getSelectionModel().getSelectedItem();
    }
}
