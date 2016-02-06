package matVect;


import utils.Ray;

/**
 * This class represents a Transform Object.
 * For transform any geometries (translate, scale and rotate).
 *
 * @author Robert Dziuba on 15/12/15 and Marcus sitting next to him.
 */
public class Transform {

    /**
     * Represents the transformation matrix
     */
    final public Mat4x4 m;
    /**
     * Represents the inverse transformation matrix
     */
    final public Mat4x4 i;

    /**
     * Generate an default Transform object
     */
    public Transform() {
        this.m = new Mat4x4(
                1,0,0,0,
                0,1,0,0,
                0,0,1,0,
                0,0,0,1
        );

        this.i = this.m;
    }

    /**
     * Generate an Transform object with a given transformation matrix and a inverse transformation matrix
     * @param m a transformation matrix. Can't be null.
     * @param i a inverse transformation matrix. Can't be null.
     * @throws IllegalArgumentException if the given argument is null.
     */
    private Transform(final Mat4x4 m, final Mat4x4 i) {
        if (m == null) throw new IllegalArgumentException("The m cannot be null!");
        if (i == null) throw new IllegalArgumentException("The i cannot be null!");
        this.m = m;
        this.i = i;
    }


    /**
     * Translate the object around the x,y and z axes.
     * @param x represents the x translation
     * @param y represents the y translation
     * @param z represents the z translation
     * @return Transform object with the translation
     */
    public Transform translate(final double x, final double y,final double z){
        return new Transform(
                m.mul(new Mat4x4(
                        1,0,0, x,
                        0,1,0, y,
                        0,0,1, z,
                        0,0,0,1
                )),
                new Mat4x4(
                        1,0,0, -x,
                        0,1,0, -y,
                        0,0,1, -z,
                        0,0,0,1
                ).mul(i)
        );
    }

    /**
     * Scale the object in direction of the x,y and z axes.
     * @param x represents the x scaling
     * @param y represents the y scaling
     * @param z represents the z scaling
     * @return Transform object with the scaling
     */
    public Transform scale(final double x, final double y,final double z){
        return new Transform(
                m.mul(new Mat4x4(
                        x, 0,0,0,
                        0, y, 0,0,
                        0,0, z, 0,
                        0,0,0,1
                )),
                new Mat4x4(
                        1/x, 0,0,0,
                        0, 1/y, 0,0,
                        0,0, 1/z, 0,
                        0,0,0,1
                ).mul(i)
        );
    }

    /**
     * Rotates the object in direction of the x axes.
     * @param a represents the x rotation.
     * @return Transform object with the x rotation.
     */
    public Transform rotateX(final double a){
        return new Transform(
                m.mul(new Mat4x4(
                        1,0,0,0,
                        0, Math.cos(a), -Math.sin(a), 0,
                        0, Math.sin(a), Math.cos(a), 0,
                        0, 0, 0, 1
                )),
                new Mat4x4(
                        1,0,0,0,
                        0, Math.cos(a), Math.sin(a), 0,
                        0, -Math.sin(a), Math.cos(a), 0,
                        0, 0, 0, 1
                ).mul(i)
        );
    }

    /**
     * Rotates the object in direction of the y axes.
     * @param a represents the y rotation.
     * @return Transform object with the y rotation.
     */
    public Transform rotateY(final double a){
        return new Transform(
                m.mul(new Mat4x4(
                        Math.cos(a), 0, Math.sin(a), 0,
                        0, 1, 0, 0,
                        -Math.sin(a),0, Math.cos(a), 0,
                        0, 0, 0, 1
                )),
                new Mat4x4(
                        Math.cos(a), 0, -Math.sin(a), 0,
                        0, 1, 0, 0,
                        Math.sin(a), 0, Math.cos(a), 0,
                        0, 0, 0, 1
                ).mul(i)
        );
    }

    /**
     * Rotates the object in direction of the z axes.
     * @param a represents the z rotation.
     * @return Transform object with the z rotation.
     */
    public Transform rotateZ(final double a){
        return new Transform(
                m.mul(new Mat4x4(
                        Math.cos(a), -Math.sin(a), 0, 0,
                        Math.sin(a), Math.cos(a), 0, 0,
                        0,0,1,0,
                        0,0,0,1
                )),
                new Mat4x4(
                        Math.cos(a), Math.sin(a), 0,0,
                        -Math.sin(a), Math.cos(a), 0,0,
                        0,0,1,0,
                        0,0,0,1
                ).mul(i)
        );
    }

    /**
     * Transforms the ray with the inverse transformation matrix
     * @param r the given ray.
     * @return a transformed ray.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Ray mul(final Ray r){
        if (r == null) throw new IllegalArgumentException("The r cannot be null!");
        return new Ray(i.mul(r.o),i.mul(r.d));
    }

    /**
     * Multiplies the normal with the inverse transformation matrix.
     * @param n the given normal.
     * @return the transformed normal.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Normal3 mul(final Normal3 n){
        if (n == null) throw new IllegalArgumentException("The n cannot be null!");
        return i.trans().mul(new Vector3(n.x,n.y,n.z)).normalized().asNormal();
    }

    @Override
    public String toString() {
        return "Transform{" +
                "m=" + m +
                ", i=" + i +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transform transform = (Transform) o;

        if (!m.equals(transform.m)) return false;
        return i.equals(transform.i);

    }

    @Override
    public int hashCode() {
        int result = m.hashCode();
        result = 31 * result + i.hashCode();
        return result;
    }
}
