/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 *
 * @author Maroine
 */
public class SQLExecutionSimulation {

    private ArrayList<QueriesBloc> blocs = new ArrayList<>();
    private int totalQueries = 0;
    private long totalTime = 0;

    public void set(int totalQueries) {
        this.totalQueries = totalQueries;
    }

    public SQLExecutionSimulation() {
    }

    public void addBloc(QueriesBloc bloc) {
        blocs.add(bloc);
    }

    public void removeBloc(QueriesBloc bloc) {
        blocs.remove(bloc);
    }

    public void simulate() throws SQLException {
        ArrayList<Integer> arr = new ArrayList<>(totalQueries);
        for (int j = 0; j < blocs.size(); j++) {
            int n = (int) (blocs.get(j).getRate() * totalQueries / 100);
            for (int i = 0; i < n; i++) {
                arr.add(j);
            }
        }
        Collections.shuffle(arr);
        for (int i = 0; i < arr.size(); i++) {
            totalTime += blocs.get(arr.get(i)).execute();
        }
    }

    public void fillPieChart(PieChart pieChart) {
        ObservableList<PieChart.Data> d = pieChart.getData();
        int i = 0;
        for (QueriesBloc bloc : blocs) {
            d.add(new Data("Bloc " + i, bloc.getTime() * 100 / totalTime));
        }

    }

    public void reset() {
        blocs.forEach(b -> {
            b.reset();
        });
        blocs.clear();
        totalQueries = 0;
        totalTime = 0;
    }
}
