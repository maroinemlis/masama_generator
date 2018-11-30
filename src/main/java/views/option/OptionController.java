package views.option;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
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

        //*********************************************Write object into file JSON**********************************************************
        HashMap map = new HashMap();
        JSONObject obj = new JSONObject();

        obj.put("nbLigne", nbLigne.getText());
        map.put(nbLigne, nbLigne.getText());

        obj.put("pNull", pNull.getText());
        map.put(pNull, pNull.getText());

        obj.put("vMin", vMin.getText());
        map.put(vMin, vMin.getText());

        obj.put("vMax", vMax.getText());
        map.put(vMax, vMax.getText());

        obj.put("longMin", longMin.getValue());
        map.put(longMin, longMin.getValue());

        obj.put("longMax", longMax.getValue());
        map.put(longMax, longMax.getValue());

        obj.put("expR", expR.getText());
        map.put(expR, expR.getText());

//        obj.put("dateMin", dateMin.getValue().toString());
//        map.put(dateMin, dateMin.getValue().toString());
//
//        obj.put("dateMax", dateMax.getValue().toString());
//        map.put(dateMax, dateMax.getValue().toString());
        System.out.println(map);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter("C:\\Users\\abidi asma\\Desktop\\ProjetLongUPEC\\Projet\\masama_generator\\config.json")) {
            file.write(obj.toJSONString());
//            System.out.println("Successfully Copied JSON Object to File...");
//            System.out.println("\nJSON Object: " + obj);
        }

        //*********************************************Read object from file JSON**********************************************************
        JSONParser parser = new JSONParser();
        try {

            Object objpARSER = parser.parse(new FileReader("C:\\Users\\abidi asma\\Desktop\\ProjetLongUPEC\\Projet\\masama_generator\\config.json"));

            JSONObject jsonObject = (JSONObject) obj;

            Object nbLigne = jsonObject.get("nbLigne");
            Object pNull = jsonObject.get("pNull");
            Object vMin = jsonObject.get("vMin");
            Object vMax = jsonObject.get("vMax");
            Object longMin = jsonObject.get("longMin");
            Object longMax = jsonObject.get("longMax");
            Object expR = jsonObject.get("expR");

//            Object dateMin = jsonObject.get("dateMin").toString();
//
//            Object dateMax = jsonObject.get("dateMax");
//            System.out.println("nbLigne: " + nbLigne);
//            System.out.println("pNull: " + pNull);
//            System.out.println("vMin: " + vMin);
//            System.out.println("vMax: " + vMax);
//            System.out.println("longMin: " + longMin);
//            System.out.println("longMax: " + longMax);
//            System.out.println("expR: " + expR);
//            System.out.println("dateMin: " + dateMin);
//            System.out.println("dateMax: " + dateMax);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
