package observables.cameras;

import camera.Camera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import observables.AOElement;
import serializable.cameras.SCamera;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Abstract observable class AOCamera
 */
public abstract class AOCamera extends AOElement {
    /**
     * represents e x coordinate
     */
    public final DoubleProperty ex = new SimpleDoubleProperty(0);
    /**
     * represents e y coordinate
     */
    public final DoubleProperty ey = new SimpleDoubleProperty(0);
    /**
     * represents e z coordinate
     */
    public final DoubleProperty ez = new SimpleDoubleProperty(5);

    /**
     * represents g x coordinate
     */
    public final DoubleProperty gx = new SimpleDoubleProperty(0);
    /**
     * represents g y coordinate
     */
    public final DoubleProperty gy = new SimpleDoubleProperty(0);
    /**
     * represents g z coordinate
     */
    public final DoubleProperty gz = new SimpleDoubleProperty(-1);

    /**
     * represents t x coordinate
     */
    public final DoubleProperty tx = new SimpleDoubleProperty(0);
    /**
     * represents t y coordinate
     */
    public final DoubleProperty ty = new SimpleDoubleProperty(1);
    /**
     * represents t z coordinate
     */
    public final DoubleProperty tz = new SimpleDoubleProperty(0);
    /**
     * represent the SamplingPattern Subdivs
     */
    public final IntegerProperty patternSubdiv = new SimpleIntegerProperty(1);


    public AOCamera() {
        tx.addListener(a -> {
            checkT();
        });

        ty.addListener(a -> {
            checkT();
        });

        tz.addListener(a -> {
            checkT();
        });

        gx.addListener(a -> {
            checkG();
        });

        gy.addListener(a -> {
            checkG();
        });

        gz.addListener(a -> {
            checkG();
        });


    }

    /**
     * checks if the given values creates a vector
     * if not it creates a vector
     */
    private void checkT() {
        if (tx.get() == 0 && ty.get() == 0 && tz.get() == 0)
            ty.set(1);
    }

    /**
     * checks if the given values creates a vector
     * if not it creates a vector
     */
    private void checkG() {
        if (gx.get() == 0 && gy.get() == 0 && gz.get() == 0)
            gz.set(1);
    }


    /**
     * Creates a new Camera for rendering
     *
     * @return a Camera-Object
     */
    public abstract Camera generate();

    /**
     * Creates a new SCamera for saving
     *
     * @return a SCameraObject
     */
    public abstract SCamera serialize();
}
