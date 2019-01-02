/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.connection;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import static views.main.LuncherApp.primaryStage;

/**
 * FXML Controller class
 *
 * @author tamac
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

    private SingleSelectionModel<String> driverString;
    private String fileString;
    private static Path filePath;
    @FXML
    private JFXTextField path;
    private JFXAlert alert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        driver.getItems().addAll("SQLite", "mySQL", "Oracle", "SQLServer", "Derby");
        cnxType.selectedToggleProperty().addListener(((observable, oldValue, new_toggle) -> {
            if (r1.isSelected()) {
                auth.setVisible(true);
                file.setVisible(false);
            } else if (r2.isSelected()) {
                auth.setVisible(false);
                file.setVisible(true);

            } else {
                auth.setVisible(false);
                file.setVisible(true);

            }
        }));
    }

    @FXML
    private void onChose(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        if (r2.isSelected()) {
            fileChooser.setTitle("Choisir un fichier sql binaire");

        } else if (r3.isSelected()) {
            fileChooser.setTitle("Choisir un script sql");
        }
        File fileUrl = fileChooser.showOpenDialog(primaryStage);
        this.fileString = fileUrl.getAbsolutePath();
        filePath = fileUrl.toPath();
        path.setText(fileString);
    }

    @FXML
    private void onConnect(ActionEvent event) {
        //this.driverString = driver.selectionModelProperty().getValue();
        alert.close();
    }

    public String getFileString() {
        return fileString;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getCnxType() {
        return driver.getSelectionModel().getSelectedItem();
    }

    public boolean getIsFile() {
        return !r1.isSelected();
    }

    public boolean isBinary() {
        return r2.isSelected();
    }

    public boolean isServer() {
        return r1.isSelected();
    }

    public String getURL() {
        return url.getText();
    }

    public String getUser() {
        return user.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void setAlert(JFXAlert alert) {
        this.alert = alert;
    }
}
