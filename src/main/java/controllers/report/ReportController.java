package controllers.report;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

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
    private JFXTextField queriesTotalNumber;
    @FXML
    private JFXTextField totalTime;

    private ObservableList<QueriesBloc> observablesQueriesBlock = FXCollections.<QueriesBloc>observableArrayList();
    ObservableList<PieChart.Data> pieChartData
            = FXCollections.observableArrayList();
    @FXML
    private JFXTreeTableView<QueriesBloc> blocsTable;
    /*@FXML
    private PieChart pieChart;*/

    @FXML
    private BarChart<String, Number> barChart;
    private JFXComboBox<String> historySimulation;
    private SimulationEvolution smulationEvolution = new SimulationEvolution();
    private SQLExecutionSimulation executionSimulation = new SQLExecutionSimulation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTableView();
        //pieChart.setData(pieChartData);
        Stage st = null;

        blocsTable.setRoot(new RecursiveTreeItem<>(observablesQueriesBlock, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
    }

    @FXML
    private void onAddQuery(ActionEvent event) {
        blocList.getItems().add(query.getText());
    }

    @FXML
    private void onDeleteQuery(ActionEvent event) {
        Integer selectedIndex = blocList.getSelectionModel().getSelectedIndex();
        if (selectedIndex != null) {
            blocList.getItems().remove(selectedIndex);
        } else {
            alert.Alerts.error("Requéte non selectionéee");
        }
    }

    @FXML
    private void onAddBloc(ActionEvent event) {
        QueriesBloc bloc = new QueriesBloc(blocList, rate);
        observablesQueriesBlock.add(bloc);
        executionSimulation.addBloc(bloc);
        alert.Alerts.done("Nouveau bloc a été ajouté");
    }

    public void createTableView() {
        blocsTable.setShowRoot(false);
        ObservableList<String> stylesheets = blocsTable.getStylesheets();
        JFXTreeTableColumn<QueriesBloc, JFXListView<String>> queriesListColumn = new JFXTreeTableColumn<>("Bloc");
        JFXTreeTableColumn<QueriesBloc, String> rateColumn = new JFXTreeTableColumn<>("Pourcentage");
        JFXTreeTableColumn<QueriesBloc, Double> timeColumn = new JFXTreeTableColumn<>("Temps d'éxécution");
        queriesListColumn.setPrefWidth(700);
        rateColumn.setPrefWidth(400);
        timeColumn.setPrefWidth(100);

        queriesListColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("queriesListColumn"));
        rateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("rateColumn"));
        timeColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("timeColumn"));
        blocsTable.getColumns()
                .addAll(queriesListColumn, rateColumn, timeColumn);

        blocsTable.setRoot(new RecursiveTreeItem<>(observablesQueriesBlock, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
    }

    @FXML
    private void onSimulate(ActionEvent event) {
        try {
            executionSimulation.simulate();
            alert.Alerts.done("Simulation est faite");
            fillBarChart(barChart);
        } catch (Exception e) {
            alert.Alerts.error(e.getMessage());

        }
    }

    @FXML
    private void onNewSimulation(ActionEvent event) {
        smulationEvolution.addSQLExecutionSimulation(executionSimulation);
        executionSimulation = new SQLExecutionSimulation();
    }

    @FXML
    private void onResetEvolution(ActionEvent event) {

    }

    public void fillBarChart(BarChart bc) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(
                "Bloc1", "Bloc2", "Bloc3")));
        xAxis.setLabel("Blocs");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Temps");
        bc.setTitle("Temps d'execution");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Temps d'éxécution en ms");
        series1.getData().add(new XYChart.Data<>("Bloc1", 46.0));

        series1.getData().add(new XYChart.Data<>("Bloc2", 57.0));
        series1.getData().add(new XYChart.Data("Bloc3", 33));
        series1.getData().add(new XYChart.Data("Bloc4", 78));
        series1.getData().add(new XYChart.Data("Bloc5", 12));
        bc.getData().add(series1);

    }

}
