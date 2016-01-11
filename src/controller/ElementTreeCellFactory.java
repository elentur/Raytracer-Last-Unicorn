package controller;

import camera.Camera;
import geometries.Geometry;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import light.Light;
import utils.Element;

/**
 * Created by Marcus Baetz on 11.01.2016.
 *
 * @author Marcus Bätz
 */
public class ElementTreeCellFactory implements Callback<TreeView<Element>, TreeCell<Element>> {


    @Override
    public TreeCell<Element> call(TreeView<Element> p) {
        return new TreeCell<Element>() {
            @Override
            protected void updateItem(Element item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null || !empty) {
                    setText(item.name);
                    if (item instanceof Geometry) {
                        setGraphic(new ImageView(new Image("file:icons/mesh.png")));

                    } else if (item instanceof Light) {
                        setGraphic(new ImageView(new Image("file:icons/light.png")));

                    } else if (item instanceof Camera) {
                        setGraphic(new ImageView(new Image("file:icons/camera.png")));

                    } else {
                        setGraphic(null);
                    }
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        };
    }
}
