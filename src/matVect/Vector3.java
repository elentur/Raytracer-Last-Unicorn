package matVect;

/**
 * This Object represents a 3 dimensional vector and offers relevant operations for a vector.
 * Created by Marcus Baetz on 07.10.2015.
 */
public class Vector3 {
    /**
     * This Component represent the x-Value of the Vector3 as Double.
     */
    public final double x;
    /**
     * This Component represent the y-Value of the Vector3 as Double.
     */
    public final double y;
    /**
     * This Component represent the z-Value of the Vector3 as Double.
     */
    public final double z;
    /**
     * This Component represent the Magnitude of the Vector3 as Double.
     */
    public final double magnitude;

    /**
     * Generates a vector with 3 dimensions;
     * @param x This Component represent the x-Value of the Vector3 as Double.
     * @param y This Component represent the y-Value of the Vector3 as Double.
     * @param z This Component represent the z-Value of the Vector3 as Double.
     */
    public Vector3(final double x, final double y, final double z ){
        this.x = x+0.0;
        this.y = y+0.0;
        this.z = z+0.0;
        this.magnitude = length(x,y,z);
    }

    /**
     * Static Method to calculate the length of a Vector.
     * @param x This Component represent the x-Value of the Vector3 as Double.
     * @param y This Component represent the y-Value of the Vector3 as Double.
     * @param z This Component represent the z-Value of the Vector3 as Double.
     * @return The Length of a Vector3 as Double.
     */
    private double length(final double x, final double y, final double z){
        return Math.sqrt(x*x+y*y+z*z);
    }

    /**
     * Add a Vector3 to this Vector3.
     * @param v The Vector3 you want to add to this Vector3.
     * @return The new Vector3 of this addition.
     */
    public Vector3 add(final Vector3 v){
        if(v==null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.x + v.x;
        final double y = this.y + v.y;
        final double z = this.z + v.z;

        return new Vector3(x,y,z);
    }

    /**
     * Add a Normal3 to this Vector3.
     * @param n The Normal3 you want to add to this Vector3.
     * @return The new Vector3 of this addition.
     */
    public Vector3 add(final Normal3 n){
        if(n==null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.x + n.x;
        final double y = this.y + n.y;
        final double z = this.z + n.z;

        return new Vector3(x,y,z);
    }
    /**
     * Subtract a Normal3 from this Vector3.
     * @param n The Normal3 you want to subtract from this Vector3.
     * @return The new Vector3 of this subtraction.
     */
    public Vector3 sub(final Normal3 n){
        if(n==null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.x - n.x;
        final double y = this.y - n.y;
        final double z = this.z - n.z;

        return new Vector3(x,y,z);
    }

    /**
     * Multiply a Skalar with this Vector3.
     * @param c The Skalar you want to multiply with this Vector3.
     * @return The new Vector3 of this multiplication.
     */
    public Vector3 mul(final double c){
        final double x = this.x *c;
        final double y = this.y *c;
        final double z = this.z *c;

        return new Vector3(x,y,z);
    }

    /**
     * Multiply a Vector3 to this Vector3.
     * @param v The Vector3 you want to multiply with this Vector3.
     * @return The new Skalar of this multiplication.
     */
    public double dot(final Vector3 v){
        if(v==null) throw new IllegalArgumentException("Null as parameter");
        return ((x*v.x) + (y*v.y) + (z*v.z));
    }
    /**
     * Multiply a Normal3 to this Vector3.
     * @param n The Normal3 you want to multiply with this Vector3.
     * @return The new Skalar of this multiplication.
     */
    public double dot(final Normal3 n) {
        if(n==null) throw new IllegalArgumentException("Null as parameter");
        return ((x*n.x) + (y*n.y) + (z*n.z));
    }

    /**
     * Return a normalized Vector3 with the same direction like this Vector3 but a length of 1.0.
     * @return A normalized Vector3.
     */
    public Vector3 normalized(){
        return mul(1/magnitude);
    }

    /**
     * Returns a Normal3 with the same Values of this Vector3.
     * @return A Normal3 from this Vector3.
     */
    public Normal3 asNormal(){
        return new Normal3(x,y,z);
    }

    /**
     * Returns a Vector3 that is a reflected Vector3 of this Vector3 on a Normal3.
     * @param n The Normal3 on that the this Vector has to reflect.
     * @return A reflected Vector3.
     */
    public Vector3 reflectedOn(final Normal3 n){
      /*  if(n==null) throw new IllegalArgumentException("Null as parameter");
        final Normal3 n1 = n.mul(2);
        final double s = n.dot(this);
        final Normal3 n2 = n1.mul(s);
        final Vector3 v1 = this.mul(-1);
        return v1.add(n2);*/
        return (this.mul(-1)).add(n.mul(2).mul(n.dot(this)));
    }

    /**
     * The cross product of this Vector3 and a second Vector3.
     * @param v The Vector3 that have to be crossed with this Vector3.
     * @return The Vector3 as Result of the Cross product.
     */
    public Vector3 x(final Vector3 v){
        if(v==null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.y * v.z - this.z * v.y;
        final double y = this.z * v.x - this.x * v.z;
        final double z = this.x * v.y - this.y * v.x;
        return new Vector3(x,y,z);
    }


    @Override
    public String toString() {
        return  "V(" + x + " " +
                y + " " +
                z + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;


        //if (Double.compare(vector3.x,x) != 0) return false;
       // if (Double.compare(vector3.y,y) != 0) return false;
        //if (Double.compare(vector3.z,z) != 0) return false;
        //return Double.compare(vector3.magnitude, magnitude) == 0;
        final double difX= vector3.x-x;
        final double difY = vector3.y-y;
        final double difZ= vector3.z-z;
        return Math.abs(difX+difY+difZ)<0.00000000000001;

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
}
