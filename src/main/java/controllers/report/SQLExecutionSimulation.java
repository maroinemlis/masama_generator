/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import utils.Shared;

/**
 *
 * @author Maroine
 */
public class SQLExecutionSimulation {

    private ArrayList<QueriesBlock> blocs = new ArrayList<>();
    private int totalQueries;

    public SQLExecutionSimulation(int totalQueries) {
        this.totalQueries = totalQueries;
    }

    public void addBloc(QueriesBlock bloc) {
        blocs.add(bloc);
    }

    public void removeBloc(QueriesBlock bloc) {
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
    }
}
