package geometries;

import matVect.Point3;
import matVect.Transform;
import material.Material;
import utils.Hit;
import utils.Ray;

import java.util.Arrays;

/**
 * This class represents a AxisAlignedBox Object.
 *
 * @author Robert Dziuba on 03.11.15.
 */

public class AxisAlignedBox extends Geometry {
    /**
     * The left bottom far corner of the Axis Aligned Box.
     */
    private final Point3 lbf;
    /**
     * The right top near corner of the Axis Aligned Box.
     */
    private final Point3 run;

    /**
     * The 6 sites of the Box.
     */
    private final Node[] faces = new Node[6];


    /**
     * Instantiates a new Axis Aligned Box Object.
     *
     * @param material of the Axis Aligned Box. Can't be null
     * @param receiveShadows  boolean if Geometry receives Shadows
     * @param castShadows boolean if Geometry cast shadows
     * @param visibility boolean if Geometry is visible
     * @param flipNormal boolean if Geometry need to flip Normals direction
     * @throws IllegalArgumentException if one of the given arguments are null
     */
    public AxisAlignedBox(final Material material, final boolean receiveShadows, final boolean castShadows
            , final boolean visibility, final boolean flipNormal) {
        super(material, receiveShadows, castShadows, visibility, flipNormal);
        this.lbf = new Point3(-0.5, -0.5, -0.5);
        this.run = new Point3(0.5, 0.5, 0.5);

        Plane p = new Plane(material, receiveShadows, castShadows, visibility, flipNormal);

        faces[0] = new Node(new Transform().translate(0, run.y, 0), p, receiveShadows, castShadows, visibility, flipNormal); // up site
        faces[1] = new Node(new Transform().translate(0, lbf.y, 0).rotateX(Math.PI), p, receiveShadows, castShadows, visibility, flipNormal); // down site

        faces[2] = new Node(new Transform().translate(0, 0, run.z).rotateX(Math.PI / 2), p, receiveShadows, castShadows, visibility, flipNormal); // front site
        faces[3] = new Node(new Transform().translate(0, 0, lbf.z).rotateX(-Math.PI / 2).rotateY(Math.PI), p, receiveShadows, castShadows, visibility, flipNormal); // back site

        faces[4] = new Node(new Transform().translate(lbf.x, 0, 0).rotateY(-Math.PI / 2).rotateX(Math.PI / 2), p, receiveShadows, castShadows, visibility, flipNormal); // left site
        faces[5] = new Node(new Transform().translate(run.x, 0, 0).rotateY(Math.PI / 2).rotateX(Math.PI / 2), p, receiveShadows, castShadows, visibility, flipNormal); // right site
    }


    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The ray cannot be null!");
        }

        final Hit[] hits = new Hit[]{
                faces[0].hit(r),
                faces[1].hit(r),
                faces[2].hit(r),
                faces[3].hit(r),
                faces[4].hit(r),
                faces[5].hit(r)
        };

        double t = Double.MAX_VALUE;
        Hit hit = null;

        for (Hit h : hits) {
            if (h != null) {
                final Point3 p = r.at(h.t);
                if (comp(p) && h.t < t && t > 0 && h.t > 0.00001) {
                    t = h.t;
                    hit = h;
                }

            }
        }

        return hit;
    }


    private boolean comp(final Point3 p) {

        return (lbf.x <= p.x + 0.00001 && p.x <= run.x + 0.00001) &&
                (lbf.y <= p.y + 0.00001 && p.y <= run.y + 0.00001) &&
                (lbf.z <= p.z + 0.00001 && p.z <= run.z + 0.00001);

    }

    @Override
    public String toString() {
        return "AxisAlignedBox{" +
                "lbf=" + lbf +
                ", run=" + run +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AxisAlignedBox)) return false;

        AxisAlignedBox that = (AxisAlignedBox) o;

        if (lbf != null ? !lbf.equals(that.lbf) : that.lbf != null) return false;
        if (run != null ? !run.equals(that.run) : that.run != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(faces, that.faces);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lbf != null ? lbf.hashCode() : 0);
        result = 31 * result + (run != null ? run.hashCode() : 0);
        return result;
    }


}
