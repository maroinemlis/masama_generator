/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class QueriesBlock {

    private ArrayList<String> queries = new ArrayList<String>();
    private ArrayList<Long> times = new ArrayList<Long>();
    private int executionNumber;
    private int globalTime = 0;

    public void execute(int i) throws SQLException {
        long t1 = System.currentTimeMillis();
        String[] queries = this.queries.get(i).split(";");
        for (String query : queries) {
        }
        long t2 = System.currentTimeMillis();
        times.add(t2 - t1);
        globalTime += times.get(i);
        executionNumber--;
    }

    public void add(String query) {
        queries.add(query);
    }

}
