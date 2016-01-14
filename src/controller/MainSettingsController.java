package controller;

import camera.Camera;
import geometries.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import light.Light;
import utils.Element;

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
        txtName.setText(selectedTreeItem.get().getValue().name);
        txtName.setOnAction(a->{
            Element e = selectedTreeItem.get().getValue().deepCopy();
            e.name=txtName.getText();
           ObservableElementLists.getInstance().updateElement(selectedTreeItem.get().getValue(),e);
        });
        try {
        if(selectedTreeItem.get().getValue() instanceof Camera){
            VBox v= FXMLLoader.load(getClass().getResource("/fxml/mainSettingsCameraView.fxml"));
            mainSettingsView.getChildren().add(v);

        }else if(selectedTreeItem.get().getValue() instanceof Light){
            VBox v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsLightView.fxml"));
            mainSettingsView.getChildren().add(v);
        }else if(selectedTreeItem.get().getValue() instanceof Node) {
            VBox v;
            v = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsNodeView.fxml"));
            mainSettingsView.getChildren().add(v);
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
