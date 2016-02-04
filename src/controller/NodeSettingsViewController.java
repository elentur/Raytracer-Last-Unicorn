package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import observables.AOElement;
import observables.cameras.AOCamera;
import observables.geometries.AOGeometry;
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
public class NodeSettingsViewController extends AController {

    @FXML
    private TabPane tabPane;

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
        selectedTreeItem.addListener(a -> handleSelectedElement());
        masterTabPane = tabPane;
    }

    private void handleSelectedElement() {
        tabPane.setVisible(true);
        tabPane.getTabs().clear();
        AOElement e = null;
        if(selectedTreeItem.get() != null)  e = selectedTreeItem.get().getValue();

        if (!(e instanceof AOLight) && !(e instanceof AOCamera) && !(e instanceof AOGeometry)) {
            tabPane.setVisible(false);

        } else {

            try {

                Tab t = FXMLLoader.load(getClass().getResource("/fxml/mainSettingsView.fxml"));
                tabPane.getTabs().addAll(t);
                if (e instanceof ONode) t.setText("Node");
                else if (e instanceof AOLight) t.setText("Light");
                else if (e instanceof AOCamera) t.setText("Camera");

                if (e instanceof AOGeometry) {
                    if (!((ONode) e).oGeos.isEmpty() && !(((ONode) e).oGeos.get(0) instanceof ONode)) {
                        Tab t2 = FXMLLoader.load(getClass().getResource("/fxml/mainMaterialSettingsView.fxml"));
                        t2.setText("Material");
                        tabPane.getTabs().add(1, t2);
                        tabPane.getSelectionModel().select(0);
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
