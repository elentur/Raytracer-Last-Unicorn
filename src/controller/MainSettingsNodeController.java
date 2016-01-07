package controller;

import geometries.Node;
import geometries.ShapeFromFile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 07.01.2016.
 *
 * @author Marcus Bätz
 */
public class MainSettingsNodeController extends AController {
    @FXML
    private VBox nodeView;

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
                if(((Node) selectedElement.get()).geos.get(0) instanceof ShapeFromFile) {
                    v = loader.load(getClass().getResource("/fxml/mainSettingsShapeFromFileView.fxml"));
                    nodeView.getChildren().add(0,v);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            initialized=true;
        }
    }
}
