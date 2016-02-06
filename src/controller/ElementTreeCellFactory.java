package controller;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import observables.AOElement;
import observables.cameras.AOCamera;
import observables.geometries.AOGeometry;
import observables.lights.AOLight;

/**
 * Created by Marcus Baetz on 11.01.2016.
 *
 * @author Marcus Bätz
 */
public class ElementTreeCellFactory implements Callback<TreeView<AOElement>, TreeCell<AOElement>> {


    @Override
    public TreeCell<AOElement> call(TreeView<AOElement> p) {
        return new TreeCell<AOElement>() {
            @Override
            protected void updateItem(AOElement item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null ) {
                    textProperty().bind(item.name);
                    if (item instanceof AOGeometry) {
                        setGraphic(new ImageView(new Image("icons/mesh.png")));

                    } else if (item instanceof AOLight) {
                        setGraphic(new ImageView(new Image("icons/light.png")));

                    } else if (item instanceof AOCamera) {
                        setGraphic(new ImageView(new Image("icons/camera.png")));

                    } else {
                        setGraphic(null);
                    }
                } else {
                    textProperty().unbind();
                    setText(null);
                    setGraphic(null);
                }
            }
        };
    }
}
