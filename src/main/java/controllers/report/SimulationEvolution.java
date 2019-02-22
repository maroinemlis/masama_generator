/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Maroine
 */
public class SimulationEvolution {

    private List<SQLExecutionSimulation> executionSimulations = new ArrayList<>();
    private AreaChart<String, Number> chart;
    int maxNumberOfBlocs = 0;

    public List<SQLExecutionSimulation> getExecutionSimulations() {
        return executionSimulations;

    }

    public SimulationEvolution(AreaChart<String, Number> chart) {
        this.chart = chart;
    }

    public void addSQLExecutionSimulation(SQLExecutionSimulation e) {
        executionSimulations.add(e);
        fillChart(e);
    }

    public void fillChart(SQLExecutionSimulation e) {
        maxNumberOfBlocs = e.getBlocs().size() > maxNumberOfBlocs ? e.getBlocs().size() : maxNumberOfBlocs;
        while (maxNumberOfBlocs > chart.getData().size()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            chart.getData().add(series);
            series.setName("Bloc " + chart.getData().size());
        }
        for (int i = 0; i < chart.getData().size(); i++) {
            if (i < e.getBlocs().size()) {
                QueriesBloc b = e.getBlocs().get(i);
                chart.getData().get(i).getData().add(new XYChart.Data("Simulation " + executionSimulations.size(), b.getTime()));
            }
        }
    }

    public void reset() {
        executionSimulations.forEach(s -> {
            s.reset();
        });
        executionSimulations.clear();
        chart.getData().removeIf(e -> e != null);
    }

}
