package controllers.index;

import beans.Attribute;
import beans.SQLSchema;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Maroine
 */
public class IndexController implements Initializable {

    @FXML
    private JFXListView<HBox> attributes;
    @FXML
    private JFXListView<String> tables;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> collect = SQLSchema.getInstance().getTables().stream().map(t -> t.getTableName()).collect(Collectors.toList());
        tables.getItems().addAll(collect);

        tables.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
            attributes.getItems().clear();
            SQLSchema.getInstance()
                    .getTable(n).getAttributes().forEach((Attribute a) -> {
                HBox b = new HBox();
                b.getChildren().add(new Label(a.getName()));
                JFXCheckBox isIndex = new JFXCheckBox();
                isIndex.setSelected(a.isIndex());
                b.getChildren().add(isIndex);
                attributes.getItems().add(b);
                isIndex.selectedProperty().addListener(obb -> a.isIndex(isIndex.isSelected()));
            });
        });
    }

}
