package utils;

import serializable.SElement;

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
    private final List<SElement> geometries;
    private  final List<SElement> lights;

    /**
     * Generates a new Scene object.
     * @param geometries
     * @param lights
     * @param camera  Represents the treeview object of this Scene.
     */
    public Scene(final List<SElement> geometries, final List<SElement> lights, final SElement camera) {
        this.camera = camera;
        this.geometries=geometries;
        this.lights=lights;
    }

    /**
     * @return the TreeView object of this Scene.
     */
    public SElement getSerializableCamera() {

        return camera;
    }
}
