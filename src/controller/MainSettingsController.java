package controller;

import camera.Camera;
import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainSettingsController extends AController {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @FXML
    private TextField txtName;
    @FXML
    private VBox mainSettingsView;
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        txtName.setText(selectedElement.getValue().name);
        txtName.setOnAction(a->{
           selectedElement.getValue().name=txtName.getText();
            NodeTreeViewController.refresh();
        });
        try {
        if(selectedElement.get() instanceof Camera){
            VBox v = null;
            v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsCameraView.fxml"));
            mainSettingsView.getChildren().add(v);
           if(selectedElement.get() instanceof OrthographicCamera){
               v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsOrthographicCameraView.fxml"));
               mainSettingsView.getChildren().add(v);
           }else if(selectedElement.get() instanceof PerspectiveCamera){
               v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsPerspectiveCameraView.fxml"));
               mainSettingsView.getChildren().add(v);
           }else {
               v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsDOFCameraView.fxml"));
               mainSettingsView.getChildren().add(v);
           }

        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
