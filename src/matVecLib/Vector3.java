package matVecLib;

import java.util.Vector;

/**
 * Created by roberto on 07/10/15.
 */
public class Vector3 {

    final public double x;

    final public double y;

    final public double z;

    final public double magnitude;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.magnitude = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
    }

    public Vector3 add(Vector3 v){
        if (v == null) throw new IllegalArgumentException("The parameter 'v' must not be null." );
        return new Vector3(
                x + v.x,
                y + v.y,
                z + v.z
        );
    }

    public Vector3 add(Normal3 n){
        if (n == null) throw new IllegalArgumentException("The parameter 'n' must not be null." );
        return new Vector3(
                x + n.x,
                y + n.y,
                z + n.z
        );
    }

    public Vector3 sub(Normal3 n){
        if (n == null) throw new IllegalArgumentException("The parameter 'n' must not be null." );
        return new Vector3(
                 x - n.x,
                 y - n.y,
                 z - n.z
        );
    }

    public Vector3 mul(double c){
        return new Vector3(
                x * c,
                y * c,
                z * c
        );
    }

    public double dot(Vector3 v){
        if (v == null) throw new IllegalArgumentException("The parameter 'v' must not be null." );
        return x * v.x + y * v.y + z * v.z;
    }

    public double dot(Normal3 n){
        if (n == null) throw new IllegalArgumentException("The parameter 'n' must not be null." );
        return x * n.x + y * n.y + z * n.z;
    }

    public Vector3 normalized(){
        return new Vector3(
                x/magnitude,
                y/magnitude,
                z/magnitude
        );
    }

    public Normal3 asNormal(){
        return new Normal3(x,y,z);
    }

    public Vector3 reflectedOn(Normal3 n){
        if (n == null) throw new IllegalArgumentException("The parameter 'n' must not be null." );

        return (this.mul(-1)).add(n.mul(2.0).mul(n.dot(this)));
    }

    public Vector3 x(Vector3 v){
        if (v == null) throw new IllegalArgumentException("The parameter 'v' must not be null." );
        return new Vector3(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", magnitude=" + magnitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        if (Double.compare(vector3.x, x) != 0) return false;
        if (Double.compare(vector3.y, y) != 0) return false;
        if (Double.compare(vector3.z, z) != 0) return false;
        return Double.compare(vector3.magnitude, magnitude) == 0;

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
        temp = Double.doubleToLongBits(magnitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static void main(String[] args) {

        Vector3 v = new Vector3(0.707, 0.707, 0);
        Normal3 n = new Normal3(1,0,0);
        // V - (2.0 * dot(N, V) * N).
        System.out.println((2*v.dot(n)*n.x) - v.x);
        System.out.println((2*v.dot(n)*n.y) - v.y);
        System.out.println((2*v.dot(n)*n.z) - v.z);

        System.out.println(v.reflectedOn(n));
    }
}
