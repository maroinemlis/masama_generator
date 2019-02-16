/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
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

    public ObservableList<QueriesBloc> getBlocs() {
        return blocs;
    }

    public int getSumOfRates() {
        return sumOfRates;
    }

    public SQLExecutionSimulation(JFXTreeTableView<QueriesBloc> blocsTable, PieChart pieChart) {
        this.pieChart = pieChart;
        this.blocsTable = blocsTable;
        show();
    }

    public void setTotalBlocExecution(long totalBlocExecution) {
        this.totalBlocExecution = totalBlocExecution;
    }

    public void addBloc(QueriesBloc bloc) {
        blocs.add(bloc);
        blocsTable.refresh();
        sumOfRates += (int) bloc.getRateColumn().getValue();
    }

    public int removeBlocs() {
        blocs.removeIf(bloc -> {
            sumOfRates -= (int) bloc.getRateColumn().getValue();
            return bloc.getUpdate().isSelected();
        });
        blocsTable.refresh();
        return 100 - sumOfRates;
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

    public void simulate() throws SQLException {
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
        blocsTable.refresh();
        fillPieChart();
    }

    public void fillPieChart() {
        ObservableList<PieChart.Data> d = pieChart.getData();
        d.clear();
        int i = 0;
        for (QueriesBloc bloc : blocs) {
            double rate = bloc.getTime() * 1.0 / totalTime;
            d.add(new Data("Bloc " + i + " = " + (bloc.getTime() * 100) / totalTime + " %", rate));
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

    public long getTotalTime() {
        return totalTime;
    }

    public long getTotalBlocExecution() {
        return totalBlocExecution;
    }

    public void show() {
        blocsTable.setRoot(new RecursiveTreeItem<>(blocs, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        blocsTable.refresh();
    }

    void setSumOfRates(int i) {
        this.sumOfRates = i;
    }
}
