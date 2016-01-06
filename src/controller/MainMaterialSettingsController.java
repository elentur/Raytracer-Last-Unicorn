package controller;

import UI.MaterialView;
import geometries.Node;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import material.Material;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainMaterialSettingsController extends AController {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @FXML
    private MaterialView materialView;
    @FXML
    private ColorPicker cplDiffuse;
    @FXML
    private ComboBox cmbDiffuse;
    @FXML
    private TextField txtNormalScale;
    @FXML
    private ComboBox cmbNormal;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        ObjectProperty<Material> material = new SimpleObjectProperty<>(((Node)selectedElement.get()).geos.get(0).material);
        materialView.setUpTracer(material);
    }

    public void handleDiffuseColor(ActionEvent actionEvent) {
    }

    public void handleDiffuseTexture(ActionEvent actionEvent) {
    }

    public void handleNormalScale(ActionEvent actionEvent) {
    }

    public void handleNormalValue(ActionEvent actionEvent) {
    }
}
