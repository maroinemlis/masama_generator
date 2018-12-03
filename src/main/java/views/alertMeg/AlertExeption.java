/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.alertMeg;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author amirouche
 */
public class AlertExeption {

    String message;

    public AlertExeption(String message) {
        this.message = message;
    }

    public void showStandarAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error : " + message);
        alert.setContentText(null);

        alert.showAndWait();
    }

}
