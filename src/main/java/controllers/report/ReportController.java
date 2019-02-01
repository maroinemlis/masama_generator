package controllers.report;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import connection.SQLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author abidi asma
 */
public class ReportController implements Initializable {

    @FXML
    private AnchorPane anchId;

    @FXML
    private TextArea deleteId;

    @FXML
    private TextField nbrRequete;

    @FXML
    private BarChart<?, ?> estimationChart;

    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;

    @FXML
    private VBox pane_main_grid;

    @FXML
    private TextField prcIDFText;

    @FXML
    private TextArea requetTextID;

    @FXML
    private TableView<RequestEstimation> tableViewID;
    @FXML
    private TableColumn<RequestEstimation, String> prcID;
    @FXML
    private TableColumn<RequestEstimation, String> requeteID;
    @FXML
    private TableColumn<RequestEstimation, String> timeExecID;

    @FXML
    private AnchorPane AnchorPane1;

    XYChart.Series set1 = new XYChart.Series<>();
    SQLConnection cnx;
    Long timeExec;
    ArrayList<Integer> PrcSelect = new ArrayList<>();
    ArrayList<String> ListSelect = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estimationChart.getData().addAll(set1);

        requeteID.setCellValueFactory(new PropertyValueFactory<>("request"));
        prcID.setCellValueFactory(new PropertyValueFactory<>("prc"));
        timeExecID.setCellValueFactory(new PropertyValueFactory<>("timeEstimation"));
        tableViewID.setItems(getEstimationList());
    }

    ObservableList<RequestEstimation> getEstimationList() {
        ObservableList<RequestEstimation> RE = FXCollections.observableArrayList();
        return RE;
    }

    public void initData(SQLConnection cnx) {
        this.cnx = cnx;
    }

    public void selectButton() {

        Integer nbRequete = Integer.parseInt(nbrRequete.getText().toString());
        String pref = requetTextID.getText().toString().substring(0, 6);

        if (pref.equals("SELECT") || pref.equals("select")) {
            timeExec = selectMethod(nbRequete);
        }
        if (pref.equals("UPDATE") || pref.equals("update") || pref.equals("delete") || pref.equals("DELETE")) {
            timeExec = updateMethod(nbRequete);
        }

        RequestEstimation estimation = new RequestEstimation();
        estimation.setRequest(requetTextID.getText());
        estimation.setPrc(prcIDFText.getText() + " %");
        if (timeExec != null) {
            estimation.setTimeEstimation(timeExec.toString() + " (ms)");
        } else {
            estimation.setTimeEstimation("Erreur d'estimaton!");
        }
        tableViewID.getItems().add(estimation);
        requetTextID.clear();
        prcIDFText.clear();

    }

    public Long updateMethod(Integer nbRequete) {
        Long start = null, end = null;
        Integer selectPrc;
        Integer valPourc;
        Long totalTime;
        ArrayList<Long> tabTimeSelect = new ArrayList();
        // Long heure, min, sec, ms;

        selectPrc = Integer.parseInt(prcIDFText.getText().toString());
        valPourc = (nbRequete * selectPrc) / 100;

        try {
            start = System.currentTimeMillis();
            sqlUpdate(valPourc);
            end = System.currentTimeMillis();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
        }

        totalTime = end - start;

        tabTimeSelect = calculTimeHMSM(totalTime);
        //heure = tabTimeSelect.get(0);
//        min = tabTimeSelect.get(1);
//        sec = tabTimeSelect.get(2);
//        ms = tabTimeSelect.get(3);
        // updateLabel.setText(heure.toString() + " heure(s), " + min.toString() + " minute(s), " + sec.toString() + " seconde(s) et " + ms.toString() + " milliseconde(s) ");
        set1.getData().add(new XYChart.Data("SELECT", totalTime));
        set1.getData().add(new XYChart.Data("delete", totalTime));
        set1.getData().add(new XYChart.Data("insert", totalTime));

        return totalTime;
    }

    public Long selectMethod(Integer nbRequete) {
        Long start = null, end = null;
        Integer selectPrc;
        Integer valPourc;
        Long totalTime;
        ArrayList<Long> tabTimeSelect = new ArrayList();
        Long heure, min, sec, ms;

        selectPrc = Integer.parseInt(prcIDFText.getText().toString());
        valPourc = (nbRequete * selectPrc) / 100;

        try {
            start = System.currentTimeMillis();
            sqlSelect(valPourc);
            end = System.currentTimeMillis();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
        }

        totalTime = end - start;

        tabTimeSelect = calculTimeHMSM(totalTime);
//        heure = tabTimeSelect.get(0);
//        min = tabTimeSelect.get(1);
//        sec = tabTimeSelect.get(2);
//        ms = tabTimeSelect.get(3);
        //selectLabel.setText(heure.toString() + " heure(s), " + min.toString() + " minute(s), " + sec.toString() + " seconde(s) et " + ms.toString() + " milliseconde(s) ");
        set1.getData().add(new XYChart.Data("SELECT", totalTime));
        set1.getData().add(new XYChart.Data("delete", totalTime));
        set1.getData().add(new XYChart.Data("insert", totalTime));
        return totalTime;
    }

    public void sqlSelect(Integer valPourc) throws ClassNotFoundException, SQLException {

        cnx.displaySelect(requetTextID.getText().toString(), valPourc);
    }

    public void sqlUpdate(Integer valPourc) throws ClassNotFoundException, SQLException {
        cnx.displayUpdate(requetTextID.getText().toString(), valPourc);
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

}
