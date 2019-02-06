/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 *
 * @author Maroine
 */
public class SQLExecutionSimulation {

    private ArrayList<QueriesBloc> blocs = new ArrayList<>();
    private long totalTime = 0;
    private long totalBlocExecution;
    private final PieChart pieChart;

    public void setTotalBlocExecution(long totalBlocExecution) {
        this.totalBlocExecution = totalBlocExecution;
    }

    public SQLExecutionSimulation(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    public void addBloc(QueriesBloc bloc) {
        blocs.add(bloc);
    }

    public void removeBloc(QueriesBloc bloc) {
        blocs.remove(bloc);
    }

    private List<Integer> generateRandomSequence() {
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

    public long simulate() throws SQLException {
        totalTime = 0;
        for (QueriesBloc bloc : blocs) {
            bloc.setTime(0);
        }
        List<Integer> arr = generateRandomSequence();
        for (int i = 0; i < arr.size(); i++) {
            blocs.get(arr.get(i)).execute();
        }
        for (QueriesBloc bloc : blocs) {
            totalTime += bloc.getTime();
        }
        fillPieChart();
        fillPieChart();

        return totalTime;
    }

    public void fillPieChart() {
        ObservableList<PieChart.Data> d = pieChart.getData();
        d.clear();
        int i = 0;
        for (QueriesBloc bloc : blocs) {
            double rate = bloc.getTime() * 1.0 / totalTime;
            d.add(new Data("Bloc " + i + " " + (bloc.getTime() * 100) / totalTime + " %", rate));
            i++;
        }
    }

    public void reset() {
        blocs.forEach(b -> {
            b.reset();
        });
        blocs.clear();
        totalTime = 0;
    }

}
