package camera;

import matVect.Point3;
import matVect.Vector3;
import utils.Ray;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class OrthographicCamera extends Camera {
    /**
     * scale factor of the of imagescene
     */
    public final double s;
    /**
     * Constructor initializes e. g and t.
     *
     * @param e
     *            eye position
     * @param g
     *            gaze vector (Blickrichtung)
     * @param t
     *            up vector
     * @param s
     *            the scale factor
     *
     */
    public OrthographicCamera(final Point3 e,final Vector3 g,final Vector3 t,final double s) {
        super(e, g, t);
        this.s =s;

    }

    @Override
    public Ray rayFor(final int w,final int h,final int x,final int y) {
        double aspectRatio = (double) w / (double) h;
        double scalar1 = aspectRatio * s * (x - (w - 1) / 2) / (w - 1);
        double scalar2 = s * (y - (h - 1) / 2) / (h - 1);

        final Point3 o = this.e.add(this.u.mul(scalar1)).add(
                this.v.mul(scalar2));
        final Vector3 d = this.w.mul(-1);

        return new Ray(o, d);
    }
}
