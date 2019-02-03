/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import beans.SQLSchema;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import connection.SQLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class QueriesBlock extends RecursiveTreeObject<QueriesBlock> {

    private ArrayList<String> queries = new ArrayList<String>();
    private int executionNumber;
    private int currentExecutionNumber = 0;
    private JFXSlider rate;
    private int time = 0;
    private JFXListView list;

    public QueriesBlock(JFXListView list, JFXSlider rate) {
        this.list = new JFXListView<>();
        this.list.getItems().addAll(list.getItems());
        this.rate = new JFXSlider();
        this.rate.setValue(rate.getValue());
    }

    public boolean execute() throws SQLException {
        if (currentExecutionNumber == executionNumber) {
            return false;
        }
        long t1 = System.currentTimeMillis();
        for (String query : queries) {
            SQLConnection.getInstance().execute(query);
        }
        long t2 = System.currentTimeMillis();
        time += t2 - t1;
        currentExecutionNumber++;
        return true;
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

    public double getRate() {
        return rate.getValue();
    }

}
