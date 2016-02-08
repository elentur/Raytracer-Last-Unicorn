package matVect;

/**
 * This Object represents a 3x3 matrix and offers relevant operations for a matrix.
 * Created by Marcus Baetz on 07.10.2015.
 */
public class Mat3x3 {
    /**
     * This Component represent the m11-Value of the matVect.Mat3x3 as Double.
     */
    private final double m11;
    /**
     * This Component represent the m12-Value of the matVect.Mat3x3 as Double.
     */
    private final double m12;
    /**
     * This Component represent the m13-Value of the matVect.Mat3x3 as Double.
     */
    private final double m13;
    /**
     * This Component represent the m21-Value of the matVect.Mat3x3 as Double.
     */
    private final double m21;
    /**
     * This Component represent the m22-Value of the matVect.Mat3x3 as Double.
     */
    private final double m22;
    /**
     * This Component represent the m23-Value of the matVect.Mat3x3 as Double.
     */
    private final double m23;
    /**
     * This Component represent the m31-Value of the matVect.Mat3x3 as Double.
     */
    private final double m31;
    /**
     * This Component represent the m32-Value of the matVect.Mat3x3 as Double.
     */
    private final double m32;
    /**
     * This Component represent the m33-Value of the matVect.Mat3x3 as Double.
     */
    private final double m33;
    /**
     * This Component represent the determinat of the matVect.Mat3x3 as Double.
     */
    public final double determinant;

    /**
     * Generate a matrix with 3 by 3 dimensions.
     *
     * @param m11 This Component represent the m11-Value of the matVect.Mat3x3 as Double.
     * @param m12 This Component represent the m12-Value of the matVect.Mat3x3 as Double.
     * @param m13 This Component represent the m13-Value of the matVect.Mat3x3 as Double.
     * @param m21 This Component represent the m21-Value of the matVect.Mat3x3 as Double.
     * @param m22 This Component represent the m22-Value of the matVect.Mat3x3 as Double.
     * @param m23 This Component represent the m23-Value of the matVect.Mat3x3 as Double.
     * @param m31 This Component represent the m31-Value of the matVect.Mat3x3 as Double.
     * @param m32 This Component represent the m32-Value of the matVect.Mat3x3 as Double.
     * @param m33 This Component represent the m33-Value of the matVect.Mat3x3 as Double.
     */
    public Mat3x3(final double m11, final double m21, final double m31,
                  final double m12, final double m22, final double m32,
                  final double m13, final double m23, final double m33) {
        this.m11 = m11 + 0.0;
        this.m12 = m12 + 0.0;
        this.m13 = m13 + 0.0;
        this.m21 = m21 + 0.0;
        this.m22 = m22 + 0.0;
        this.m23 = m23 + 0.0;
        this.m31 = m31 + 0.0;
        this.m32 = m32 + 0.0;
        this.m33 = m33 + 0.0;

        this.determinant = (m11 * m22 * m33) + (m21 * m32 * m13) + (m31 * m12 * m23) - (m13 * m22 * m31) - (m23 * m32 * m11) - (m33 * m12 * m21);
    }

    /**
     * Multiply a matVect.Mat3x3 with a second matVect.Mat3x3 and returns the result as a new matVect.Mat3x3.
     *
     * @param m The matVect.Mat3x3 that have to be multiplied with this matVect.Mat3x3.
     * @return The matVect.Mat3x3 as result of the multiplication.
     */
    public Mat3x3 mul(final Mat3x3 m) {
        if (m == null) throw new IllegalArgumentException("Null as parameter");
        final double m11 = this.m11 * m.m11 + this.m21 * m.m12 + this.m31 * m.m13;
        final double m21 = this.m12 * m.m11 + this.m22 * m.m12 + this.m32 * m.m13;
        final double m31 = this.m13 * m.m11 + this.m23 * m.m12 + this.m33 * m.m13;
        final double m12 = this.m11 * m.m21 + this.m21 * m.m22 + this.m31 * m.m23;
        final double m22 = this.m12 * m.m21 + this.m22 * m.m22 + this.m32 * m.m23;
        final double m32 = this.m13 * m.m21 + this.m23 * m.m22 + this.m33 * m.m23;
        final double m13 = this.m11 * m.m31 + this.m21 * m.m32 + this.m31 * m.m33;
        final double m23 = this.m12 * m.m31 + this.m22 * m.m32 + this.m32 * m.m33;
        final double m33 = this.m13 * m.m31 + this.m23 * m.m32 + this.m33 * m.m33;

        return new Mat3x3(
                m11, m12, m13,
                m21, m22, m23,
                m31, m32, m33);
    }

    /**
     * Multiply a matVect.Mat3x3 with a matVect.Vector3 and returns the result as a new matVect.Vector3.
     *
     * @param v The matVect.Vector3 that have to be multiplied with this matVect.Mat3x3.
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
     * Multiply a matVect.Mat3x3 with a matVect.Point3 and returns the result as a new matVect.Point3.
     *
     * @param p The matVect.Point3 that have to be multiplied with this matVect.Mat3x3.
     * @return The matVect.Point3 as result of the multiplication.
     */
    public Point3 mul(final Point3 p) {
        if (p == null) throw new IllegalArgumentException("Null as parameter");
        final double x = this.m11 * p.x + this.m21 * p.x + this.m31 * p.x;
        final double y = this.m12 * p.y + this.m22 * p.y + this.m32 * p.y;
        final double z = this.m13 * p.z + this.m23 * p.z + this.m33 * p.z;

        return new Point3(x, y, z);
    }

    /**
     * Changes the first column of a matVect.Mat3x3 to the values of a matVect.Vector3 and return it as a new matVect.Mat3x3.
     *
     * @param v The matVect.Vector3 that have to replace the Column.
     * @return A new matVect.Mat3x3 with a replace Column.
     */
    public Mat3x3 col1(Vector3 v) {
        if (v == null) throw new IllegalArgumentException("Null as parameter");
        return new Mat3x3(
                v.x, m21, m31,
                v.y, m22, m32,
                v.z, m23, m33);
    }

    /**
     * Changes the second column of a matVect.Mat3x3 to the values of a matVect.Vector3 and return it as a new matVect.Mat3x3.
     *
     * @param v The matVect.Vector3 that have to replace the Column.
     * @return A new matVect.Mat3x3 with a replace Column.
     */
    public Mat3x3 col2(Vector3 v) {
        if (v == null) throw new IllegalArgumentException("Null as parameter");
        return new Mat3x3(
                m11, v.x, m31,
                m12, v.y, m32,
                m13, v.z, m33);
    }

    /**
     * Changes the third column of a matVect.Mat3x3 to the values of a matVect.Vector3 and return it as a new matVect.Mat3x3.
     *
     * @param v The matVect.Vector3 that have to replace the Column.
     * @return A new matVect.Mat3x3 with a replace Column.
     */
    public Mat3x3 col3(Vector3 v) {
        if (v == null) throw new IllegalArgumentException("Null as parameter");
        return new Mat3x3(
                m11, m21, v.x,
                m12, m22, v.y,
                m13, m23, v.z);
    }

    @Override
    public String toString() {
        return +m11 + ", " + m21 + ", " + m31 + "\n" +
                +m12 + ", " + m22 + ", " + m32 + "\n" +
                +m13 + ", " + m23 + ", " + m33;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mat3x3 mat3x3 = (Mat3x3) o;

        if (Double.compare(mat3x3.m11, m11) != 0) return false;
        if (Double.compare(mat3x3.m12, m12) != 0) return false;
        if (Double.compare(mat3x3.m13, m13) != 0) return false;
        if (Double.compare(mat3x3.m21, m21) != 0) return false;
        if (Double.compare(mat3x3.m22, m22) != 0) return false;
        if (Double.compare(mat3x3.m23, m23) != 0) return false;
        if (Double.compare(mat3x3.m31, m31) != 0) return false;
        if (Double.compare(mat3x3.m32, m32) != 0) return false;
        if (Double.compare(mat3x3.m33, m33) != 0) return false;
        return Double.compare(mat3x3.determinant, determinant) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(m11);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m12);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m13);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m23);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m31);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m32);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m33);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(determinant);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
