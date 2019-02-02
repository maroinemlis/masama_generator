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
                StringBuilder insert = new StringBuilder("INSERT INTO" + table.getTableName() + "VALUES(");
                for (i = 0; i < table.getAttributes().size() - 1; i++) {
                    insert.append(table.getAttributes().get(i).getInstances().get(j));
                    insert.append(", ");
                }
                insert.append(table.getAttributes().get(i).getInstances().get(j));
                insert.append(");");
                Files.write(path, insert.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }
    }

    private void exportOnXml() throws Exception {
        List<Table> tables = SQLSchema.getInstance().getTables();
        for (Table table : tables) {
            Files.write(path, ("<" + table.getTableName() + ">\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            for (int j = 0; j < table.getHowMuch(); j++) {
                int i = 0;
                String insert = null;
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
        for (Table table : tables) {
            String insert = "\t\"" + table.getTableName() + "\" : {\n";
            Files.write(path, insert.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            for (int j = 0; j < table.getHowMuch(); j++) {
                int i = 0;
                for (i = 0; i < table.getAttributes().size() - 1; i++) {
                    insert = "\t\t\"" + table.getAttributes().get(i).getName() + "\" : ";
                    insert += table.getAttributes().get(i).getInstances().get(j).replace("'", "\"") + ",\n";
                    Files.write(path, insert.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                insert = "\t\t\"" + table.getAttributes().get(i).getName() + "\" : ";
                insert += table.getAttributes().get(i).getInstances().get(j).replace("'", "\"") + "\n\t\t}\n}";
                Files.write(path, insert.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        }
        Files.write(path, "}".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

    }

}
