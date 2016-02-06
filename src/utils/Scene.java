package utils;

import observables.cameras.AOCamera;
import observables.geometries.AOGeometry;
import observables.lights.AOLight;
import serializable.SElement;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
    private final List<SElement> lights;

    /**
     * Generates a new Scene object.
     *
     * @param geometries Represents the Geometries of this Scene
     * @param lights     Represents the lights of this Scene
     * @param camera     Represents the Camera of this Scene
     */
    public Scene(final List<SElement> geometries, final List<SElement> lights, final SElement camera) {
        this.camera = camera;
        this.geometries = geometries;
        this.lights = lights;
    }

    /**
     * @return the TreeView object of this Scene.
     */
    public AOCamera getCamera() {

        return camera != null ? (AOCamera) camera.generate() : null;
    }

    /**
     * @return the TreeView object of this Scene.
     */
    public List<AOLight> getLights() {
        return lights.stream().map(light -> (AOLight) light.generate()).collect(Collectors.toList());
    }

    /**
     * @return the TreeView object of this Scene.
     */
    public List<AOGeometry> getGeometries() {
        return geometries.stream().map(geometry -> (AOGeometry) geometry.generate()).collect(Collectors.toList());
    }
}
