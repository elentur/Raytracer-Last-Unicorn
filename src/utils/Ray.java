package utils;

import matVect.Point3;
import matVect.Vector3;

/**
 * Created by Marcus Baetz on 03.11.2015.
 *
 * @author Marcus Bätz
 */
public class Ray {
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
    public Ray(final Point3 o,final Vector3 d){
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
    public Point3 at(final double t){
        return this.o.add(this.d.mul(t));
    }

    public double tOf(final Point3 p){
        if (p == null) {
            throw new IllegalArgumentException("The parameter "
                    + "’p’ must not be null.");
        }
        return p.sub(this.o).magnitude / this.d.magnitude;
    }
}
