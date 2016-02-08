package camera;

import matVect.Point2;
import matVect.Point3;
import matVect.Vector3;
import sampling.DOFPattern;
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
    private final double angle;
    /**
     * The Patern relevant for Depth-of-Field Calculation
     * includes subdiv for sampling rate and fStop for the amount of sharpness
     */
    private final DOFPattern dofPattern;
    /**
     * represents the range between the camera and the focused point
     */
    private final double focalLength;

    /**
     * Constructor initializes e. g and t.
     *
     * @param e     eye position
     * @param g     gaze vector (gaze direction)
     * @param t     up vector
     * @param angle the opening angle between ]0 and PI/2]
     * @param dofPattern the Sampling pattern for DOF calculations
     * @param focalLength the range between the camera and the focused point
     * @param samplingPattern the Sampling Pattern of the camera
     */
    public DOFCamera(final Point3 e, final Vector3 g, final Vector3 t, final double angle, final DOFPattern dofPattern, final double focalLength, final SamplingPattern samplingPattern) {
        super(e, g, t, samplingPattern);
        if (angle <= 0 || angle > Math.PI / 2)
            throw new IllegalArgumentException("angle have to be greater than 0 and lower than PI/2");
        this.angle = angle;
        this.dofPattern = dofPattern;
        this.focalLength = focalLength;
    }


    @Override
    public Set<Ray> rayFor(final int w, final int h, final int x, final int y) {
        if (w <= 0) throw new IllegalArgumentException("w must not be 0 or lower");
        if (h <= 0) throw new IllegalArgumentException("h must not be 0 or lower");
        if (x < 0 || x >= w) throw new IllegalArgumentException("x have to be between 0 and w");
        if (y < 0 || y >= h) throw new IllegalArgumentException("y have to be between 0 and h");

        final Set<Ray> rays = new HashSet<>();

        final Vector3 summand1 = this.w.mul(-1).mul((h * 1.0 / 2) / Math.tan(angle / 2));

        for (Point2 point : samplingPattern.generateSampling()) {
            final Vector3 summand2 = this.u.mul(x + point.x - ((w - 1.0) / 2));
            final Vector3 summand3 = this.v.mul(y + point.y - ((h - 1.0) / 2));
            final Vector3 r = summand1.add(summand2).add(summand3).add(this.u.mul(point.x)).add(this.v.mul(point.y));
            final Ray ray = new Ray(this.e, r.normalized());
            rays.add(ray);
            final Point3 p = ray.at(focalLength);
            for (Point2 point1 : dofPattern.generateSampling()) {
                Point3 e1 = new Point3(this.e.x + point1.x, this.e.y + point1.y, this.e.z);
                final Vector3 r1 = p.sub(e1);
                rays.add(new Ray(e1, r1.normalized()));
            }
        }

        return rays;
    }

    @Override
    public String toString() {
        return "DOFCamera{" +
                "angle=" + angle +
                ", dofPattern=" + dofPattern +
                ", focalLength=" + focalLength +
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
