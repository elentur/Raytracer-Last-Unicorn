package matVect;

/**
 * s class represents a Point2 Object.
 *
 * @author  Robert Dziuba on 03/12/15.
 */
public class Point2 {

    /**
     * the x coordinate of the point.
     */
    public final double x;

    /**
     * the y coordinate of the point.
     */
    public final double y;

    /**
     * Point2 constructor which generate a point object.
     *
     * @param x coordinate of the point.
     * @param y coordinate of the point.
     */
    public Point2(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2 point2 = (Point2) o;

        return Double.compare(point2.x, x) == 0 && Double.compare(point2.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
