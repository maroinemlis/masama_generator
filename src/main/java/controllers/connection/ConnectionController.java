/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.connection;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 *
 * @author acer
 */
public class ConnexionController implements Initializable {

    @FXML
    private VBox typeCnx;
    @FXML
    private Label labelCnx;
    @FXML
    private JFXRadioButton r1;
    @FXML
    private ToggleGroup group;
    @FXML
    private JFXRadioButton r2;
    @FXML
    private JFXRadioButton r3;
    @FXML
    private VBox container;
    @FXML
    private JFXButton cnx;

    @FXML
    private void onConnect(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent roott = null;
        try {
            roott = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/authentification.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        container.getChildren().clear();
        container.getChildren().add(roott);
        group.selectedToggleProperty().addListener(((observable, oldValue, new_toggle) -> {
            Parent root = null;
            try {
                if (r1.isSelected()) {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/authentification.fxml"));

                } else if (r2.isSelected()) {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/fichierDB.fxml"));

                } else if (r3.isSelected()) {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/fichierSQL.fxml"));

                }
            } catch (IOException ex) {
                Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            container.getChildren().clear();
            container.getChildren().add(root);
        }));

    }

    public boolean isServer() {
        return r1.isSelected();
    }

}
