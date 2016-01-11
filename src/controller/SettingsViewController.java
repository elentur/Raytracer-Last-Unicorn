package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import utils.Element;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by roberto on 06.01.16.
 */
public class SettingsViewController extends AController{
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedElement.addListener(new ChangeListener<Element>() {
            @Override
            public void changed(ObservableValue<? extends Element> observable, Element oldValue, Element newValue) {
                System.out.println(observable);
                System.out.println(oldValue);
                System.out.println(newValue);
            }
        });

    }
}
