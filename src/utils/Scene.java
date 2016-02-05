package utils;

import camera.Camera;
import com.sun.corba.se.impl.orbutil.ObjectWriter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import observables.AOElement;
import observables.cameras.AOCamera;
import observables.geometries.AOGeometry;
import observables.lights.AOLight;
import serializable.SElement;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Combines a World object and a Camera object for saving and loading as a File.
 * Created by Marcus Baetz on 08.11.2015.
 *
 * @author Marcus Bätz
 */
public class Scene implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Represents the AOElments Treeview object of this Scene.
     */
    private final SElement camera;

    /**
     * Generates a new Scene object.

     * @param camera  Represents the treeview object of this Scene.
     */
    public Scene(SElement camera) {
        this.camera = camera;
    }

    /**
     * @return the TreeView object of this Scene.
     */
    public SElement getSerializableCamera() {

        return camera;
    }
}
