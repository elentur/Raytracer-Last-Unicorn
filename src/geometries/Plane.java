package geometries;

import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import material.Material;
import texture.TexCoord2;
import utils.Color;
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
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Plane(final Material material,final boolean reciveShadows, final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        super(material,reciveShadows,castShadows,visibility,flipNormal);
        this.a = new Point3(0, 0, 0);
        this.n = new Normal3(0, 1, 0);
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

            final Point3 p = r.at(t);

            final double u = p.x+0.5;
            final double v = p.z+0.5;

            Color normalC = material.bumpMap.getColor(u,v);
            Vector3 nc = new Vector3(normalC.r * 2 - 1, normalC.g * 2 - 1, normalC.b * 2 - 1).normalized();
            Normal3 n1 = new Vector3( n.x+nc.x*material.bumpScale, n.y+nc.y*material.bumpScale, n.z).normalized().asNormal();
            if (flipNormal) n1 = n1.mul(-1);
            if (t > 0) return new Hit(t, n1, r, this, new TexCoord2(u,v));
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;

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
