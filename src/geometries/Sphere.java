package geometries;

import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import material.Material;
import texture.InterpolatedImageTexture;
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
    public final Point3 c;
    /**
     * The radius of the Sphere.
     */
    public final double r;

    /**
     * Instantiates a new Sphere Object.
     *
     * @param material of the Sphere. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Sphere(final Material material, final boolean reciveShadows, final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        super(material,reciveShadows,castShadows,visibility,flipNormal);
        this.name = "Sphere";
        this.c = new Point3(0,0,0);
        this.r = 1;
    }

    /**
     * Copy Constructor
     *
     * @param sphere
     */
    public Sphere(Sphere sphere) {
        super(sphere.material, sphere.reciveShadows, sphere.castShadows, sphere.visibility, sphere.flipNormal);
        this.c = sphere.c;
        this.r = sphere.r;
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

        double t = -1;
        // change the normal if we are inside the sphere.

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
            final double u = 0.5 - Math.atan2(n.z,n.x)/(2*Math.PI);

            final double v = 0.5 - 2.0 * (Math.asin(n.y)/(2*Math.PI));
            Color normalC = material.bumpMap.getColor(u,v);
            Vector3 nc = new Vector3(normalC.r * 2 - 1, normalC.g * 2 - 1, normalC.b * 2 - 1).normalized();
            Normal3 n1 = new Vector3( n.x+nc.x*material.bumpScale, n.y+nc.y*material.bumpScale, n.z).normalized().asNormal();
            if (flipNormal) n1 = n1.mul(-1);
            return new Hit(t, n1, r, this, new TexCoord2(u, v));


        }
        /*Vector3 l = c.sub(r.o);
        double s = l.dot(r.d.normalized());
        double l2 = l.dot(l);
        double r2 = this.r*this.r;
        if(s < 0 && l2 > r2) return null;
        double m2 = l2 -s*s;
        if(m2> r2) return null;
        double q= Math.sqrt(r2-m2);
        double t = s-q;
        Normal3 n = r.at(t).sub(this.c).normalized().asNormal();
        if(l2 -0.00001<r2){
            t = s+q;
            n = r.at(t).sub(this.c).normalized().asNormal();
        }

        if(t > 0){
            return new Hit(t, n, r, this);
        }*/
        return null;
    }

    @Override
    public Sphere deepCopy() {
        return new Sphere(this);
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
