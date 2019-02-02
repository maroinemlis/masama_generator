package controllers.option;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import beans.SQLSchema;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * FXML Controller class
 *
 * @author asma
 */
public class OptionController implements Initializable {

    @FXML
    private JFXButton onConfig;
    @FXML
    private JFXTextField nbLigne;
    @FXML
    private JFXTextField pNull;
    @FXML
    private JFXTextField vMin;
    @FXML
    private JFXTextField vMax;
    @FXML
    private JFXSlider longMin;
    @FXML
    private JFXSlider longMax;
    @FXML
    private JFXTextField expR;
    @FXML
    private JFXDatePicker dateMin;
    @FXML
    private JFXDatePicker dateMax;

    /**
     * Initializes the controller class.
     */
    @FXML
    void onConfig(ActionEvent event) throws IOException {
        saveConfig();
        Stage stage = (Stage) nbLigne.getScene().getWindow();
        stage.close();
    }

    public void saveConfig() {
        JSONObject obj = new JSONObject();
        obj.put("nbLigne", nbLigne.getText());
        obj.put("pNull", pNull.getText());
        obj.put("vMin", vMin.getText());
        obj.put("vMax", vMax.getText());
        obj.put("longMin", longMin.getValue());
        obj.put("longMax", longMax.getValue());
        obj.put("expR", expR.getText());
        obj.put("dateMin", dateMin.getValue().toString());
        obj.put("dateMax", dateMax.getValue().toString());
        try (FileWriter file = new FileWriter("masama_config.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SQLSchema.getInstance().getTables().forEach(t -> t.setHowMuch(Integer.parseInt((String) obj.get("nbLigne"))));
        SQLSchema.getInstance().getTables().forEach(t
                -> t.getAttributes().forEach(at -> {
                    switch (at.getDataType()) {
                        case "TEXT":
                            at.getDataFaker().setFromToNullsRate(toString().valueOf(obj.get("longMin")), toString().valueOf(obj.get("longMax")), Integer.parseInt((String) obj.get("pNull")));
                            break;
                        case "DATE":
                            at.getDataFaker().setFromToNullsRate(toString().valueOf(obj.get("dateMin")), toString().valueOf(obj.get("dateMax")), Integer.parseInt((String) obj.get("pNull")));
                            break;
                        case "INT":
                        case "INTEGER":
                        case "DOUBLE":
                        case "FLOAT":
                            at.getDataFaker().setFromToNullsRate(toString().valueOf(obj.get("vMin")), toString().valueOf(obj.get("vMax")), Integer.parseInt((String) obj.get("pNull")));
                            break;
                    }
                }));
    }

    public void readConfig() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader("config.json"));
            JSONObject jsonObject = (JSONObject) obj;

            String nbLigne = (String) jsonObject.get("nbLigne");
            String pNull = (String) jsonObject.get("pNull");
            String vMin = (String) jsonObject.get("vMin");
            String vMax = (String) jsonObject.get("vMax");
            double longMin = (double) jsonObject.get("longMin");
            double longMax = (double) jsonObject.get("longMax");
            String expR = (String) jsonObject.get("expR");
            String dateMin = (String) jsonObject.get("dateMin");
            String dateMax = (String) jsonObject.get("dateMax");

            this.nbLigne.setText(nbLigne);
            this.pNull.setText(pNull);
            this.vMin.setText(vMin);
            this.vMax.setText(vMax);
            this.longMin.setValue(longMin);
            this.longMax.setValue(longMax);
            this.expR.setText(expR);
            String[] TdateMax = dateMax.split("-");
            this.dateMax.setValue(LocalDate.of(Integer.parseInt(TdateMax[0]), Integer.parseInt(TdateMax[1]), Integer.parseInt(TdateMax[2])));
            String[] TdateMin = dateMin.split("-");
            this.dateMin.setValue(LocalDate.of(Integer.parseInt(TdateMin[0]), Integer.parseInt(TdateMin[1]), Integer.parseInt(TdateMin[2])));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // force the field to be numeric only
    public void justNumber(JFXTextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readConfig();
        justNumber(nbLigne);
        justNumber(pNull);
        justNumber(vMax);
        justNumber(vMin);
    }

}
