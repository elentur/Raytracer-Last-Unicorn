package matVect;

import java.io.Serializable;

/**
 * This Object represents a 3 dimensional point and offers relevant operations for a point.
 * Created by Marcus Baetz on 07.10.2015.
 */
public class Point3 implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * This Component represent the x-Coordinate of the Point3 as Double.
     */
    public final double x;
    /**
     * This Component represent the y-Coordinate of the Point3 as Double.
     */
    public final double y;
    /**
     * This Component represent the z-Coordinate of the Point3 as Double.
     */
    public final double z;

    /**
     * Generates a point with 3 coordinates;
     *
     * @param x This Component represent the x-Coordinate of the Point3 as Double.
     * @param y This Component represent the y-Coordinate of the Point3 as Double.
     * @param z This Component represent the z-Coordinate of the Point3 as Double.
     */
    public Point3(final double x, final double y, final double z) {
        this.x = x + 0.0;
        this.y = y + 0.0;
        this.z = z + 0.0;
    }

    /**
     * Subtract a Point3 from this Point3 and returns the Vector3.
     *
     * @param p The Point3 you want to subtract from this Point3.
     * @return The new Vector3 of this Subtraction.
     */
    public Vector3 sub(final Point3 p) {
        if (p == null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.x - p.x;
        final double y = this.y - p.y;
        final double z = this.z - p.z;

        return new Vector3(x, y, z);
    }

    /**
     * Subtract a Vector3 from this Point3 and returns the new Point3.
     *
     * @param v The Vector3 you want to subtract from this Point3.
     * @return The new Point3 of this Subtraction.
     */
    public Point3 sub(final Vector3 v) {
        if (v == null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.x - v.x;
        final double y = this.y - v.y;
        final double z = this.z - v.z;

        return new Point3(x, y, z);
    }


    /**
     * Add a Vector to this Point3 and returns the new Point3.
     *
     * @param v The Vector3 you want to add to this Point3.
     * @return The new Point3 of this addition.
     */
    public Point3 add(final Vector3 v) {
        if (v == null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.x + v.x;
        final double y = this.y + v.y;
        final double z = this.z + v.z;

        return new Point3(x, y, z);

    }


    @Override
    public String toString() {
        return "P(" + x + "/ " + y + "/ " + z + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3 point3 = (Point3) o;

        if (Double.compare(point3.x, x) != 0) return false;
        if (Double.compare(point3.y, y) != 0) return false;
        return Double.compare(point3.z, z) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
