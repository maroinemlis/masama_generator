package controllers.option;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import alert.Alerts;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author asma
 */
public class OptionController implements Initializable {

    @FXML
    private void onUpdate(ActionEvent event) {
        try {
            Alerts.done("Configuration réussite");
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
