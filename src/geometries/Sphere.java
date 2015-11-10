package geometries;

import matVect.Point3;
import utils.Color;
import utils.Ray;
import utils.Hit;

/**
 * This class represents a Sphere Object.
 *
 * @author Robert Dziuba on 25/10/15.
 */
public class Sphere extends Geometry {
    /**
     * The point of center of the Sphere.
     */
    private final Point3 c;
    /**
     * The radius of the Sphere.
     */
    private final double r;

    /**
     * Instantiates a new Sphere Object.
     *
     * @param color of the Sphere. Can't be null.
     * @param c     of the Sphere. Can't be null.
     * @param r     of the Sphere. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Sphere(final Point3 c, double r, final Color color) {
        super(color);
        if (c == null) {
            throw new IllegalArgumentException("The c cannot be null!");
        }
        this.c = c;
        this.r = r;
    }

    @Override
    public Hit hit(Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The r cannot be null!");
        }

        // t = (-b +- wurzel b2 - 4 * ac) / 2a
        final double a = r.d.dot(r.d);
        final double b = r.d.dot(r.o.sub(c).mul(2));
        final double cn = r.o.sub(c).dot(r.o.sub(c)) - (this.r * this.r);

        // d = b2 - 4ac
        final double d = (b * b) - (4 * a * cn);

        if (d > 0) {
            final double t1 = (-b + Math.sqrt(d)) / (2 * a);
            final double t2 = (-b - Math.sqrt(d)) / (2 * a);
            if (t1 >= 0 && t2 >= 0) {
                return new Hit(Math.min(t1, t2), r, this);
            } else if (t1 >= 0) {
                return new Hit(t1, r, this);
            } else if (t2 >= 0) {
                return new Hit(t2, r, this);
            }
        } else if (d == 0) {
            final double t = -b / (2 * a);
            if (t >= 0) {
                return new Hit(t, r, this);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "c=" + c +
                ", r=" + r +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sphere sphere = (Sphere) o;

        if (Double.compare(sphere.r, r) != 0) return false;
        return !(c != null ? !c.equals(sphere.c) : sphere.c != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = c != null ? c.hashCode() : 0;
        temp = Double.doubleToLongBits(r);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
