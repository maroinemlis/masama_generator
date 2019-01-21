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
import db.bean.Attribute;
import db.bean.Table;
import db.models.AttributeModel;
import db.models.InstancesModel;
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
 *
 * @author tamac
 */
public class TableView implements Serializable {

    private Table table;
    private JFXTreeTableView<AttributeModel> tableView = null;
    private JFXTreeTableView<InstancesModel> insertsView = null;
    ObservableList<InstancesModel> observablesInserts = FXCollections.observableArrayList();
    ObservableList<AttributeModel> observablesAttributes = FXCollections.observableArrayList();
    int howMuchWeDisplayed = 0;

    public List<List<StringProperty>> getLines() {
        return observablesInserts.stream().map(t -> t.getInstances()).collect(Collectors.toList());
    }

    public TableView(Table table) {
        this.table = table;
        tableView = createTableView();
        insertsView = createTableViewInserts();

    }

    public Table get() {
        return table;
    }

    public JFXTreeTableView<AttributeModel> getTableView() {
        return tableView;
    }

    public JFXTreeTableView<InstancesModel> getTableInserts() {
        return insertsView;
    }

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
            col.setPrefWidth(LuncherApp.primaryStage.getWidth() / table.getAttributes().size());
            treeTableView.getColumns().add(col);
            i++;
        }
        treeTableView.setRoot(new RecursiveTreeItem<>(observablesInserts, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        treeTableView.setMaxWidth(Double.MAX_VALUE);
        treeTableView.setMaxHeight(Double.MAX_VALUE);
        return treeTableView;
    }

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
        //JFXTreeTableColumn<AttributeModel, ?> references = new JFXTreeTableColumn<>("Référence");
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
        //references.setCellValueFactory(new TreeItemPropertyValueFactory("reference"));

        treeTableView.getColumns().addAll(checked, name, type, pk, unique, nullable, generatorType, specificType, from, to/*, references*/);
        List<AttributeModel> collect = table.getAttributes().stream().
                map(att -> new AttributeModel(att, table)).
                collect(Collectors.toList());
        observablesAttributes.addAll(collect);
        treeTableView.setRoot(new RecursiveTreeItem<>(observablesAttributes, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        treeTableView.setMaxWidth(Double.MAX_VALUE);
        treeTableView.setMaxHeight(Double.MAX_VALUE);
        return treeTableView;
    }

    void updateAttributes() {
        for (AttributeModel a : observablesAttributes) {
            a.update();
        }
    }
}
