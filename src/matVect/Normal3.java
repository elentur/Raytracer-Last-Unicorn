package matVect;

import java.io.Serializable;

/**
 * This Object represents a 3 dimensional normal and offers relevant operations for a normal.
 * Created by Marcus Baetz on 07.10.2015.
 */
public class Normal3 implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * This Component represent the x-Value of the Normal3 as Double.
     */
    public final double x;
    /**
     * This Component represent the y-Value of the Normal3 as Double.
     */
    public final double y;
    /**
     * This Component represent the z-Value of the Normal3 as Double.
     */
    public final double z;

    /**
     * Generates a normal with 3 dimensions;
     *
     * @param x This Component represent the x-Value of the Normal3 as Double.
     * @param y This Component represent the y-Value of the Normal3 as Double.
     * @param z This Component represent the z-Value of the Normal3 as Double.
     */
    public Normal3(final double x, final double y, final double z) {
        this.x = x + 0.0;
        this.y = y + 0.0;
        this.z = z + 0.0;
    }


    /**
     * Multiply a Skalar with this Normal and returns the new Normal3.
     *
     * @param s The Skalar you want to multiply with this Normal3 as Double.
     * @return The new Normal3 of this multiplication.
     */
    public Normal3 mul(final double s) {
        final double x = this.x * s;
        final double y = this.y * s;
        final double z = this.z * s;

        return new Normal3(x, y, z);

    }

    /**
     * Add a Normal3 with this Normal3 and returns the new Normal3.
     *
     * @param n The Normal3 you want to add to this Normal3.
     * @return The new Normal3 of this addition.
     */
    public Normal3 add(final Normal3 n) {
        if (n == null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.x + n.x;
        final double y = this.y + n.y;
        final double z = this.z + n.z;

        return new Normal3(x, y, z);
    }

    /**
     * Multiply a Vector3 with the Normal3 and returns the Skalar.
     *
     * @param v The Vector3 you want to multiply with this Normal3.
     * @return The new Skalar of this Skalar product as Double.
     */
    public double dot(final Vector3 v) {
        if (v == null) throw new IllegalArgumentException("Null as parameter");
        return ((x * v.x) + (y * v.y) + (z * v.z));

    }

    @Override
    public String toString() {
        return "N(" + x + " " +
                y + " " +
                z + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Normal3 normal3 = (Normal3) o;

        if (Double.compare(normal3.x, x) != 0) return false;
        if (Double.compare(normal3.y, y) != 0) return false;
        return Double.compare(normal3.z, z) == 0;

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
