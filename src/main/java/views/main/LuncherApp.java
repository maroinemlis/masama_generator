/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import test_classes.TestClass_amirouche;

/**
 *
 * @author amirouche
 */
public class LuncherApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  throws Exception{
        //new TestClass_amirouche().main();        
        startMainInterface(primaryStage);
    }
    
    public void startMainInterface(Stage primaryStage)throws Exception{
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/main2.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/main.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/main.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("MASAMA Generator v1");
        primaryStage.show();
    }
}
