/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sa3d
 */
public class SaveDialogueController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXRadioButton sql;

    @FXML
    private ToggleGroup selected;

    @FXML
    private JFXRadioButton json;

    @FXML
    private JFXRadioButton db;
    
    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton enregistrer;
    
    @FXML
    void onCancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        String selected;
        if(sql.isSelected()){
            selected = "sql";
        }
        else if(json.isSelected()){
            selected = "json";
        }
        else{
            selected = "db";
        }
        System.out.println(selected);
        onCancel(event);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
