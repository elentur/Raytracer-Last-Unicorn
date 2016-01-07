package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import light.DirectionalLight;
import light.PointLight;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 07.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainSettingsLightController extends  AController{
    @FXML
    private VBox lightView;
    private boolean initialized = false;
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
        if(!initialized) {
            VBox v;
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            try {
                if(selectedElement.get() instanceof DirectionalLight){
                    v = loader.load(getClass().getResource("/fxml/mainSettingsDirectionalLightView.fxml"));
                    lightView.getChildren().add(v);
                }else if(selectedElement.get() instanceof PointLight){
                    v = loader.load(getClass().getResource("/fxml/mainSettingsPointLightView.fxml"));
                    lightView.getChildren().add(v);
                }else {
                    v = loader.load(getClass().getResource("/fxml/mainSettingsPointLightView.fxml"));
                    lightView.getChildren().add(v);
                    v = loader.load(getClass().getResource("/fxml/mainSettingsDirectionalLightView.fxml"));
                    lightView.getChildren().add(v);
                    v = loader.load(getClass().getResource("/fxml/mainSettingsSpotLightView.fxml"));
                    lightView.getChildren().add(v);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            initialized=true;
        }
    }
}
