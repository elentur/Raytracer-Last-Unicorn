package camera;

import matVect.Point2;
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
public class OrthographicCamera extends Camera {
    /**
     * scale factor of the of imagescene
     */
    public final double s;


    private Set<Ray> rays;

    /**
     * Constructor initializes e. g and t.
     *
     * @param e eye position
     * @param g gaze vector (Blickrichtung)
     * @param t up vector
     * @param s the scale factor(greater than 0)
     */
    public OrthographicCamera(final Point3 e, final Vector3 g, final Vector3 t, final double s, final SamplingPattern samplingPattern) {
        super(e, g, t, samplingPattern);
        if (s <= 0) throw new IllegalArgumentException("s must not be 0 or lower");
        this.s = s;

    }

    @Override
    public String toString() {
        return "OrthographicCamera{" +
                "s=" + s +
                '}';
    }

    @Override
    public Set<Ray> rayFor(final int w, final int h, final int x, final int y) {
        if (w <= 0) throw new IllegalArgumentException("w must not be 0 or lower");
        if (h <= 0) throw new IllegalArgumentException("h must not be 0 or lower");
        if (x < 0 || x >= w) throw new IllegalArgumentException("x have to be between 0 and w");
        if (y < 0 || y >= h) throw new IllegalArgumentException("y have to be between 0 and h");

        double aspectRatio = (double) w / (double) h;

        for(Point2 point : samplingPattern.points) {

            double scalar1 = aspectRatio * s * (x - (w - 1) / 2) / (w - 1);
            double scalar2 = s * (y - (h - 1) / 2) / (h - 1);

            final Point3 o = this.e.add(this.u.mul(scalar1).mul(point.x)).add(this.v.mul(scalar2).mul(point.y));
            final Vector3 d = this.w.mul(-1);


            rays.add(new Ray(o, d));
        }

        return rays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrthographicCamera that = (OrthographicCamera) o;

        return Double.compare(that.s, s) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(s);
        return (int) (temp ^ (temp >>> 32));
    }

}
