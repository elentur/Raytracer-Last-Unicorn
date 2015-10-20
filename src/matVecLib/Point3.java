package matVecLib;

/**
 * Created by roberto on 07/10/15.
 */
public class Point3 {

    final public double x;

    final public double y;

    final public double z;

    public Point3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3 add(Vector3 n){
        if (n == null) throw new IllegalArgumentException("The parameter 'n' must not be null." );
        return new Point3(
                x + n.x,
                y + n.y,
                z + n.z
        );
    }

    public Vector3 sub(Point3 p){
        if (p == null) throw new IllegalArgumentException("The parameter 'p' must not be null." );
        return new Vector3(
                x - p.x,
                y - p.y,
                z - p.z
        );
    }

    public Point3 sub(Vector3 n){
        if (n == null) throw new IllegalArgumentException("The parameter 'n' must not be null." );
        return new Point3(
                x - n.x,
                y - n.y,
                z - n.z
        );
    }

    @Override
    public String toString() {
        return "Point3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
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
