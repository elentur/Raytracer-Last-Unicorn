package geometries;

import matVect.Point3;
import material.Material;
import utils.Hit;
import utils.Ray;

/**
 * This class represents a AxisAlignedBox Object.
 *
 * @author Robert Dziuba on 03.11.15.
 */

public class AxisAlignedBox extends Geometry {
    /**
     * The left bottom far corner of the Axis Aligned Box.
     */
    public final Point3 lbf;
    /**
     * The right top near corner of the Axis Aligned Box.
     */
    public final Point3 run;

    /**
     * The 6 sites of the Box.
     */
    private final Node[] faces = new Node[6];


    /**
     * Instantiates a new Axis Aligned Box Object.
     *
     * @param material of the Axis Aligned Box. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public AxisAlignedBox(final Material material, final boolean reciveShadows, final boolean castShadows
            ,final boolean visibility,final boolean flipNormal) {
        super(material,reciveShadows, castShadows,visibility,flipNormal);
        this.name = "Axis Aligned Box";
        this.lbf = new Point3(-0.5, -0.5, -0.5);
        this.run = new Point3(0.5, 0.5, 0.5);

        Plane p = new Plane(material,reciveShadows,castShadows,visibility,flipNormal);

        faces[0] = new Node(new Point3(0,run.y,0),new Point3(1,1,1),new Point3(0,0,0),p,reciveShadows,castShadows,visibility,flipNormal); // up site
        faces[1] = new Node(new Point3(0,lbf.y,0),new Point3(1,1,1),new Point3(Math.PI,0,0),p,reciveShadows,castShadows,visibility,flipNormal); // down site

        faces[2] = new Node(new Point3(0,0,run.z),new Point3(1,1,1),new Point3(Math.PI/2,0,0),p,reciveShadows,castShadows,visibility,flipNormal); // front site
        faces[3] = new Node(new Point3(0,0,lbf.z),new Point3(1,1,1),new Point3(-Math.PI/2,Math.PI,0),p,reciveShadows,castShadows,visibility,flipNormal); // back site

        faces[4] = new Node(new Point3(lbf.x,0,0),new Point3(1,1,1),new Point3(Math.PI/2,-Math.PI/2,0),p,reciveShadows,castShadows,visibility,flipNormal); // left site
        faces[5] = new Node(new Point3(run.x,0,0),new Point3(1,1,1),new Point3(Math.PI/2,Math.PI/2,0),p,reciveShadows,castShadows,visibility,flipNormal); // right site

    }

    /**
     * Copy Constructor
     *
     * @param box
     */
    public AxisAlignedBox(AxisAlignedBox box) {
        this(box,box.material);
    }

    public AxisAlignedBox(final AxisAlignedBox box, final Material m) {
        super(m, box.reciveShadows, box.castShadows, box.visibility, box.flipNormal);
        this.lbf = box.lbf;
        this.run = box.run;
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

        for(Hit h : hits){
            if(h!= null){
                final Point3 p = r.at(h.t);
                if(comp(p,0.00001) && h.t < t && t > 0 && h.t > 0.00001) {
                    t = h.t;
                    hit = h;
                }

            }
        }

        return hit;
    }

    @Override
    public AxisAlignedBox deepCopy() {
        return new AxisAlignedBox(this);
    }

    @Override
    public Geometry deepCopy(final Material m) {
        return  new AxisAlignedBox(this,m);
    }

    private boolean comp(final Point3 p, final double e) {

        return (lbf.x <= p.x + e && p.x <= run.x + e) &&
                (lbf.y <= p.y + e && p.y <= run.y + e) &&
                (lbf.z <= p.z + e && p.z <= run.z + e);

    }

    @Override
    public String toString() {
        return "AxisAlignedBox{" +
                "lbf=" + lbf +
                ", run=" + run +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AxisAlignedBox that = (AxisAlignedBox) o;

        if (lbf != null ? !lbf.equals(that.lbf) : that.lbf != null) return false;
        if (run != null ? !run.equals(that.run) : that.run != null) return false;
        return material.equals(that.material) && name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lbf != null ? lbf.hashCode() : 0);
        result = 31 * result + (run != null ? run.hashCode() : 0);
        return result;
    }


}
