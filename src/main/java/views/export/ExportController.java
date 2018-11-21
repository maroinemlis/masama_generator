package views.export;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import views.main.Data;
import static views.main.MainController.schema;

/**
 * FXML Controller class
 *
 * @author tamac
 */
public class ExportController implements Initializable {
    
    @FXML
    private JFXRadioButton sql;

    @FXML
    private ToggleGroup exportSave;

    @FXML
    private JFXRadioButton json;

    @FXML
    private JFXRadioButton xml;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton enregistrer;
    
    Path path;
    ArrayList<Data> data;

    @FXML
    void onCancel(ActionEvent event) {

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    void onSave(ActionEvent event) throws Exception {

        String selected;
        if(sql.isSelected()){
            exportOnSql();
        }
        else if(json.isSelected()){
            selected = "json";
        }
        else{
            selected = "db";
        }
        onCancel(event);
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void initData(Path path, ArrayList<Data> data) {
        this.path = path;
        this.data = data;
    }
    
    private void exportOnSql() throws FileNotFoundException, IOException{
        
        String destination = "monNouveauFichier.sql";
        OutputStream output = new FileOutputStream(destination);
        Files.copy(path, output);
        for (Data t : data) {
            String insert = "INSERT INTO " + t.getTableName() + " VALUES (";
            int i=0 ;
            String Type = "";
            for (; i<t.getLines().size() - 1; i++) {
                Type = schema.getTableByName(t.getTableName()).getAttributes().get(i).getDataType();
                if(Type.equals("TEXT")) insert += "'";
                insert += t.getLines().get(i).get();
                if(Type.equals("TEXT")) insert += "'";
                insert +=", ";
            }
            Type = schema.getTableByName(t.getTableName()).getAttributes().get(i).getDataType();
            if(Type.equals("TEXT")) insert += "'";
            insert += t.getLines().get(i).get();
            if(Type.equals("TEXT")) insert += "'";
            insert +=");";
            Files.write(Paths.get(destination), (insert + System.lineSeparator()).getBytes(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }
        
    }

}
