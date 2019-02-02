package controllers.export;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import beans.SQLSchema;
import beans.Table;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import connection.SQLConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FXML Controller class
 *
 * @author tamac
 */
public class ExportController implements Initializable {

    @FXML
    private JFXRadioButton sql;
    @FXML
    private JFXRadioButton json;
    @FXML
    private JFXRadioButton xml;

    @FXML
    private ToggleGroup exportSave;

    @FXML
    private JFXButton save;
    private Path path;

    void close() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le répertoire d'enregistrement");
        fileChooser.setInitialDirectory(new File("."));
        File showSaveDialog = fileChooser.showSaveDialog(save.getScene().getWindow());
        this.path = showSaveDialog.toPath();
        if (sql.isSelected()) {
            this.path = Paths.get(path.toAbsolutePath().toString() + ".sql");
            exportOnSql();
        } else if (json.isSelected()) {
            this.path = Paths.get(path.toAbsolutePath().toString() + ".json");
            exportOnJson();
        } else {
            this.path = Paths.get(path.toAbsolutePath().toString() + ".xml");
            exportOnXml();
        }
        close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void exportOnSql() throws FileNotFoundException, IOException, Exception {
        List<Table> tables = SQLSchema.getInstance().getTables();
        for (Table table : tables) {
            for (int j = 0; j < table.getHowMuch(); j++) {
                int i = 0;
                StringBuilder insert = new StringBuilder("INSERT INTO " + table.getTableName() + " VALUES(");
                for (i = 0; i < table.getAttributes().size() - 1; i++) {
                    insert.append(table.getAttributes().get(i).getInstances().get(j));
                    insert.append(", ");
                }
                insert.append(table.getAttributes().get(i).getInstances().get(j));
                insert.append(");\n");
                Files.write(path, insert.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }
    }

    private void exportOnXml() throws Exception {
        List<Table> tables = SQLSchema.getInstance().getTables();
        for (Table table : tables) {
            for (int j = 0; j < table.getHowMuch(); j++) {
                int i = 0;
                String insert = null;
                Files.write(path, ("<" + table.getTableName() + ">\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                for (i = 0; i < table.getAttributes().size(); i++) {
                    insert = "\t<" + table.getAttributes().get(i).getName() + "> ";
                    insert += table.getAttributes().get(i).getInstances().get(j);
                    insert += "<" + table.getAttributes().get(i).getName() + "/>\n";
                    Files.write(path, insert.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                Files.write(path, ("<" + table.getTableName() + "/>\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }
    }

    private void exportOnJson() throws Exception {
        List<Table> tables = SQLSchema.getInstance().getTables();
        Files.write(path, "{\n".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        int k;
        for (k = 0; k < tables.size(); k++) {
            Table table = tables.get(k);
            String insert = "  \"" + table.getTableName() + "\": [\n";
            Files.write(path, insert.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            for (int j = 0; j < table.getHowMuch(); j++) {
                int i = 0;
                insert = "\t{\n";
                for (i = 0; i < table.getAttributes().size() - 1; i++) {
                    insert += "\t  \"" + table.getAttributes().get(i).getName() + "\": ";
                    insert += table.getAttributes().get(i).getInstances().get(j).replace("'", "\"") + ",\n";
                }
                insert += "\t  \"" + table.getAttributes().get(i).getName() + "\": ";
                insert += table.getAttributes().get(i).getInstances().get(j).replace("'", "\"") + "\n\t}\n  ]";
                if (k != tables.size() - 1) {
                    insert += ",";
                }
                Files.write(path, insert.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }
        Files.write(path, "}".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

    }

}
