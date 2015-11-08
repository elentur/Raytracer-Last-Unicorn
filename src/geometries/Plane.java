package geometries;

import matVect.Normal3;
import matVect.Point3;
import utils.Color;
import utils.Ray;
import utils.Hit;

/**
 * This class represents a Plane Object.
 * @author Robert Dziuba on 25/10/15.
 */
public class Plane extends Geometry {
    /**
     * A known Point.
     */
    protected final Point3 a;
    /**
     * A Normal of the Plane.
     */
    protected final Normal3 n;

    /**
     * Instantiates a new Plane Object.
     * @param color of the Plane. Can't be null.
     * @param a of the Plane. Can't be null.
     * @param n of the Plane. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Plane(final Color color, final Point3 a, final Normal3 n) {
        super(color);

        if (a == null) {
            throw new IllegalArgumentException("The a cannot be null!");
        }
        if (n == null) {
            throw new IllegalArgumentException("The n cannot be null!");
        }

        this.a = a;
        this.n = n;
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The r cannot be null!");
        }

        // t = ((a - o) · n)/ (d · n)
        final double nenner = r.d.dot(n);

        if (nenner != 0) {
            final double t = n.dot(a.sub(r.o)) / nenner;
            if (t > 0) return new Hit(t, r, this);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "a=" + a +
                ", n=" + n +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plane plane = (Plane) o;

        if (a != null ? !a.equals(plane.a) : plane.a != null) return false;
        return !(n != null ? !n.equals(plane.n) : plane.n != null);

    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (n != null ? n.hashCode() : 0);
        return result;
    }
}
