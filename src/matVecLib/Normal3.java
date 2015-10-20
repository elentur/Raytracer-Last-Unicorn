package matVecLib;

/**
 * Created by roberto on 07/10/15.
 */
public class Normal3 {

    final public double x;

    final public double y;

    final public double z;

    public Normal3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Normal3 mul(double n){
        return new Normal3(
                x * n,
                y * n,
                z * n
        );
    }

    public Normal3 add(Normal3 n){
        if (n == null) throw new IllegalArgumentException("The parameter 'n' must not be null." );
        return new Normal3(
                x + n.x,
                y + n.y,
                z + n.z
        );
    }

    public double dot(Vector3 v){
        if (v == null) throw new IllegalArgumentException("The parameter 'v' must not be null." );
        return x * v.x + y * v.y + z * v.z;
    }

    @Override
    public String toString() {
        return "Normal3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
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
