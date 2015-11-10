package utils;

import matVect.Point3;
import matVect.Vector3;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class Ray implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * the origin of the beam
     */
    public final Point3 o;
    /**
     * the direction of the beam
     */
    public final Vector3 d;

    /**
     * Constructs a beam with a given origin and a direction
     *
     * @param o the origin of the beam
     * @param d the direction of the beam
     */
    public Ray(final Point3 o, final Vector3 d) {
        if (o == null || d == null) {
            throw new IllegalArgumentException("The parameter "
                    + "’o’ and/or d must not be null.");
        }
        this.o = o;
        this.d = d;
    }

    /**
     * Takes a t as skalar and returns a specified point
     *
     * @param t the skalar
     * @return a specified point
     */
    public Point3 at(final double t) {
        return this.o.add(this.d.mul(t));
    }

    public double tOf(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("The parameter "
                    + "’p’ must not be null.");
        }
        return p.sub(this.o).magnitude / this.d.magnitude;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "o=" + o +
                ", d=" + d +
                '}';
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;

        Ray ray = (Ray) o1;

        if (o != null ? !o.equals(ray.o) : ray.o != null) return false;
        return !(d != null ? !d.equals(ray.d) : ray.d != null);

    }

    @Override
    public int hashCode() {
        int result = o != null ? o.hashCode() : 0;
        result = 31 * result + (d != null ? d.hashCode() : 0);
        return result;
    }
}
