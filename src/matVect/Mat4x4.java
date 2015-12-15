package matVect;

import java.io.Serializable;

/**
 * This class represents a Mat4x4 Object.
 *
 * @author Robert Dziuba on 15/12/15.
 */
public class Mat4x4 implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * This Component represent the m11-Value of the matVect.Mat4x4 as Double.
     */
    public final double m11;
    /**
     * This Component represent the m12-Value of the matVect.Mat4x4 as Double.
     */
    public final double m12;
    /**
     * This Component represent the m13-Value of the matVect.Mat4x4 as Double.
     */
    public final double m13;
    /**
     * This Component represent the m14-Value of the matVect.Mat4x4 as Double.
     */
    public final double m14;
    /**
     * This Component represent the m21-Value of the matVect.Mat4x4 as Double.
     */
    public final double m21;
    /**
     * This Component represent the m22-Value of the matVect.Mat4x4 as Double.
     */
    public final double m22;
    /**
     * This Component represent the m23-Value of the matVect.Mat4x4 as Double.
     */
    public final double m23;
    /**
     * This Component represent the m24-Value of the matVect.Mat4x4 as Double.
     */
    public final double m24;
    /**
     * This Component represent the m31-Value of the matVect.Mat4x4 as Double.
     */
    public final double m31;
    /**
     * This Component represent the m32-Value of the matVect.Mat4x4 as Double.
     */
    public final double m32;
    /**
     * This Component represent the m33-Value of the matVect.Mat4x4 as Double.
     */
    public final double m33;
    /**
     * This Component represent the m34-Value of the matVect.Mat4x4 as Double.
     */
    public final double m34;
    /**
     * This Component represent the m41-Value of the matVect.Mat4x4 as Double.
     */
    public final double m41;
    /**
     * This Component represent the m42-Value of the matVect.Mat4x4 as Double.
     */
    public final double m42;
    /**
     * This Component represent the m43-Value of the matVect.Mat4x4 as Double.
     */
    public final double m43;
    /**
     * This Component represent the m44-Value of the matVect.Mat4x4 as Double.
     */
    public final double m44;


    /**
     * Generate a matrix with 4 by 4 dimensions.
     *
     * @param m11 This Component represent the m11-Value of the matVect.Mat4x4 as Double.
     * @param m12 This Component represent the m12-Value of the matVect.Mat4x4 as Double.
     * @param m13 This Component represent the m13-Value of the matVect.Mat4x4 as Double.
     * @param m14 This Component represent the m14-Value of the matVect.Mat4x4 as Double.
     * @param m21 This Component represent the m21-Value of the matVect.Mat4x4 as Double.
     * @param m22 This Component represent the m22-Value of the matVect.Mat4x4 as Double.
     * @param m23 This Component represent the m23-Value of the matVect.Mat4x4 as Double.
     * @param m24 This Component represent the m24-Value of the matVect.Mat4x4 as Double.
     * @param m31 This Component represent the m31-Value of the matVect.Mat4x4 as Double.
     * @param m32 This Component represent the m32-Value of the matVect.Mat4x4 as Double.
     * @param m33 This Component represent the m33-Value of the matVect.Mat4x4 as Double.
     * @param m34 This Component represent the m34-Value of the matVect.Mat4x4 as Double.
     * @param m41 This Component represent the m41-Value of the matVect.Mat4x4 as Double.
     * @param m42 This Component represent the m42-Value of the matVect.Mat4x4 as Double.
     * @param m43 This Component represent the m43-Value of the matVect.Mat4x4 as Double.
     * @param m44 This Component represent the m44-Value of the matVect.Mat4x4 as Double.
     */
    public Mat4x4(final double m11, final double m21, final double m31, final double m41,
                  final double m12, final double m22, final double m32, final double m42,
                  final double m13, final double m23, final double m33, final double m43,
                  final double m14, final double m24, final double m34, final double m44) {
        this.m11 = m11 + 0.0;
        this.m12 = m12 + 0.0;
        this.m13 = m13 + 0.0;
        this.m14 = m14 + 0.0;
        this.m21 = m21 + 0.0;
        this.m22 = m22 + 0.0;
        this.m23 = m23 + 0.0;
        this.m24 = m24 + 0.0;
        this.m31 = m31 + 0.0;
        this.m32 = m32 + 0.0;
        this.m33 = m33 + 0.0;
        this.m34 = m34 + 0.0;
        this.m41 = m41 + 0.0;
        this.m42 = m42 + 0.0;
        this.m43 = m43 + 0.0;
        this.m44 = m44 + 0.0;
    }

    /**
     * Multiply a matVect.Mat4x4 with a second matVect.Mat4x4 and returns the result as a new matVect.Mat4x4.
     *
     * @param m The matVect.Mat4x4 that have to be multiplied with this matVect.Mat4x4.
     * @return The matVect.Mat4x4 as result of the multiplication.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Mat4x4 mul(final Mat4x4 m) {
        if (m == null) throw new IllegalArgumentException("Null as parameter");

        return new Mat4x4(
                (m11 * m.m11 + m21 * m.m12 + m31 * m.m13 + m41 * m.m14),
                (m11 * m.m21 + m21 * m.m22 + m31 * m.m23 + m41 * m.m24),
                (m11 * m.m31 + m21 * m.m32 + m31 * m.m33 + m41 * m.m34),
                (m11 * m.m41 + m21 * m.m42 + m31 * m.m43 + m41 * m.m44),

                (m12 * m.m11 + m22 * m.m12 + m32 * m.m13 + m42 * m.m14),
                (m12 * m.m21 + m22 * m.m22 + m32 * m.m23 + m42 * m.m24),
                (m12 * m.m31 + m22 * m.m32 + m32 * m.m33 + m42 * m.m34),
                (m12 * m.m41 + m22 * m.m42 + m32 * m.m43 + m42 * m.m44),

                (m13 * m.m11 + m23 * m.m12 + m33 * m.m13 + m43 * m.m14),
                (m13 * m.m21 + m23 * m.m22 + m33 * m.m23 + m43 * m.m24),
                (m13 * m.m31 + m23 * m.m32 + m33 * m.m33 + m43 * m.m34),
                (m13 * m.m41 + m23 * m.m42 + m33 * m.m43 + m43 * m.m44),

                (m14 * m.m11 + m24 * m.m12 + m34 * m.m13 + m44 * m.m14),
                (m14 * m.m21 + m24 * m.m22 + m34 * m.m23 + m44 * m.m24),
                (m14 * m.m31 + m24 * m.m32 + m34 * m.m33 + m44 * m.m34),
                (m14 * m.m41 + m24 * m.m42 + m34 * m.m43 + m44 * m.m44)
                );
    }

    /**
     * Multiply a matVect.Mat4x4 with a matVect.Vector3 and returns the result as a new matVect.Vector3.
     *
     * @param v The matVect.Vector3 that have to be multiplied with this matVect.Mat4x4.
     * @return The matVect.Vector3 as result of the multiplication.
     */
    public Vector3 mul(final Vector3 v) {
        if (v == null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.m11 * v.x + this.m21 * v.y + this.m31 * v.z;
        final double y = this.m12 * v.x + this.m22 * v.y + this.m32 * v.z;
        final double z = this.m13 * v.x + this.m23 * v.y + this.m33 * v.z;

        return new Vector3(x, y, z);
    }

    /**
     * Multiply a matVect.Mat4x4 with a matVect.Point3 and returns the result as a new matVect.Point3.
     *
     * @param p The matVect.Point3 that have to be multiplied with this matVect.Mat4x4.
     * @return The matVect.Point3 as result of the multiplication.
     */
    public Point3 mul(final Point3 p) {
        if (p == null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.m11 * p.x + this.m21 * p.y + this.m31 * p.z + this.m41;
        final double y = this.m12 * p.x + this.m22 * p.y + this.m32 * p.z + this.m42;
        final double z = this.m13 * p.x + this.m23 * p.y + this.m33 * p.z + this.m43;

        return new Point3(x, y, z);
    }

    /**
     * Returns a transposed Mat4x4 object.
     * @return a transposed Mat4x4 object.
     */
    public Mat4x4 trans(){
        return new Mat4x4(
                m11,m12,m13,m14,
                m21,m22,m23,m24,
                m31,m32,m33,m34,
                m41,m42,m43,m44
        );
    }

    @Override
    public String toString() {
        return  m11 + ", " + m21 + ", " + m31 + ", " + m41 + "\n" +
                m12 + ", " + m22 + ", " + m32 + ", " + m42 + "\n" +
                m13 + ", " + m23 + ", " + m33 + ", " + m43 + "\n" +
                m14 + ", " + m24 + ", " + m34 + ", " + m44 + "\n"
                ;
    }
}
