package controllers.report;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author abidi asma + Maroine
 */
public class ReportController implements Initializable {

    @FXML
    private TextArea query;
    @FXML
    private JFXListView<String> blocList;
    @FXML
    private JFXSlider rate;
    @FXML
    private JFXTextField queriesTotalNumber;
    @FXML
    private JFXTextField totalTime;

    private ObservableList<QueriesBlock> observablesQueriesBlock = FXCollections.<QueriesBlock>observableArrayList();
    @FXML
    private JFXTreeTableView<QueriesBlock> blocsTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTableView();
    }

    @FXML
    private void onAddQuery(ActionEvent event) {
        blocList.getItems().add(query.getText());
    }

    @FXML
    private void onDeleteQuery(ActionEvent event) {
        int selectedIndex = blocList.getSelectionModel().getSelectedIndex();
        blocList.getItems().remove(selectedIndex);
    }

    @FXML
    private void onAddBloc(ActionEvent event) {
        observablesQueriesBlock.add(new QueriesBlock(blocList, rate));
        blocsTable.setRoot(new RecursiveTreeItem<>(observablesQueriesBlock, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        alert.Alerts.done("Nouveau bloc a été ajouté");
    }

    public void createTableView() {
        blocsTable.setShowRoot(false);
        ObservableList<String> stylesheets = blocsTable.getStylesheets();
        JFXTreeTableColumn<QueriesBlock, ?> queriesListColumn = new JFXTreeTableColumn<>("Bloc");
        JFXTreeTableColumn<QueriesBlock, ?> rateColumn = new JFXTreeTableColumn<>("Pourcentage");
        JFXTreeTableColumn<QueriesBlock, ?> timeColumn = new JFXTreeTableColumn<>("Temps d'éxécution");
        queriesListColumn.setPrefWidth(700);
        rateColumn.setPrefWidth(400);
        timeColumn.setPrefWidth(100);

        queriesListColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("queriesListColumn"));
        rateColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("rateColumn"));
        timeColumn.setCellValueFactory(
                new TreeItemPropertyValueFactory("timeColumn"));
        blocsTable.getColumns()
                .addAll(queriesListColumn, rateColumn, timeColumn);

        blocsTable.setRoot(
                new RecursiveTreeItem<>(observablesQueriesBlock, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
    }

}
