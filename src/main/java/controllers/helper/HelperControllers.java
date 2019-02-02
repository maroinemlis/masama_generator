/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.helper;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author tamac
 */
public class HelperControllers {

    private static Object _this;

    public static void setClassResources(Object _this) {
        HelperControllers._this = _this;
    }

    public static Parent getNodeController(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_this.getClass().getClassLoader().getResource("fxml/" + file));
        Parent name = (Parent) fxmlLoader.load();
        return name;
    }

    public static <C> C getController(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_this.getClass().getClassLoader().getResource("fxml/" + file));
        return fxmlLoader.<C>getController();
    }

    public static void showControlller(String file, String title) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(getNodeController(file));
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }

    public static <C> void addNodeToContainer(String file, Pane container) throws IOException {
        Parent nodeController = getNodeController(file);
        container.getChildren().add(nodeController);
    }

    public static void showControlllerOnAlert(String file, String title) throws IOException {
        SnackBarAlert.getInstance().newSnackBarAlert(getNodeController(file), title);
    }
    public static Parent root;

    public static void setRootParent(Parent root) {
        HelperControllers.root = root;
    }

}
