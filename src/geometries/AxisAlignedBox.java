package geometries;

import matVect.Normal3;
import matVect.Point3;
import material.Material;
import texture.TexCoord2;
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
     * Instantiates a new Axis Aligned Box Object.
     *
     * @param material of the Axis Aligned Box. Can't be null.
     * @param lbf      of the Axis Aligned Box. Can't be null.
     * @param run      of the Axis Aligned Box. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public AxisAlignedBox(final Point3 run, final Point3 lbf, final Material material) {
        super(material);
        if (lbf == null) {
            throw new IllegalArgumentException("The lbf cannot be null!");
        }
        if (run == null) {
            throw new IllegalArgumentException("The run cannot be null!");
        }

        this.lbf = lbf;
        this.run = run;
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("The r cannot be null!");
        }

        final Plane[] planes = new Plane[6];

        planes[0] = new Plane( // front layer
                run,
                new Normal3(0, 0, 1),
                material
        );
        planes[1] = new Plane( // back layer
                lbf,
                new Normal3(0, 0, -1),
                material
        );
        planes[2] = new Plane( // left layer
                lbf,
                new Normal3(-1, 0, 0),
                material
        );
        planes[3] = new Plane( // right layer
                run,
                new Normal3(1, 0, 0),
                material
        );
        planes[4] = new Plane( // up layer
                run,
                new Normal3(0, 1, 0),
                material

        );
        planes[5] = new Plane( // down layer
                lbf,
                new Normal3(0, -1, 0),
                material
        );

        Hit max = null;
/*
falls in würfel
 */
      if(comp(r.o,0.000001)){
            for (final Plane plane : planes) {
                final Hit h = plane.hit(r);
                if ( (h != null  && h.t >0.000001) &&(max == null || ( h.t < max.t))) max = h;
            }
           // if (max!= null) max = new Hit(max.t,max.n.mul(-1),max.ray,max.geo);
        }else {
            for (final Plane plane : planes) {
                // Finds all layers whose normals shows to the viewer.
                final double condition = r.o.sub(plane.a).normalized().dot(plane.n);

                if (condition > 0) {
                    // calculates the ray that intersects the selected layers
                    final double t = plane.a.sub(r.o).dot(plane.n) / r.d.dot(plane.n);
                    if (max == null || t > max.t) {
                        final Point3 p = r.at(t);
                        final double v = p.x;
                        final double u = p.z;

                        max = new Hit(t, plane.n, r, this, new TexCoord2(u,v));

                    }
                }
            }
        }

        return comparison(max);
    }

    /**
     * The method checks if the results lies within the Axis Aligned Box coordinates.
     *
     * @param hit a hit of the ray or null
     * @return Hit if the coordinates are within the Axis Aligned Box coordinates or null.
     */
    private Hit comparison(final Hit hit) {
        if (hit != null) {
            final Point3 p = hit.ray.at(hit.t);

            final double e = 0.00000000001;
            if (comp(p,e))
                return hit;
        }

        return null;
    }
private boolean comp(final Point3 p, final double e){

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
