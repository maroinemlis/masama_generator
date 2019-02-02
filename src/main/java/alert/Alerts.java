/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alert;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import controllers.helper.HelperControllers;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author Maroine
 */
public class Alerts {

    public static void error(String message) {
        JFXSnackbar bar = new JFXSnackbar((Pane) HelperControllers.root);
        bar.show(message, message, (event) -> {
        });
    }

    public static void done(String message) {
        JFXSnackbar bar = new JFXSnackbar((Pane) HelperControllers.root);
        bar.enqueue(new SnackbarEvent(message));
    }
}
