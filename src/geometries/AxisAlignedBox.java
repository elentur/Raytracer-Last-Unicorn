package geometries;

<<<<<<< HEAD
import matVect.Vector3;
import utils.Color;
=======
import matVect.Normal3;
import matVect.Point3;
import utils.Color;
import utils.Hit;
import utils.Ray;
>>>>>>> origin/robert

/**
 *  This class represents a AxisAlignedBox Object.
 *  @author Robert Dziuba on 03.11.15.
 */
<<<<<<< HEAD
public class AxisAlignedBox extends Geometry {
    public AxisAlignedBox(Vector3 run, Vector3 lbf, Color color) {
        super(color);
=======

public class AxisAlignedBox extends Geometry{
    /**
     * The left bottom far corner of the Axis Aligned Box.
     */
    private final Point3 lbf;
    /**
     * The right top near corner of the Axis Aligned Box.
     */
    private final Point3 run;

    /**
     * Instantiates a new Axis Aligned Box Object.
     * @param color of the Axis Aligned Box. Can't be null.
     * @param lbf of the Axis Aligned Box. Can't be null.
     * @param run of the Axis Aligned Box. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public AxisAlignedBox(final Color color, final Point3 lbf, final Point3 run) {
        super(color);
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
                color,
                run,
                new Normal3(0,0,1)
        );
        planes[1] = new Plane( // back layer
                color,
                lbf,
                new Normal3(0,0,-1)
        );
        planes[2] =  new Plane( // left layer
                color,
                lbf,
                new Normal3(-1,0,0)
        );
        planes[3] = new Plane( // right layer
                color,
                run,
                new Normal3(1,0,0)
        );
        planes[4] = new Plane( // up layer
                color,
                run,
                new Normal3(0,1,0)

        );
        planes[5] = new Plane( // down layer
                color,
                lbf,
                new Normal3(0,-1,0)
        );

        Hit max = null;

        for(final Plane plane : planes){
            // Finds all layers whose normal shows to the viewers.
            final double condition = r.o.sub(plane.a).dot(plane.n);

            if(condition > 0) {
                // calculates the ray that intersects the selected layers
                final double t = plane.a.sub(r.o).dot(plane.n) / r.d.dot(plane.n);
                if (max == null || t > max.t) {
                    max = new Hit(t, r, this);
                }
            }
        }

        return comparison(max);
    }

    /**
     * The method checks if the results lies within the Axis Aligned Box coordinates.
     * @param hit a hit of the ray or null
     * @return Hit if the coordinates are within the Axis Aligned Box coordinates or null.
     */
    private Hit comparison(final Hit hit) {
        if(hit != null) {
            final Point3 p = hit.ray.at(hit.t);
            final double e = 0.00000000001;

            if (    (lbf.x <= p.x+e && p.x <= run.x+e) &&
                    (lbf.y <= p.y+e && p.y <= run.y+e) &&
                    (lbf.z <= p.z+e && p.z <= run.z+e)
                    )
                return hit;
        }

        return null;
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
        return !(run != null ? !run.equals(that.run) : that.run != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lbf != null ? lbf.hashCode() : 0);
        result = 31 * result + (run != null ? run.hashCode() : 0);
        return result;
>>>>>>> origin/robert
    }
}
