package controllers.main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.helper.HelperControllers;
import test_classes.TestClass_amirouche;

/**
 *
 * @author amirouche
 */
public class LuncherApp extends Application {

    private static Stage primaryStage;

    /**
     * Return the main Stage
     *
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * The first method to run when launching the application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //new TestClass_amirouche().main();
        //new TestClass_saad().main();
        //new TestClass_maroine().main();
        //new TestClass_maryem().main();
        //new TestClass_asma().main();
        //new TestClass_abdNour().main();
        LuncherApp.primaryStage = primaryStage;
        startMainInterface();
    }

    /**
     * Initialize and Start the main interface
     *
     * @throws Exception
     */
    public void startMainInterface() throws Exception {
        HelperControllers.setClassResources(this);
        Parent root = HelperControllers.getNodeController("main.fxml");
        Scene scene = new Scene(root);
        HelperControllers.setRootParent(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MASAMA Generator v1");
        primaryStage.show();
    }
}
