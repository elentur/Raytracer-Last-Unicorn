package controller;

import UI.MaterialView;
import geometries.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import material.OrenNayarMaterial;
import material.PhongMaterial;
import material.ReflectiveMaterial;
import material.TransparentMaterial;

import java.io.IOException;
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
    private MaterialView materialRenderView;
    @FXML
    private VBox materialView;
    @FXML
    private ColorPicker cplDiffuse;
    @FXML
    private ComboBox cmbDiffuse;
    @FXML
    private TextField txtNormalScale;
    @FXML
    private ComboBox cmbNormal;

    private boolean initialized = false;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        material.setValue(((Node)selectedElement.get()).geos.get(0).material);
        materialRenderView.setUpTracer(material);
        if(!initialized) {
            VBox v;
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            try {
                if (material.get() instanceof OrenNayarMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainOrenNayarMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                } else if (material.get() instanceof PhongMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                }else if (material.get() instanceof ReflectiveMaterial) {
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                    v = loader.load(this.getClass().getResource("/fxml/mainReflectiveMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                } else  if (material.get() instanceof TransparentMaterial){
                    v = loader.load(this.getClass().getResource("/fxml/mainPhongMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                    v = loader.load(this.getClass().getResource("/fxml/mainReflectiveMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                    v = loader.load(this.getClass().getResource("/fxml/mainTransparentMaterialSettingsView.fxml"));
                    materialView.getChildren().add(v);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            initialized=true;
        }

    }

    public void handleDiffuseColor(ActionEvent actionEvent) {
    }

    public void handleDiffuseTexture(ActionEvent actionEvent) {
    }

    public void handleNormalScale(ActionEvent actionEvent) {
    }

    public void handleNormalValue(ActionEvent actionEvent) {
    }

    public void handleSpecularColor(ActionEvent actionEvent) {
    }

    public void handleSpecularTexture(ActionEvent actionEvent) {
    }

    public void handleExponentScale(ActionEvent actionEvent) {
    }

    public void handleRoughnessScale(ActionEvent actionEvent) {

    }

    public void handleReflectionrColor(ActionEvent actionEvent) {
    }

    public void handleReflectionTexture(ActionEvent actionEvent) {
    }

    public void handleIORIndex(ActionEvent actionEvent) {
    }
}
