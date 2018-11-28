/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.connection;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author acer
 */
public class AuthController implements Initializable {

    @FXML
    private JFXComboBox<String> driver;
    @FXML
    private JFXTextField url;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        driver.getItems().addAll("SQLite", "mySQL", "Oracle", "SQLServer", "Derby");
        driver.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println(newValue);
        }
        );
    }

    public String getURL() {
        return url.getText();
    }

    public String getUser() {
        return user.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getCnxType() {
        return driver.getSelectionModel().getSelectedItem();
    }

}
