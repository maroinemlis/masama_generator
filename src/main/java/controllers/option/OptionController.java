package controllers.option;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import alert.Alerts;
import beans.Attribute;
import beans.SQLSchema;
import beans.Table;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author asma
 */
public class OptionController implements Initializable {

    @FXML
    private JFXTextField howMuch;
    @FXML
    private JFXTextField intFrom;
    @FXML
    private JFXTextField intTo;
    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private JFXDatePicker dateTo;
    @FXML
    private JFXSlider textFrom;
    @FXML
    private JFXSlider textTo;
    @FXML
    private JFXSlider nullsRate;

    @FXML
    private void onUpdate(ActionEvent event) {
        try {
            List<Table> tables = SQLSchema.getInstance().getTables();
            for (Table t : tables) {
                t.setHowMuch(Integer.parseInt(howMuch.getText()));
                t.setNullsRate(nullsRate.getValue());
                for (Attribute a : t.getAttributes()) {
                    switch (a.getDataType()) {
                        case "TEXT":
                            a.getDataFaker().setFrom("" + (int) textFrom.getValue());
                            a.getDataFaker().setTo("" + (int) textTo.getValue());
                            break;
                        case "DATE":
                            a.getDataFaker().setFrom(dateFrom.getValue().toString());
                            a.getDataFaker().setTo(dateTo.getValue().toString());
                            break;
                        case "INT":
                        case "NUMERIC":
                        case "INTEGER":
                            a.getDataFaker().setFrom(intFrom.getText());
                            a.getDataFaker().setTo(intTo.getText());
                            break;
                        case "DOUBLE":
                        case "FLOAT":
                        case "REAL":
                            a.getDataFaker().setFrom(intFrom.getText());
                            a.getDataFaker().setTo(intTo.getText());
                            break;
                    }
                }
            }
            Alerts.done("Configuration réussite");
        } catch (Exception e) {
            Alerts.error(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        howMuch.textProperty().addListener((ob, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                howMuch.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });
        intFrom.textProperty().addListener((ob, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                howMuch.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });
        intTo.textProperty().addListener((ob, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                howMuch.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });
    }

}
