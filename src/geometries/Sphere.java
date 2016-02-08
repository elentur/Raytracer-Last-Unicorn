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
     * @param material of the Sphere. Can't be null.
     * @param receiveShadows  boolean if Geometry receives Shadows
     * @param castShadows boolean if Geometry cast shadows
     * @param visibility boolean if Geometry is visible
     * @param flipNormal boolean if Geometry need to flip Normals direction
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Sphere(final Material material, final boolean receiveShadows, final boolean castShadows, final boolean visibility, final boolean flipNormal) {
        super(material, receiveShadows, castShadows, visibility, flipNormal);
        this.c = new Point3(0, 0, 0);
        this.r = 1;
    }


    @Override
    public Hit hit(Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The r cannot be null!");
        }

        final double a = r.d.dot(r.d);
        final double b = r.d.dot(r.o.sub(c).mul(2));
        final double cn = r.o.sub(c).dot(r.o.sub(c)) - (this.r * this.r);


        final double d = (b * b) - (4 * a * cn);

        double t = -1;


        if (d > 0) {
            final double t1 = (-b + Math.sqrt(d)) / (2 * a);
            final double t2 = (-b - Math.sqrt(d)) / (2 * a);
            if (t1 > 0.00001 && t2 > 0.00001) {
                t = Math.min(t1, t2);
            } else if (t1 > 0.00001) {
                t = t1;
            } else if (t2 > 0.00001) {
                t = t2;
            }
        } else if (d == 0) {
            t = -b / (2 * a);
        }

        if (t >= 0.00001) {
            Normal3 n = r.at(t).sub(this.c).normalized().asNormal();
            final double u = 0.5 - Math.atan2(n.z, n.x) / (2 * Math.PI);

            final double v = 0.5 - 2.0 * (Math.asin(n.y) / (2 * Math.PI));
            Color normalC = material.bumpMap.getColor(u, v);
            Vector3 nc = new Vector3(normalC.r * 2 - 1, normalC.g * 2 - 1, normalC.b * 2 - 1).normalized();
            Normal3 n1 = new Vector3(n.x + nc.x * material.bumpScale, n.y + nc.y * material.bumpScale, n.z).normalized().asNormal();
            if (flipNormal) n1 = n1.mul(-1);
            return new Hit(t, n1, r, this, new TexCoord2(u, v));


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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Sphere)) return false;

        Sphere sphere = (Sphere) o;

        if (Double.compare(sphere.r, r) != 0) return false;
        return !(c != null ? !c.equals(sphere.c) : sphere.c != null);

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
