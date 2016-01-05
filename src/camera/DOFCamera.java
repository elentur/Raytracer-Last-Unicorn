package camera;

import matVect.Point2;
import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;
import utils.Ray;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class DOFCamera extends Camera {
    /**
     * the opening angle
     */
    public final double angle;
    private final double focalLength;
    private final double fStop;
    private Set<Ray> rays;

    /**
     * Constructor initializes e. g and t.
     *
     * @param e     eye position
     * @param g     gaze vector (gaze direction)
     * @param t     up vector
     * @param angle the opening angle between ]0 and PI/2]
     */
    public DOFCamera(final Point3 e, final Vector3 g, final Vector3 t, final double angle,final double focalLength,final double fStop, final SamplingPattern samplingPattern) {
        super(e, g, t, samplingPattern);
        if (angle <= 0 || angle > Math.PI / 2)
            throw new IllegalArgumentException("angle have to be greater than 0 and lower than PI/2");
        this.angle = angle;
        this.focalLength = focalLength;
        this.fStop = fStop;
    }

    @Override
    public Set<Ray> rayFor(final int w, final int h, final int x, final int y) {
        if (w <= 0) throw new IllegalArgumentException("w must not be 0 or lower");
        if (h <= 0) throw new IllegalArgumentException("h must not be 0 or lower");
        if (x < 0 || x >= w) throw new IllegalArgumentException("x have to be between 0 and w");
        if (y < 0 || y >= h) throw new IllegalArgumentException("y have to be between 0 and h");

        rays = new HashSet<>();

        final Vector3 summand1 = this.w.mul(-1).mul((h * 1.0 / 2) / Math.tan(angle / 2));
        final Vector3 summand2 = this.u.mul(x - ((w - 1.0) / 2));
        final Vector3 summand3 = this.v.mul(y - ((h - 1.0) / 2));

        for(Point2 point : samplingPattern.points) {
            final Vector3 r = summand1.add(summand2).add(summand3).add(this.u.mul(point.x)).add(this.v.mul(point.y));
            rays.add(new Ray(this.e, r.normalized()));
        }

        return rays;
    }

    @Override
    public String toString() {
        return "PerspectiveCamera{" +
                "angle=" + angle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DOFCamera that = (DOFCamera) o;

        return Double.compare(that.angle, angle) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(angle);
        return (int) (temp ^ (temp >>> 32));
    }


}
