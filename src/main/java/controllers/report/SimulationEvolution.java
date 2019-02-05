/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class SimulationEvolution {

    private ArrayList<SQLExecutionSimulation> executionSimulations = new ArrayList<>();
    private int i;

    public void addSQLExecutionSimulation(SQLExecutionSimulation e) {
        this.executionSimulations.add(e);
    }

    public String toString() {
        return "Simulation " + i;
    }

    public void reset() {
        executionSimulations.forEach(s -> {
            s.reset();
        });
        executionSimulations.clear();
    }
}
