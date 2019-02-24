/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import connection.SQLConnection;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * A class the represet an SQL simulation of a multiple blocs
 *
 * @author Maroine
 */
public class SQLExecutionSimulation {

    private ObservableList<QueriesBloc> blocs = FXCollections.<QueriesBloc>observableArrayList();
    private JFXTreeTableView<QueriesBloc> blocsTable;
    private PieChart pieChart;
    private long totalTime = 0;
    private long totalBlocExecution;
    private int sumOfRates = 0;
    private static int size = 0;
    private int id = 0;
    private final Label pes;
    private AreaChart<Number, Number> chartBlocs;

    @Override
    public SQLExecutionSimulation clone() {
        SQLExecutionSimulation c = new SQLExecutionSimulation(blocsTable, pieChart, pes, chartBlocs);
        c.blocs.addAll(blocs);
        c.show();
        return c;
    }

    /**
     *
     * @return the list observale of the a queriesBloc
     */
    public ObservableList<QueriesBloc> getBlocs() {
        return blocs;
    }

    /**
     *
     * @return the sum of the rates (always less than 100)
     */
    public int getSumOfRates() {
        return sumOfRates;
    }

    public SQLExecutionSimulation(JFXTreeTableView<QueriesBloc> blocsTable, PieChart pieChart, Label pes, AreaChart<Number, Number> chartBlocs) {
        this.blocsTable = blocsTable;
        this.pieChart = pieChart;
        this.pes = pes;
        this.chartBlocs = chartBlocs;
        show();
        size++;
        id = size;
    }

    /**
     * set the total execution of all the blocs
     *
     * @param totalBlocExecution
     */
    public void setTotalBlocExecution(long totalBlocExecution) {
        this.totalBlocExecution = totalBlocExecution;
    }

    /**
     * add a new bloc to this simulation
     *
     * @param bloc
     */
    public void addBloc(QueriesBloc bloc) {
        blocs.add(bloc);
        blocsTable.refresh();
        sumOfRates += (int) bloc.getRateColumn().getValue();
    }

    /**
     *
     * @return remove all the selected bloc from the table view
     */
    public int removeBlocs() {
        blocs.removeIf(bloc -> {
            sumOfRates -= (int) bloc.getRateColumn().getValue();
            return bloc.getUpdate().isSelected();
        });
        blocsTable.refresh();
        return 100 - sumOfRates;
    }

    /**
     * Clear all the blocs
     *
     * @param all
     */
    public void removeBlocs(boolean all) {
        blocs.clear();
        blocsTable.refresh();
    }

    /**
     *
     * @return a list of numbers that represent the order execution of the
     * blocs, its kind of randomness
     */
    public List<Integer> generateRandomSequence() {
        List<Integer> arr = new ArrayList<>((int) totalBlocExecution);
        for (int i = 0; i < blocs.size(); i++) {
            long n = (int) (blocs.get(i).getRate() * totalBlocExecution / 100);
            for (int j = 0; j < n; j++) {
                arr.add(i);
            }
        }
        Collections.shuffle(arr);
        return arr;
    }

    /**
     * Start to simulate
     *
     * @throws SQLException
     */
    public void simulate() throws SQLException {
        totalTime = 0;
        SQLConnection.getInstance().checkIndex();
        for (QueriesBloc bloc : blocs) {
            bloc.setTime(0);
            bloc.getAllTime().clear();
        }
        List<Integer> arr = generateRandomSequence();
        for (int i = 0; i < arr.size(); i++) {
            blocs.get(arr.get(i)).execute();
        }
        for (QueriesBloc bloc : blocs) {
            totalTime += bloc.getTime();
        }
        blocsTable.refresh();
        fillPieChart();
        fillAreaChart();
    }

    /**
     * Fill the pie chart with the data
     */
    public void fillPieChart() {
        pieChart.setTitle("Simulation numéro " + id);
        ObservableList<PieChart.Data> d = pieChart.getData();
        d.clear();
        int i = 1;
        for (QueriesBloc bloc : blocs) {
            double rate = bloc.getTime() * 1.0 / totalTime;
            d.add(new Data("Bloc " + i + " = " + (bloc.getTime() * 100) / totalTime + " %", rate));
            Data data = pieChart.getData().get(pieChart.getData().size() - 1);
            data.getNode().setOnMouseMoved((e) -> {
                Point p = MouseInfo.getPointerInfo().getLocation();
                pes.setTranslateX(p.getX());
                pes.setTranslateY(p.getY());
                pes.setText(data.getPieValue() + "%");
            });
            data.getNode().setOnMouseExited((e) -> {
                pes.setText("");
            });
            i++;
        }
    }

    /**
     * Fill the evolution chart of this simulation for each bloc
     */
    public void fillAreaChart() {
        chartBlocs.setTitle("Simulation numéro " + id);
        chartBlocs.getData().clear();
        int i = 1;
        //chartBlocs.getXAxis().setTickLabelGap(totalBlocExecution + 1 / 10);
        for (QueriesBloc bloc : blocs) {

            XYChart.Series series = new XYChart.Series();
            series.setName("Bloc " + i);
            for (int j = 0; j < bloc.getAllTime().size(); j++) {
                series.getData().add(new XYChart.Data(j, bloc.getAllTime().get(j)));
            }
            chartBlocs.getData().addAll(series);
            i++;
        }
    }

    /**
     * reset this simulation
     */
    public void reset() {
        blocs.forEach(b -> {
            b.reset();
        });
        blocs.clear();
        totalTime = 0;
        id = 0;
    }

    /**
     *
     * @return the total time for this simulation
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * Show the simulation
     */
    public void show() {
        blocsTable.setRoot(new RecursiveTreeItem<>(blocs, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        blocsTable.refresh();
    }

    /**
     *
     * @return the id of the simulation
     */
    public int getId() {
        return id;
    }
}
