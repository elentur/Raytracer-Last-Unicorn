package controller;

import geometries.Geometry;
import geometries.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import utils.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class NodeSettingsViewController  extends AController{

    @FXML
    TabPane tabPane;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        selectedElement.addListener(a-> handleSelectedElement());
    }

    private void handleSelectedElement() {
        Element e = selectedElement.getValue();
        tabPane.getTabs().clear();
        try {
            if(e !=null){
                Tab t = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsView.fxml"));
                t.setText(e.getClass().getName());
                tabPane.getTabs().add(t);
                if(e instanceof Geometry){
                    if(!((Node)e).geos.isEmpty()&&!(((Node)e).geos.get(0) instanceof Node)){
                        t = FXMLLoader.load(getClass().getResource("/fxml/mainMaterialSettingsView.fxml"));
                        t.setText(((Geometry)e).material.getClass().getName());
                        tabPane.getTabs().add(t);
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
