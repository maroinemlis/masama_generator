/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Maroine
 */
public class SimulationEvolution {

    private List<SQLExecutionSimulation> executionSimulations = new ArrayList<>();
    private LineChart chart;

    public List<SQLExecutionSimulation> getExecutionSimulations() {
        return executionSimulations;
    }

    public SimulationEvolution(LineChart chart) {
        this.chart = chart;
    }

    public void addSQLExecutionSimulation(SQLExecutionSimulation e) {
        this.executionSimulations.add(e);
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Simulation " + (executionSimulations.size() - 1), e.getTotalTime()));
        chart.getData().add(series);
    }

    public void reset() {
        executionSimulations.forEach(s -> {
            s.reset();
        });
        executionSimulations.clear();
    }

}
