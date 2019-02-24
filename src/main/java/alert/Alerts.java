/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alert;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import controllers.helper.HelperControllers;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;

/**
 *
 *
 *
 * @author Maroine
 */
public class Alerts {

    /**
     * Show a red message error in the center bottom of screen for few moment
     *
     * @param message the message to show
     */
    public static void error(String message) {
        JFXSnackbar bar;
        try {
            bar = (JFXSnackbar) HelperControllers.getNodeController("error.fxml");
            bar.registerSnackbarContainer((Pane) HelperControllers.root);
            bar.enqueue(new SnackbarEvent(message));
        } catch (IOException ex) {
            Logger.getLogger(Alerts.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Show a Green message in the center bottom of screen for few moment
     *
     * @param message the message to show
     */
    public static void done(String message) {
        JFXSnackbar bar;
        try {
            bar = (JFXSnackbar) HelperControllers.getNodeController("done.fxml");
            bar.registerSnackbarContainer((Pane) HelperControllers.root);
            bar.enqueue(new SnackbarEvent(message));
        } catch (IOException ex) {
            Logger.getLogger(Alerts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
