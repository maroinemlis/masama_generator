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
 * @author Maroine
 */
public class HelperControllers {

    private static Object _this;

    /**
     * set the resources class to get fxml files
     *
     * @param _this
     */
    public static void setClassResources(Object _this) {
        HelperControllers._this = _this;
    }

    /**
     *
     * @param file
     * @return a Parent object from a given fxml file name as a node
     * @throws IOException
     */
    public static Parent getNodeController(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_this.getClass().getClassLoader().getResource("fxml/" + file));
        Parent name = (Parent) fxmlLoader.load();
        return name;
    }

    /**
     *
     * @param <C>
     * @param file
     * @return the controller instance class from a given fxml file name
     * @throws IOException
     */
    public static <C> C getController(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(_this.getClass().getClassLoader().getResource("fxml/" + file));
        return fxmlLoader.<C>getController();
    }

    /**
     * Show controlller view as a stage scene window
     *
     * @param file
     * @param title
     * @throws IOException
     */
    public static void showControlller(String file, String title) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(getNodeController(file));
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Add a node to a given container pane
     *
     * @param <C>
     * @param file
     * @param container
     * @throws IOException
     */
    public static <C> void addNodeToContainer(String file, Pane container) throws IOException {
        Parent nodeController = getNodeController(file);
        container.getChildren().add(nodeController);
    }

    /**
     * Show controller view inside an alert
     *
     * @param file
     * @param title
     * @throws IOException
     */
    public static void showControllerOnAlert(String file, String title) throws IOException {
        SnackBarAlert.getInstance().newSnackBarAlert(getNodeController(file), title);
    }
    public static Parent root;

    /**
     * set the root parent of the total view
     *
     * @param root
     */
    public static void setRootParent(Parent root) {
        HelperControllers.root = root;
    }

}
