package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import observables.cameras.AOCamera;
import observables.geometries.ONode;
import observables.lights.AOLight;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainSettingsController extends AController {

    @FXML
    private TextField txtName;
    @FXML
    private VBox mainSettingsView;

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
        txtName.setText(selectedTreeItem.get().getValue().name.getValue());
        txtName.textProperty().bindBidirectional(selectedTreeItem.get().getValue().name);
        try {
            VBox v;
            if (selectedTreeItem.get().getValue() instanceof AOCamera) {
                v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsCameraView.fxml"));
                mainSettingsView.getChildren().add(v);

            } else if (selectedTreeItem.get().getValue() instanceof AOLight) {
                v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsLightView.fxml"));
                mainSettingsView.getChildren().add(v);
            } else if (selectedTreeItem.get().getValue() instanceof ONode) {
                v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsNodeView.fxml"));
                mainSettingsView.getChildren().add(v);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
