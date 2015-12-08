package geometries;

import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import material.Material;
import texture.TexCoord2;
import utils.Hit;
import utils.Ray;

/**
 * This class represents a Plane Object.
 *
 * @author Robert Dziuba on 25/10/15.
 */
public class Plane extends Geometry {
    /**
     * A known Point.
     */
    public final Point3 a;
    /**
     * A Normal of the Plane.
     */
    public final Normal3 n;

    /**
     * Instantiates a new Plane Object.
     *
     * @param material of the Plane. Can't be null.
     * @param a        of the Plane. Can't be null.
     * @param n        of the Plane. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Plane(final Point3 a, final Normal3 n, final Material material) {
        super(material);

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
            Point3 p = r.at(t);

            Vector3 uv = a.sub(p);

            double l = uv.magnitude;

            double u = Math.abs(p.x*l);
            double v = Math.abs(p.z*l);

            u = u-(int)u;
            v = v-(int)v;

            System.out.println(u);
            System.out.println(v);

            if (t > 0) return new Hit(t, n, r, this, new TexCoord2(u,v));
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
        if (n != null ? !n.equals(plane.n) : plane.n != null) return false;
        return material.equals(plane.material) && name.equals(plane.name);

    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (n != null ? n.hashCode() : 0);
        return result;
    }

}
