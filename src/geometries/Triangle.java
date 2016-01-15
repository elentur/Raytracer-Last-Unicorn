package geometries;

import matVect.Mat3x3;
import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import material.Material;
import texture.TexCoord2;
import utils.Color;
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

    private final TexCoord2 texCoordA;
    private final TexCoord2 texCoordB;
    private final TexCoord2 texCoordC;


    /**
     * Instantiates a new Triangle Object.
     *
     * @param a        corner point of the Sphere. Can't be null.
     * @param b        corner point of the Sphere. Can't be null.
     * @param c        corner point of the Sphere. Can't be null.
     * @param material of the Sphere. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c,
                    final Normal3 na, final Normal3 nb, final Normal3 nc,
                    final Material material,
                    final TexCoord2 texCoordA, final TexCoord2 texCoordB, final TexCoord2 texCoordC,
                    final boolean reciveShadows, final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        super(material, reciveShadows,castShadows,visibility,flipNormal);

        if (a == null) {
            throw new IllegalArgumentException("The a cannot be null!");
        }
        if (b == null) {
            throw new IllegalArgumentException("The b cannot be null!");
        }
        if (c == null) {
            throw new IllegalArgumentException("The c cannot be null!");
        }
        this.name = "Triangle";
        this.a = a;
        this.b = b;
        this.c = c;
        //TODO nur ein n
        this.na = na;
        /*
        Spezifiziere Normale an den Eckpunkten
         */
        this.nb = nb;
        this.nc = nc;

        this.texCoordA = texCoordA;
        this.texCoordB = texCoordB;
        this.texCoordC = texCoordC;
    }

    public Triangle(final Point3 a, final Point3 b, final Point3 c, final Material material, final TexCoord2 texCoordA, final TexCoord2 texCoordB, final TexCoord2 texCoordC, final boolean reciveShadows, final boolean castShadows, final boolean visibility,final boolean flipNormal) {
        this(a, b, c,
                a.sub(b).x(c.sub(b)).normalized().asNormal().mul(-1),
                a.sub(b).x(c.sub(b)).normalized().asNormal().mul(-1),
                a.sub(b).x(c.sub(b)).normalized().asNormal().mul(-1),
                material,
                texCoordA, texCoordB, texCoordC,reciveShadows,castShadows,
                visibility,flipNormal);
    }

    public Triangle(final Material material,final boolean reciveShadows, final boolean castShadows, final boolean visibility,final boolean flipNormal){
        this(
                new Point3(-0.5,1,0),
                new Point3(0.5,1,0),
                new Point3(0.5,0,0),
                new Normal3(0,0,1),
                new Normal3(0,0,1),
                new Normal3(0,0,1),
                material,
                new TexCoord2(0,1),
                new TexCoord2(1,1),
                new TexCoord2(1,0),
                reciveShadows,
                castShadows,
                visibility,
                flipNormal
        );
    }

    /**
     * Copy Constructor
     *
     * @param triangle
     */
    public Triangle(Triangle triangle) {
        this(triangle,triangle.material);
    }

    public Triangle(final Triangle triangle, final Material m) {
        super(m, triangle.reciveShadows,triangle.castShadows,triangle.visibility,triangle.flipNormal);
        this.a = triangle.a;
        this.b = triangle.b;
        this.c = triangle.c;
        this.na = triangle.na;
        this.nb = triangle.nb;
        this.nc = triangle.nc;
        this.texCoordA = triangle.texCoordA;
        this.texCoordB = triangle.texCoordB;
        this.texCoordC = triangle.texCoordC;
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The r cannot be null!");
        }

        // M x = V

        final Vector3 vec = new Vector3(a.x - r.o.x, a.y - r.o.y, a.z - r.o.z);

        final Mat3x3 m = new Mat3x3(
                a.x - b.x, a.x - c.x, r.d.x,
                a.y - b.y, a.y - c.y, r.d.y,
                a.z - b.z, a.z - c.z, r.d.z
        );

        final double detA = m.determinant;

        final double detA1 = m.col1(vec).determinant;
        final double beta = detA1 / detA;

        if (beta >= 0 && beta <= 1) {

            final double detA2 = m.col2(vec).determinant;
            final double gamma = detA2 / detA;

            if (gamma > 0 && beta + gamma <= 1) {
                final double detA3 = m.col3(vec).determinant;
                final double t = detA3 / detA;
                if (t > 0) {
                    Normal3 n = na.mul(1 - beta - gamma).add(nb.mul(beta)).add(nc.mul(gamma));

                    final Point3 p = r.at(t);

                    final double u = texCoordA.u*(1 - beta - gamma) +  texCoordB.u * beta + texCoordC.u * gamma;
                    final double v = texCoordA.v*(1 - beta - gamma) +  texCoordB.v * beta + texCoordC.v * gamma;
                    Color normalC = material.bumpMap.getColor(u,v);
                    Vector3 nc = new Vector3(normalC.r * 2 - 1, normalC.g * 2 - 1, normalC.b * 2 - 1).normalized();
                    Normal3 n1 = new Vector3( n.x+nc.x*material.bumpScale, n.y+nc.y*material.bumpScale, n.z).normalized().asNormal();
                    if (flipNormal) n1 = n1.mul(-1);
                    return new Hit(t, n1, r, this, new TexCoord2(u,v));
                }
            }

        }

        return null;
    }

    @Override
    public Triangle deepCopy() {
        return new Triangle(this);
    }

    @Override
    public Geometry deepCopy(final Material m) {
        return new Triangle(this,m);
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
