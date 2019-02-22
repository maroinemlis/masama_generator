/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.tableview;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import beans.Attribute;
import beans.Table;
import com.jfoenix.controls.JFXListView;
import controllers.main.LuncherApp;
import models.AttributeModel;
import models.InstancesModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * A table view that represent a the view an sql schema table
 *
 * @author Maroine
 */
public class TableView implements Serializable {

    private Table table;
    private JFXTreeTableView<AttributeModel> tableView = null;
    private JFXTreeTableView<InstancesModel> insertsView = null;
    private ObservableList<InstancesModel> observablesInserts = FXCollections.observableArrayList();
    private ObservableList<AttributeModel> observablesAttributes = FXCollections.observableArrayList();
    private int howMuchWeDisplayed = 0;

    public List<List<StringProperty>> getLines() {
        return observablesInserts.stream().map(t -> t.getInstances()).collect(Collectors.toList());
    }

    public TableView(Table table) {
        this.table = table;
        tableView = createTableView();
        insertsView = createTableViewInserts();

    }

    /**
     *
     * @return get the table instance of the view
     */
    public Table get() {
        return table;
    }

    /**
     *
     * @return get the jfx tree table view
     */
    public JFXTreeTableView<AttributeModel> getTableView() {
        return tableView;
    }

    /**
     *
     * @return get the table inserts view that contains the data
     */
    public JFXTreeTableView<InstancesModel> getTableInserts() {
        return insertsView;
    }

    /**
     * Add n sql queries inserts on the view
     *
     * @param n
     */
    public void addLine(int n) {
        int j;
        for (j = howMuchWeDisplayed; j < table.getAttributes().get(0).getInstances().size() && j < howMuchWeDisplayed + n; j++) {

            ArrayList<StringProperty> listDonnees = new ArrayList();
            for (Attribute a : table.getAttributes()) {
                listDonnees.add(new SimpleStringProperty(a.getInstances().get(j)));
            }
            InstancesModel line = new InstancesModel(listDonnees);
            observablesInserts.add(line);
        }
        howMuchWeDisplayed = howMuchWeDisplayed + n;
        insertsView.refresh();
    }

    /**
     * Update the table view for each scroll
     */
    public void updateTableViewInserts() {
        observablesInserts.clear();
        howMuchWeDisplayed = 0;
        addLine(10);
        insertsView.setOnScroll((event) -> {
            if (event.getTextDeltaY() < 0) {
                addLine(10);
            }
        });
    }

    /**
     *
     * @return create and retruen a jfx tree table view for the sql inserts of
     * this table
     */
    public JFXTreeTableView<InstancesModel> createTableViewInserts() {
        JFXTreeTableView<InstancesModel> treeTableView = new JFXTreeTableView<>();
        treeTableView.setShowRoot(false);
        int i = 0;
        for (Attribute a : table.getAttributes()) {
            JFXTreeTableColumn<InstancesModel, String> col = new JFXTreeTableColumn<>(a.getName());
            final int currentI = i;
            col.setCellValueFactory((param) -> {
                return param.getValue().getValue().getInstances().get(currentI);
            });
            col.setPrefWidth(LuncherApp.getPrimaryStage().getWidth() / table.getAttributes().size());
            treeTableView.getColumns().add(col);
            i++;
        }
        treeTableView.setRoot(new RecursiveTreeItem<>(observablesInserts, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        treeTableView.setMaxWidth(Double.MAX_VALUE);
        treeTableView.setMaxHeight(Double.MAX_VALUE);
        return treeTableView;
    }

    /**
     *
     * @return create and return the table defintion as a table view
     */
    public JFXTreeTableView<AttributeModel> createTableView() {
        JFXTreeTableView<AttributeModel> treeTableView = new JFXTreeTableView<>();
        treeTableView.setShowRoot(false);
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> checked = new JFXTreeTableColumn<>("Colonne");
        JFXTreeTableColumn<AttributeModel, String> name = new JFXTreeTableColumn<>("Colonne");
        JFXTreeTableColumn<AttributeModel, String> type = new JFXTreeTableColumn<>("Type");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> pk = new JFXTreeTableColumn<>("Clé primaire");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> unique = new JFXTreeTableColumn<>("Unique");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> nullable = new JFXTreeTableColumn<>("Nullable");
        JFXTreeTableColumn<AttributeModel, ?> generatorType = new JFXTreeTableColumn<>("Type de génération");
        JFXTreeTableColumn<AttributeModel, ?> specificType = new JFXTreeTableColumn<>("Spécificité");
        JFXTreeTableColumn<AttributeModel, ?> from = new JFXTreeTableColumn<>("Valeur min");
        JFXTreeTableColumn<AttributeModel, ?> to = new JFXTreeTableColumn<>("Valeur max");
        JFXTreeTableColumn<AttributeModel, JFXListView> references = new JFXTreeTableColumn<>("Référence(s)");
        checked.setCellValueFactory(new TreeItemPropertyValueFactory("checked"));
        name.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        type.setCellValueFactory(new TreeItemPropertyValueFactory("dataType"));
        pk.setCellValueFactory(new TreeItemPropertyValueFactory("isPrimaryKey"));
        unique.setCellValueFactory(new TreeItemPropertyValueFactory("isUnique"));
        nullable.setCellValueFactory(new TreeItemPropertyValueFactory("isNullable"));
        generatorType.setCellValueFactory(new TreeItemPropertyValueFactory("generatorType"));
        specificType.setCellValueFactory(new TreeItemPropertyValueFactory("specificType"));
        from.setCellValueFactory(new TreeItemPropertyValueFactory("from"));
        to.setCellValueFactory(new TreeItemPropertyValueFactory("to"));
        references.setCellValueFactory(new TreeItemPropertyValueFactory("references"));

        treeTableView.getColumns().addAll(checked, name, type, pk, unique, nullable, generatorType, specificType, from, to, references);
        List<AttributeModel> collect = table.getAttributes().stream().
                map(att -> new AttributeModel(att, table)).
                collect(Collectors.toList());
        observablesAttributes.addAll(collect);
        treeTableView.setRoot(new RecursiveTreeItem<>(observablesAttributes, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        treeTableView.setMaxWidth(Double.MAX_VALUE);
        treeTableView.setMaxHeight(Double.MAX_VALUE);
        return treeTableView;
    }

}
