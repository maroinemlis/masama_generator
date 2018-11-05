package views.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author amirouche
 */
public class MainController implements Initializable {

    @FXML
    private Pane idPane;
    @FXML
    private BorderPane idMainBorderPane;
    @FXML
    private Pane idPaneTables;
    @FXML
    private Spinner<Integer> spinerNbrMax;
    @FXML
    private Spinner<Integer> spinerNbrMin;
    @FXML
    private Spinner<Integer> spinerNbrIntegerNulls;
    @FXML
    private Spinner<Integer> spinerNbrTuples;
    @FXML
    private JFXCheckBox checkBoxUniqueValue;
    @FXML
    private VBox vBoxExplorer;
    @FXML
    private Spinner<Integer> spinerNbrIntegerNulls1;
    @FXML
    private JFXComboBox<String> comboBoxValeuOfString;
    @FXML
    private TableView<?> tableContain;
    @FXML
    private TabPane tabPaneDeffirentData;
    @FXML
    private Spinner<?> spinerNbrIntegerNulls11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onOpenNewProject(ActionEvent event) {
    }

    @FXML
    private void onGenerateData(ActionEvent event) {
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");
        tableContain.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
    }

    @FXML
    private void onButtonTest(ActionEvent event) {
        System.out.println("Views.MainClasses.MainFXMLController.onButtonTest()");
    }

}
