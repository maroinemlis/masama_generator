package controllers.report;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import beans.SQLSchema;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import controllers.helper.HelperControllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Asma
 * @author Maroine
 */
public class ReportController implements Initializable {

    private boolean open = false;
    private SimulationEvolution smulationEvolution;
    private SQLExecutionSimulation executionSimulation;
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
    private AreaChart<String, Number> chart;
    @FXML
    private JFXComboBox<String> historySimulation;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Label pes;

    /*Variables */
    @FXML
    private JFXTextField variableName;
    @FXML
    private JFXComboBox referenceCombo;
    @FXML
    private JFXComboBox attributeCombo;
    @FXML
    private JFXSlider existanceRate;
    @FXML
    private JFXTreeTableView<GenericVariable> variablesTable;

    @FXML
    private Pane indexPane;

    @FXML
    private void onCreateVariable(ActionEvent event) {
        GenericVariable.getVariblesList().add(new GenericVariable(variableName, referenceCombo, attributeCombo, existanceRate));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historySimulation.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
            int id = Integer.parseInt(n);
            executionSimulation = smulationEvolution.getExecutionSimulations().stream()
                    .filter(s -> s.getId() == id).findFirst().get();
            totalBlocExecution.setText(executionSimulation.getTotalBlocExecution() + "");
            totalTime.setText(executionSimulation.getTotalTime() + "");

            executionSimulation.show();
            executionSimulation.fillPieChart();
        });
        smulationEvolution = new SimulationEvolution(chart);
        executionSimulation = new SQLExecutionSimulation(blocsTable, pieChart, pes);
        createTableView();
        blocList.setEditable(true);
        blocList.setCellFactory(TextFieldListCell.forListView());

        /* Variables*/
        createVariablesTableView();

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
        try {
            QueriesBloc bloc = new QueriesBloc(blocList, rate);
            executionSimulation.addBloc(bloc);
            rate.setMax(100 - executionSimulation.getSumOfRates());
            alert.Alerts.done("Nouveau bloc a été ajouté");
        } catch (Exception e) {
            alert.Alerts.error(e.getMessage());
        }

    }

    @FXML
    private void onDeleteBocs(ActionEvent event) {
        int n = executionSimulation.removeBlocs();
        rate.setMax(n);
    }

    public void createTableView() {
        blocsTable.setShowRoot(false);
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

    public void createVariablesTableView() {

        referenceCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            attributeCombo.getItems().setAll(SQLSchema.getInstance().getTable(newValue.toString())
                    .getAttributes().stream().map(a -> a.getName()).collect(Collectors.toList()));

        });

        variablesTable.setShowRoot(false);
        JFXTreeTableColumn<GenericVariable, JFXCheckBox> updateColumn = new JFXTreeTableColumn<>("");
        JFXTreeTableColumn<GenericVariable, JFXListView<String>> nameColumn = new JFXTreeTableColumn<>("Nom");
        JFXTreeTableColumn<GenericVariable, String> tableColumn = new JFXTreeTableColumn<>("Table de référence");
        JFXTreeTableColumn<GenericVariable, Double> attributeColumn = new JFXTreeTableColumn<>("Attribut de référence");
        JFXTreeTableColumn<GenericVariable, JFXSlider> rateColumn = new JFXTreeTableColumn<>("Pourcentage d'existance");
        updateColumn.setPrefWidth(50);
        nameColumn.setPrefWidth(200);
        tableColumn.setPrefWidth(300);
        attributeColumn.setPrefWidth(300);
        rateColumn.setPrefWidth(200);

        nameColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("name"));
        tableColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("tableReference"));
        attributeColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("attributeReference"));
        rateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("existanceRate"));
        updateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("update"));
        variablesTable.getColumns()
                .addAll(updateColumn, nameColumn, tableColumn, attributeColumn, rateColumn);
        variablesTable.setRoot(new RecursiveTreeItem<>(GenericVariable.getVariblesList(), (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        variablesTable.refresh();

    }

    @FXML
    private void onSimulate(ActionEvent event) {
        try {
            executionSimulation.setTotalBlocExecution(Long.parseLong(totalBlocExecution.getText()));
            executionSimulation.simulate();
            totalTime.setText(executionSimulation.getTotalTime() + "");
            tabPane.getSelectionModel().select(2);
            smulationEvolution.addSQLExecutionSimulation(executionSimulation);
            executionSimulation = executionSimulation.clone();
            historySimulation.getItems().add("" + executionSimulation.getId());
        } catch (Exception e) {
            alert.Alerts.error("err");
<<<<<<< HEAD
            System.err.println("err :" + e.getMessage());
=======
            e.printStackTrace();
>>>>>>> a4db2b31d3053a719a364f8e076c5f7ab46755ee
        }
    }

    @FXML
    private void onResetEvolution(ActionEvent event) {
        smulationEvolution.reset();
        pieChart.getData().clear();
        chart.getData().clear();
        blocsTable.refresh();
        executionSimulation.removeBlocs(true);
        rate.setMax(100);
        blocList.getItems().clear();

    }

    @FXML
    private void onNewSimulation(ActionEvent event) {
    }

    @FXML
    private void onAddVariables(Event event) {
        referenceCombo.getItems().
                setAll(SQLSchema.getInstance().getTables()
                        .stream().map(a -> a.getTableName()).collect(Collectors.toList()));
    }

    @FXML
    private void onIndex(Event event) {

        try {
            HelperControllers.addNodeToContainer("index.fxml", indexPane);
            //HelperControllers.showControlllerOnAlert("index.fxml", "Index");
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
