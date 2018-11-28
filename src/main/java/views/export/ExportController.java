package views.export;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import static db.utils.FileUtil.writeObjectInFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static views.main.LuncherApp.primaryStage;
import static views.main.MainController.schema;
import views.main.TableView;

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
    private List<TableView> tables;

    @FXML
    void onCancel(ActionEvent event) {

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onSave(ActionEvent event) throws Exception {

        String selected;
        if (sql.isSelected()) {
            exportOnSql();
        } else if (json.isSelected()) {
            selected = "json";
        } else {
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

    public void initData(List<TableView> tables) {
        this.tables = tables;
    }

    private void exportOnSql() throws FileNotFoundException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le répertoire d'enregistrement");
        fileChooser.setInitialDirectory(new File("."));
        File showSaveDialog = fileChooser.showSaveDialog(primaryStage);
        for (TableView t : tables) {
            List<List<StringProperty>> lines = t.getLines();
            for (List<StringProperty> line : lines) {
                String insert = "INSERT INTO " + t.get().getTableName() + " VALUES (";
                int i = 0;
                for (; i < line.size() - 1; i++) {
                    insert += line.get(i).get() + ", ";
                }
                insert += line.get(i).get() + ");";
                Files.write(Paths.get(showSaveDialog.getAbsolutePath()), (insert + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }

    }

}
