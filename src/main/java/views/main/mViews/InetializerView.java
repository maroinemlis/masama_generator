/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.main.mViews;

import com.jfoenix.controls.JFXComboBox;
import java.util.Iterator;
import java.util.List;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 *
 * @author amirouche
 */
public class InetializerView {

    public void initializeSpiner(Spinner<Integer> spinerNbrTuples, int minValeu, int maxValeu, int initializeValeu) {

        SpinnerValueFactory<Integer> spinnerValueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValeu, maxValeu, initializeValeu);
        spinerNbrTuples.setValueFactory(spinnerValueFactory);
    }

    public <T> void initializeCombobox(JFXComboBox<T> comboBoxValeuOfString, List<T> list) {
        Iterator<? extends T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            comboBoxValeuOfString.getItems().add(next);
        }
        comboBoxValeuOfString.getSelectionModel().selectFirst();
    }

}
