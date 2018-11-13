/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.helper;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author tamac
 */
public class Helper {

    public static <C extends DataAcceptable, D> Parent getNodeComponent(Object _this, String file, D data) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_this.getClass().getClassLoader().getResource(file));
        Parent node = (Parent) fxmlLoader.load();
        if (data != null) {
            C controller = fxmlLoader.<C>getController();
            controller.setData(data);
        }
        return node;
    }
}
