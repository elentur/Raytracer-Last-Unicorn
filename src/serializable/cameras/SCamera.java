package serializable.cameras;

import observables.cameras.AOCamera;
import serializable.SElement;

import java.io.Serializable;

/**
 * Abstract wrapper class for all serializable camera objects
 * Created by Marcus Baetz on 06.02.2016.
 *
 * @author Marcus Bätz
 */
public abstract class SCamera implements SElement, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The name of the camera.
     */
    final String name;
    /**
     * Coordinates for the eye position.
     */
    final double ex;
    final double ey;
    final double ez;
    /**
     * Coordinates for the gaze vector (Blickrichtung).
     */
    final double gx;
    final double gy;
    final double gz;
    /**
     * Coordinates for the up vector.
     */
    final double tx;
    final double ty;
    final double tz;
    /**
     * The Square number of the Rays for each pixel
     */
    final int subdiv;

    /**
     *
     * @param name of the camera.
     * @param ex coordinates for the eye position.
     * @param ey coordinates for the eye position.
     * @param ez coordinates for the eye position.
     * @param gx Coordinates for the gaze vector .
     * @param gy Coordinates for the gaze vector.
     * @param gz Coordinates for the gaze vector.
     * @param tx Coordinates for the up vector.
     * @param ty Coordinates for the up vector.
     * @param tz Coordinates for the up vector.
     * @param subdiv the Square number of the Rays for each pixel.
     */
    SCamera(final String name, final double ex, final double ey, final double ez,
            final double gx, final double gy, final double gz,
            final double tx, final double ty, final double tz,
            final int subdiv) {
        this.name = name;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.subdiv = subdiv;
    }

    @Override
    public abstract AOCamera generate();
}
