package utils;

import camera.Camera;

import java.io.Serializable;

/**
 * Combines a World object and a Camera object for saving and loading as a File.
 * Created by Marcus Baetz on 08.11.2015.
 *
 * @author Marcus Bätz
 */
public class Scene implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Represents the World object of this Scene.
     */
    private final World world;
    /**
     * Represents the Camera object of this Scene.
     */
    private final Camera camera;

    /**
     * Generates a new Scene object.
     *
     * @param world  Represents the World object of this Scene.
     * @param camera Represents the Camera object of this Scene.
     */
    public Scene(final World world, final Camera camera) {
        this.world = world;
        this.camera = camera;
    }

    /**
     * @return the World object of this Scene.
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return the Camera object of this Scene.
     */
    public Camera getCamera() {
        return camera;
    }
}
