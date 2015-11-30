package geometries;

import matVect.Normal3;
import matVect.Point3;
import material.Material;
import utils.Hit;
import utils.Ray;

/**
 * This class represents a Sphere Object.
 *
 * @author Robert Dziuba on 25/10/15.
 */
public class Sphere extends Geometry {
    /**
     * The point of center of the Sphere.
     */
    public final Point3 c;
    /**
     * The radius of the Sphere.
     */
    public final double r;


    /**
     * Instantiates a new Sphere Object.
     *
     * @param material of the Sphere. Can't be null.
     * @param c        of the Sphere. Can't be null.
     * @param r        of the Sphere. Greater 0.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Sphere(final Point3 c, double r, final Material material) {
        super(material);
        if (c == null) {
            throw new IllegalArgumentException("The c cannot be null!");
        }
        if (r <= 0) {
            throw new IllegalArgumentException("The r cannot be 0 or lower!");
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
                //TODO statt c - r.at(t)*-1 -> r.at(t)-c
                Normal3 n = this.c.sub(r.at(Math.min(t1, t2))).mul(-1).normalized().asNormal();
                return new Hit(Math.min(t1, t2), n, r, this);
            } else if (t1 >= 0) {
                Normal3 n = this.c.sub(r.at(t1)).mul(-1).normalized().asNormal();
                return new Hit(t1, n, r, this);
            } else if (t2 >= 0) {
                Normal3 n = this.c.sub(r.at(t2)).mul(-1).normalized().asNormal();
                return new Hit(t2, n, r, this);
            }
        } else if (d == 0) {
            final double t = -b / (2 * a);
            Normal3 n = this.c.sub(r.at(t)).mul(-1).normalized().asNormal();
            if (t >= 0) {

                return new Hit(t, n, r, this);
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "c=" + c +
                ", r=" + r +
                '}' + "material=" + material +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sphere sphere = (Sphere) o;

        if (Double.compare(sphere.r, r) != 0) return false;
        if (!c.equals(sphere.c)) return false;
        return material.equals(sphere.material) && name.equals(sphere.name);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = c.hashCode();
        temp = Double.doubleToLongBits(r);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
