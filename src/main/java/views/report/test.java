package views.report;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import db.bean.SQLSchema;
import static db.utils.FileUtil.readFileObject;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import views.main.Alerts;
import static views.main.LuncherApp.primaryStage;
import static views.main.MainController.schema;

/**
 * FXML Controller class
 *
 * @author abidi asma
 */
/*public class ReportController implements Initializable {

    @FXML
    private Label status;

    @FXML
    private TextField login;

    @FXML
    private TextField pw;

    @FXML
    private TextField text;

    @FXML
    private AnchorPane anchId;

    @FXML
    private MenuItem mItemBDD;

    @FXML
    private Label fichierBDD;

    @FXML
    private TextArea selectId;

    @FXML
    private TextArea updateId;

    @FXML
    private TextArea deleteId;

    @FXML
    private TextField nbrRequete;

    @FXML
    private TextField selectPr;

    @FXML
    private TextField updatePr;

    @FXML
    private TextField deletePr;

    @FXML
    private Label selectLabel;

    @FXML
    private Label updateLabel;

    @FXML
    private BarChart<?, ?> estimationChart;

    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;

    private String pathFile;
    private static Connection con;
    XYChart.Series set1 = new XYChart.Series<>();

    //Create the connction sqlite/mysql...
    private void getConnection() throws ClassNotFoundException, SQLException {
        String[] path = pathFile.split("\\.");
        if (path[1].equals("sqlite")) {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + pathFile);
        }
    }

    public void chooseBddFile(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir la BDD");
            fileChooser.setInitialDirectory(new File("."));
            File showOpenDialog = fileChooser.showOpenDialog(null);
            fichierBDD.setText(showOpenDialog.getName());
            pathFile = showOpenDialog.getAbsolutePath();
        } catch (Exception e) {
            Alerts.error();
        }
    }

    public void estimerRequest(ActionEvent event) {
        Integer nbRequete = Integer.parseInt(nbrRequete.getText().toString());
        if (!"".equals(selectId.getText().toString())) {
            selectMethod(nbRequete);
        }
        if (!"".equals(updateId.getText().toString())) {
            updateMethod(nbRequete);
        }
    }

    public void updateMethod(Integer nbRequete) {
        Long start;
        Long totalTime;
        ArrayList<Long> tabTimeUpdate = new ArrayList();
        Long heure, min, sec, ms;
        Integer updatePrc;
        Integer valUpPourc;
        start = System.currentTimeMillis();
        try {
            displayUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
        }
        totalTime = System.currentTimeMillis() - start;
        updatePrc = Integer.parseInt(updatePr.getText().toString());
        valUpPourc = (nbRequete * updatePrc) / 100;
        totalTime = totalTime * valUpPourc;
        tabTimeUpdate = calculTimeHMSM(totalTime);
        heure = tabTimeUpdate.get(0);
        min = tabTimeUpdate.get(1);
        sec = tabTimeUpdate.get(2);
        ms = tabTimeUpdate.get(3);
        updateLabel.setText(heure.toString() + " heure(s), " + min.toString() + " minute(s), " + sec.toString() + " seconde(s) et " + ms.toString() + " milliseconde(s) ");
        set1.getData().add(new XYChart.Data("UPDATE", totalTime));
    }

    public void selectMethod(Integer nbRequete) {
        Long start;
        Integer selectPrc;
        Integer valPourc;
        Long totalTime;
        ArrayList<Long> tabTimeSelect = new ArrayList();
        Long heure, min, sec, ms;
        start = System.currentTimeMillis();
        try {
            displaySelect();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
        }
        totalTime = System.currentTimeMillis() - start;
        selectPrc = Integer.parseInt(selectPr.getText().toString());
        valPourc = (nbRequete * selectPrc) / 100;
        totalTime = totalTime * valPourc;
        tabTimeSelect = calculTimeHMSM(totalTime);
        heure = tabTimeSelect.get(0);
        min = tabTimeSelect.get(1);
        sec = tabTimeSelect.get(2);
        ms = tabTimeSelect.get(3);
        selectLabel.setText(heure.toString() + " heure(s), " + min.toString() + " minute(s), " + sec.toString() + " seconde(s) et " + ms.toString() + " milliseconde(s) ");
        set1.getData().add(new XYChart.Data("SELECT", totalTime));
        set1.getData().add(new XYChart.Data("delete", totalTime));
        set1.getData().add(new XYChart.Data("insert", totalTime));
    }

    public void displaySelect() throws ClassNotFoundException, SQLException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        state.executeQuery(selectId.getText().toString());

    }

    public void displayUpdate() throws ClassNotFoundException, SQLException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        state.executeUpdate(updateId.getText().toString());
    }

    public ArrayList<Long> calculTimeHMSM(Long totalTime) {
        int val;
        Long heure;
        Long rest;
        Long min;
        Long sec;
        Long ms;
        ArrayList<Long> tabTime = new ArrayList<>();
        val = 1000 * 3600;
        heure = totalTime / val;
        rest = totalTime - heure * val;
        val /= 60;
        min = rest / val;
        rest = rest - min * val;
        val /= 60;
        sec = rest / val;
        ms = rest - sec * val;
        tabTime.add(heure);
        tabTime.add(min);
        tabTime.add(sec);
        tabTime.add(ms);
        return tabTime;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estimationChart.getData().addAll(set1);
    }

}
*/
