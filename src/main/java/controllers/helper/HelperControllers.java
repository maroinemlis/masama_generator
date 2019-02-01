/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author tamac
 */
public class HelperControllers {

    public static Parent getNodeController(Object _this, String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_this.getClass().getClassLoader().getResource(file));
        Parent name = (Parent) fxmlLoader.load();
        return name;
    }

    public static <C> C getController(Object _this, String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_this.getClass().getClassLoader().getResource(file));
        return fxmlLoader.<C>getController();
    }

    public static void showControlller(Object _this, String file, String title) throws IOException {
        Stage primaryStage = new Stage();
        Scene scene = new Scene(getNodeController(_this, file));
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.show();
    }

    public static <C> void addNodeToContainer(Object _this, String file, Pane container) throws IOException {
        Parent nodeController = getNodeController(_this, file);
        container.getChildren().add(nodeController);
    }

}
