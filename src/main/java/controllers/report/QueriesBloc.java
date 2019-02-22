/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import connection.SQLConnection;
import java.sql.SQLException;
import javafx.scene.control.cell.TextFieldListCell;

/**
 *
 * @author Maroine
 */
public class QueriesBloc extends RecursiveTreeObject<QueriesBloc> {

    private JFXSlider rate;
    private long time = 0;
    private JFXListView<String> list;
    private final JFXCheckBox updateCheckBox;

    public QueriesBloc(JFXListView<String> list, JFXSlider rate) {
        this.list = new JFXListView<>();
        this.list.setEditable(true);
        this.list.setCellFactory(TextFieldListCell.forListView());
        this.list.getItems().addAll(list.getItems());
        this.rate = new JFXSlider();
        this.rate.setMin(0);
        this.rate.setMax(100);
        this.rate.setValue(rate.getValue());
        this.updateCheckBox = new JFXCheckBox();

    }

    public void execute() throws SQLException {
        long t1 = System.currentTimeMillis();
        for (String query : list.getItems()) {
            SQLConnection.getInstance().execute(query);
        }
        long t2 = System.currentTimeMillis();
        time += t2 - t1;
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

    public long getTime() {
        return time;
    }

    public void reset() {
        list.getItems().clear();
    }

    public void setTime(int i) {
        time = i;
    }

    public JFXCheckBox getUpdate() {
        return updateCheckBox;
    }
}
