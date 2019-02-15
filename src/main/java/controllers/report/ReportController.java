package controllers.report;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author abidi asma + Maroine
 */
public class ReportController implements Initializable {

    @FXML
    private TextArea query;
    @FXML
    private JFXListView<String> blocList;
    @FXML
    private JFXSlider rate;
    @FXML
    private JFXTextField totalBlocExecution;
    @FXML
    private JFXTextField totalTime;
    @FXML
    private JFXTreeTableView<QueriesBloc> blocsTable;
    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart chart;
    @FXML
    private JFXComboBox<String> historySimulation;

    private SimulationEvolution smulationEvolution;
    private SQLExecutionSimulation executionSimulation;
    @FXML
    private JFXTabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historySimulation.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
            int i = historySimulation.getSelectionModel().getSelectedIndex();
            executionSimulation = smulationEvolution.getExecutionSimulations().get(i);
            totalBlocExecution.setText(executionSimulation.getTotalBlocExecution() + "");
            executionSimulation.show();
        });
        smulationEvolution = new SimulationEvolution(chart);
        executionSimulation = new SQLExecutionSimulation(blocsTable, pieChart);
        createTableView();
    }

    @FXML
    private void onAddQuery(ActionEvent event) {
        blocList.getItems().add(query.getText());
    }

    @FXML
    private void onDeleteQuery(ActionEvent event) {
        int selectedIndex = blocList.getSelectionModel().getSelectedIndex();
        try {
            blocList.getItems().remove(selectedIndex);
            blocList.refresh();
        } catch (Exception e) {
            alert.Alerts.error("Requéte non selectionéee");
        }
    }

    @FXML
    private void onAddBloc(ActionEvent event) {
        QueriesBloc bloc = new QueriesBloc(blocList, rate);
        executionSimulation.addBloc(bloc);
        alert.Alerts.done("Nouveau bloc a été ajouté");
    }

    public void createTableView() {
        blocsTable.setShowRoot(false);
        ObservableList<String> stylesheets = blocsTable.getStylesheets();
        JFXTreeTableColumn<QueriesBloc, JFXCheckBox> updateColumn = new JFXTreeTableColumn<>("");
        JFXTreeTableColumn<QueriesBloc, JFXListView<String>> queriesListColumn = new JFXTreeTableColumn<>("Bloc");
        JFXTreeTableColumn<QueriesBloc, String> rateColumn = new JFXTreeTableColumn<>("Pourcentage");
        JFXTreeTableColumn<QueriesBloc, Double> timeColumn = new JFXTreeTableColumn<>("Temps d'éxécution");
        updateColumn.setPrefWidth(50);
        queriesListColumn.setPrefWidth(600);
        rateColumn.setPrefWidth(300);
        timeColumn.setPrefWidth(100);

        queriesListColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("queriesListColumn"));
        rateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("rateColumn"));
        timeColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("timeColumn"));
        updateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("update"));
        blocsTable.getColumns()
                .addAll(updateColumn, queriesListColumn, rateColumn, timeColumn);

    }

    @FXML
    private void onSimulate(ActionEvent event) {
        try {
            executionSimulation.setTotalBlocExecution(Long.parseLong(totalBlocExecution.getText()));
            executionSimulation.simulate();
            totalTime.setText(executionSimulation.getTotalTime() + "");
            tabPane.getSelectionModel().selectLast();
        } catch (Exception e) {
            alert.Alerts.error("err");
            System.err.println("err :" + e.getMessage());
        }
    }

    @FXML
    private void onNewSimulation(ActionEvent event) {
        smulationEvolution.addSQLExecutionSimulation(executionSimulation);
        historySimulation.getItems().add("Simulation " + smulationEvolution.getExecutionSimulations().size());
        alert.Alerts.done("Simulation est faite");
        executionSimulation = new SQLExecutionSimulation(blocsTable, pieChart);
    }

    @FXML
    private void onResetEvolution(ActionEvent event) {
        executionSimulation.reset();
    }

}
