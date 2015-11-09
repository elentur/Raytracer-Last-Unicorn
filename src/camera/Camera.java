package camera;

import matVect.Point3;
import matVect.Vector3;
import utils.Ray;

/**
 * Created by Marcus Baetz on 03.11.2015.
 * This class represents a beam which has an origin and a direction.
 * @author Marcus Bätz
 */
public abstract class Camera {
    /**
     * the eye position
     */
    public Point3 e;
    /**
     * the gaze direction (Blickrichtung)
     */
    public Vector3 g;
    /**
     * the up-vector
     */
    public Vector3 t;

    /**
     * u-axis of the local coordinate-system
     */
    public Vector3 u;

    /**
     * v-axis of the local coordinate-system
     */
    public Vector3 v;

    /**
     * w-axis of the local coordinate-system
     */
    public Vector3 w;

    /**
     * Constructor initializes e. g and t.
     *
     * @param e
     *            eye position
     * @param g
     *            gaze vector (Blickrichtung)
     * @param t
     *            up vector
     */
    public Camera(final Point3 e, final Vector3 g, final Vector3 t) {
        this.e = e;
        this.g = g;
        this.t = t;

        this.w = this.g.normalized().mul(-1.0);

        this.u = this.t.x(this.w).normalized();

        this.v = this.w.x(this.u).mul(-1);
    }

    /**
     * Return a beam for certain pixel.
     *
     * @param w
     *            width of image
     * @param h
     *            height of image
     * @param x
     *            x-coordinate of pixel
     * @param y
     *            y-coordinate of pixel
     * @return beam for certain pixel
     */
    public abstract Ray rayFor(final int w, final int h, final int x, final int y);

}
