package controllers.main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.helper.HelperControllers;

/**
 *
 * @author amirouche
 */
public class LuncherApp extends Application {

    public static Stage primaryStage;

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
        this.primaryStage = primaryStage;
        startMainInterface();
    }

    public void startMainInterface() throws Exception {
        Parent root = HelperControllers.getNodeController(this, "fxml/main.fxml");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MASAMA Generator v1");
        primaryStage.show();

    }
}
