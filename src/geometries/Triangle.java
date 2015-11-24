package geometries;

import matVect.Mat3x3;
import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import material.Material;
import utils.Hit;
import utils.Ray;

/**
 * This class represents a Triangle Object.
 *
 * @ Robert Dziuba on 03.11.15.
 */
public class Triangle extends Geometry {
    /**
     * The a corner point of the Triangle.
     */
    public final Point3 a;
    /**
     * The b corner point of the Triangle.
     */
    public final Point3 b;
    /**
     * The c corner point of the Triangle.
     */
    public final Point3 c;

    public final Normal3 na;
    public final Normal3 nb;
    public final Normal3 nc;


    /**
     * Instantiates a new Triangle Object.
     *
     * @param a     corner point of the Sphere. Can't be null.
     * @param b     corner point of the Sphere. Can't be null.
     * @param c     corner point of the Sphere. Can't be null.
     * @param material of the Sphere. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c,
                    final Normal3 na, final Normal3 nb, final Normal3 nc,
             final Material material) {
        super(material);
        if (a == null) {
            throw new IllegalArgumentException("The a cannot be null!");
        }
        if (b == null) {
            throw new IllegalArgumentException("The b cannot be null!");
        }
        if (c == null) {
            throw new IllegalArgumentException("The c cannot be null!");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        //TODO nur ein n
        this.na = na;
        /*
        Spezifiziere Normale an den Eckpunkten
         */
        this.nb =nb;
        this.nc = nc;
    }

    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Material material) {
        this(a,b,c,
                a.sub(b).x(c.sub(b)).normalized().asNormal().mul(-1),
                a.sub(b).x(c.sub(b)).normalized().asNormal().mul(-1),
                a.sub(b).x(c.sub(b)).normalized().asNormal().mul(-1),
                material);
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The r cannot be null!");
        }

        // M x = V

        final Vector3 v = new Vector3(a.x - r.o.x, a.y - r.o.y, a.z - r.o.z);

        final Mat3x3 m = new Mat3x3(
                a.x - b.x, a.x - c.x, r.d.x,
                a.y - b.y, a.y - c.y, r.d.y,
                a.z - b.z, a.z - c.z, r.d.z
        );

        final double detA = m.determinant;

        final double detA1 = m.col1(v).determinant;
        final double beta = detA1 / detA;

        if (beta >= 0 && beta <= 1) {

            final double detA2 = m.col2(v).determinant;
            final double gamma = detA2 / detA;

            if ( gamma > 0 && beta + gamma <= 1) {
                final double detA3 = m.col3(v).determinant;
                final double t = detA3 / detA;
                if (t > 0){
                    Normal3 n = na.mul(1- beta - gamma).add(nb.mul(beta)).add(nc.mul(gamma));
                    return new Hit(t,n, r, this);
                }
            }

        }

        return null;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Triangle triangle = (Triangle) o;

        if (a != null ? !a.equals(triangle.a) : triangle.a != null) return false;
        if (b != null ? !b.equals(triangle.b) : triangle.b != null) return false;
        if (c != null ? !c.equals(triangle.c) : triangle.c != null) return false;
        return material.equals(triangle.material) && name.equals(triangle.name);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (a != null ? a.hashCode() : 0);
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }


}
