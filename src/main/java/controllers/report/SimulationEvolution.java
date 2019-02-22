/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;

/**
 * A class represent all the simulations done
 *
 * @author Maroine
 */
public class SimulationEvolution {

    private List<SQLExecutionSimulation> executionSimulations = new ArrayList<>();
    private AreaChart<String, Number> chart;
    int maxNumberOfBlocs = 0;

    /**
     *
     * @return the list of all the simulation
     */
    public List<SQLExecutionSimulation> getExecutionSimulations() {
        return executionSimulations;

    }

    public SimulationEvolution(AreaChart<String, Number> chart) {
        this.chart = chart;
    }

    /**
     * Add a simulation
     *
     * @param e
     */
    public void addSQLExecutionSimulation(SQLExecutionSimulation e) {
        executionSimulations.add(e);
        fillChart(e);
    }

    /**
     * Fill the simulation e on the chart evolution
     *
     * @param e
     */
    public void fillChart(SQLExecutionSimulation e) {
        maxNumberOfBlocs = e.getBlocs().size() > maxNumberOfBlocs ? e.getBlocs().size() : maxNumberOfBlocs;
        while (maxNumberOfBlocs > chart.getData().size()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            chart.getData().add(series);
            series.setName("Bloc " + chart.getData().size());
        }

        for (int i = 0; i < e.getBlocs().size(); i++) {
            QueriesBloc b = e.getBlocs().get(i);
            chart.getData().get(i).getData().add(new XYChart.Data("" + executionSimulations.size(), b.getTime()));
        }
    }

    /**
     * Reset all the simulation evolution
     */
    public void reset() {
        executionSimulations.forEach(s -> {
            s.reset();
        });
        executionSimulations.clear();
    }

}
