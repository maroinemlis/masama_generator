/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class QueriesBlock extends RecursiveTreeObject<QueriesBlock> {

    private ArrayList<String> queries = new ArrayList<String>();
    private ArrayList<Long> times = new ArrayList<Long>();
    private int executionNumber;
    private JFXSlider rate;
    private int time = 0;
    private JFXListView list;

    public QueriesBlock(JFXListView list, JFXSlider rate) {
        this.list = new JFXListView<>();
        this.list.getItems().addAll(list.getItems());
        this.rate = new JFXSlider();
        this.rate.setValue(rate.getValue());
    }

    public void execute(int i) throws SQLException {
        long t1 = System.currentTimeMillis();
        String[] queries = this.queries.get(i).split(";");
        for (String query : queries) {
        }
        long t2 = System.currentTimeMillis();
        times.add(t2 - t1);
        time += times.get(i);
        executionNumber--;
    }

    public JFXListView getQueriesListColumn() {
        return list;
    }

    public JFXSlider getRateColumn() {
        return rate;
    }

    public double getTimeColumn() {
        return time;
    }

}
