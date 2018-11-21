/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.main;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.bean.Attribute;
import db.bean.Table;
import db.models.AttributeModel;
import db.models.InstancesModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import static views.main.MainController.schema;

/**
 *
 * @author tamac
 */
public class TableView implements Serializable {

    private Table table;
    private JFXTreeTableView<Data> insertsView = null;
    private JFXTreeTableView<AttributeModel> tableView = null;

    public TableView(Table table) {
        this.table = table;
        tableView = createTableView();
    }

    public Table get() {
        return table;
    }

    public JFXTreeTableView<AttributeModel> getTableView() {
        return tableView;
    }
    
    public void insertInstancesInTable( ArrayList<Data> tables_data, String tableName, JFXTreeTableView<Data> instancesView){
        instancesView.setVisible(true);
        ObservableList<Data> data = FXCollections.observableArrayList();
        List<Attribute> listAttribute = schema.getTableByName(tableName).getAttributes();                   
        instancesView.getColumns().clear();
        for(int i=0; i<listAttribute.size();i++){
            final int finalIdx = i;
            String nameOfColomn = listAttribute.get(i).getName();
            JFXTreeTableColumn<Data, String> column = new JFXTreeTableColumn<>(nameOfColomn);
            column.setPrefWidth(instancesView.getWidth()/listAttribute.size());
            column.setCellValueFactory((param) -> {
                return param.getValue().getValue().lines.get(finalIdx);
            });
            instancesView.getColumns().add(column);
        }
        for(Data table : tables_data){
            if(table.table_name.equals(tableName))
            data.add(table);
        }

        final TreeItem<Data> root = new RecursiveTreeItem<Data>(data, RecursiveTreeObject::getChildren);
        instancesView.setRoot(root);
        instancesView.setShowRoot(false);
    }

    public JFXTreeTableView<InstancesModel> getTableInserts() {
        JFXTreeTableView<InstancesModel> treeTableView = new JFXTreeTableView<>();
        treeTableView.setShowRoot(false);
        int i = 0;
        for (Attribute a : table.getAttributes()) {
            JFXTreeTableColumn<InstancesModel, String> col = new JFXTreeTableColumn<>(a.getName());
            col.setCellValueFactory(new TreeItemPropertyValueFactory("column" + i));
            treeTableView.getColumns().add(col);
            i++;
        }
        List<InstancesModel> collect = new ArrayList<>();
        for (int j = 0; j < table.getHowMuch(); j++) {
            collect.add(new InstancesModel(table.getAttributes(), j));
        }
        ObservableList<InstancesModel> observables = FXCollections.observableArrayList(collect);
        treeTableView.setRoot(new RecursiveTreeItem<>(observables, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        treeTableView.setMaxWidth(Double.MAX_VALUE);
        treeTableView.setMaxHeight(Double.MAX_VALUE);
        return treeTableView;
    }

    public JFXTreeTableView<AttributeModel> createTableView() {
        JFXTreeTableView<AttributeModel> treeTableView = new JFXTreeTableView<>();
        treeTableView.setShowRoot(false);
        JFXTreeTableColumn<AttributeModel, String> name = new JFXTreeTableColumn<>("Colonne");
        JFXTreeTableColumn<AttributeModel, String> type = new JFXTreeTableColumn<>("Type");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> pk = new JFXTreeTableColumn<>("Clé primaire");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> unique = new JFXTreeTableColumn<>("Unique");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> nullable = new JFXTreeTableColumn<>("Nullable");
        JFXTreeTableColumn<AttributeModel, ?> generatorType = new JFXTreeTableColumn<>("generatorType");
        JFXTreeTableColumn<AttributeModel, ?> specificType = new JFXTreeTableColumn<>("specificType");
        JFXTreeTableColumn<AttributeModel, ?> from = new JFXTreeTableColumn<>("from");
        JFXTreeTableColumn<AttributeModel, ?> to = new JFXTreeTableColumn<>("to");

        name.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        type.setCellValueFactory(new TreeItemPropertyValueFactory("dataType"));
        pk.setCellValueFactory(new TreeItemPropertyValueFactory("isPrimaryKey"));
        unique.setCellValueFactory(new TreeItemPropertyValueFactory("isUnique"));
        nullable.setCellValueFactory(new TreeItemPropertyValueFactory("isNullable"));
        generatorType.setCellValueFactory(new TreeItemPropertyValueFactory("generatorType"));
        specificType.setCellValueFactory(new TreeItemPropertyValueFactory("specificType"));
        from.setCellValueFactory(new TreeItemPropertyValueFactory("from"));
        to.setCellValueFactory(new TreeItemPropertyValueFactory("to"));

        List<AttributeModel> collect = table.getAttributes().stream().
                map(att -> new AttributeModel(att)).
                collect(Collectors.toList());
        ObservableList<AttributeModel> observables = FXCollections.observableArrayList(collect);
        treeTableView.getColumns().addAll(name, type, pk, unique, nullable, generatorType, specificType, from, to);
        treeTableView.setRoot(new RecursiveTreeItem<>(observables, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        treeTableView.setMaxWidth(Double.MAX_VALUE);
        treeTableView.setMaxHeight(Double.MAX_VALUE);
        return treeTableView;
    }
}
