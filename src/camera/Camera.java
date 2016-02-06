package camera;

import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;
import utils.Ray;

import java.util.Set;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Camera {
    /**
     * the eye position
     */
    Point3 e;
    /**
     * the gaze direction (Blickrichtung)
     */
    private Vector3 g;
    /**
     * the up-vector
     */
    private Vector3 t;

    /**
     * u-axis of the local coordinate-system
     */
    Vector3 u;

    /**
     * v-axis of the local coordinate-system
     */
    Vector3 v;

    /**
     * w-axis of the local coordinate-system
     */
    Vector3 w;

    /**
     * the Sampling Pattern of the camera
     */
    SamplingPattern samplingPattern;

    /**
     * constructor initializes e. g and t.
     *
     * @param e eye position
     * @param g gaze vector (Blickrichtung)
     * @param t up vector
     */
    Camera(final Point3 e, final Vector3 g, final Vector3 t, final SamplingPattern samplingPattern) {
        if (e == null) throw new IllegalArgumentException("e must not be null");
        if (g == null) throw new IllegalArgumentException("e must not be null");
        if (t == null) throw new IllegalArgumentException("e must not be null");
        if (g.x == 0 && g.y == 0 && g.z == 0) throw new IllegalArgumentException("g must not be (0,0,0)");
        if (t.x == 0 && t.y == 0 && t.z == 0) throw new IllegalArgumentException("t must not be (0,0,0)");
        if (samplingPattern == null) throw new IllegalArgumentException("samplingPattern must not be null");

        this.e = e;
        this.g = g;
        this.t = t;

        this.w = this.g.normalized().mul(-1.0);
        this.u = this.t.x(this.w).normalized();
        this.v = this.w.x(this.u).mul(-1);
        this.samplingPattern = samplingPattern;
    }

    /**
     * returns a beam for certain pixel.
     *
     * @param w width of image
     * @param h height of image
     * @param x x-coordinate of pixel
     * @param y y-coordinate of pixel
     * @return beamset for certain pixel
     */
    public abstract Set<Ray> rayFor(final int w, final int h, final int x, final int y);


}
