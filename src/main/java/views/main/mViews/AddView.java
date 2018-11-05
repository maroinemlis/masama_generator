/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.main.mViews;

import db.bean.Table;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.VBox;

/**
 *
 * @author amirouche
 */
public class AddView {

    public static final int SIZE_LEFT_PANE = 333;

    public static final int SIZE_SPLIT_PANE_HEIGHT = 40;

    /**
     *
     * @author amirouche
     * @param table
     */
    public TitledPane addTableToProject(Table table) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(table.getTableName());
        titledPane.setContent(addColumns());
        titledPane.setExpanded(false);
        titledPane.setPrefSize(SIZE_LEFT_PANE, USE_COMPUTED_SIZE);
        return titledPane;
        //vBox.getChildren().addAll(titledPane);
    }

    public VBox addColumns() {
        VBox boxCulomns = new VBox();
        SplitPane splitPane = getCulomn();
        boxCulomns.getChildren().add(splitPane);
        splitPane = getCulomn();
        boxCulomns.getChildren().add(splitPane);
        return boxCulomns;
    }

    private SplitPane getCulomn() {
        SplitPane splitPane = new SplitPane();
        splitPane.setPrefSize(SIZE_LEFT_PANE, SIZE_SPLIT_PANE_HEIGHT);
        Label c1 = new Label("Culomn Name");
        Label l1 = new Label("character 1");
        Label l2 = new Label("character 2");
        VBox boxCharacters = new VBox(l1, l2);
        splitPane.getItems().addAll(c1, boxCharacters);

        return splitPane;
    }

}
