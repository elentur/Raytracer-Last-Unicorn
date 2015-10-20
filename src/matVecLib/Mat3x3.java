package matVecLib;

/**
 * Created by roberto on 07/10/15.
 */
public class Mat3x3 {

    /**
     *
     */
    final public double m11;

    final public double m12;

    final public double m13;

    final public double m21;

    final public double m22;

    final public double m23;

    final public double m31;

    final public double m32;

    final public double m33;

    final public double determinante;


    /**
     *
     * @param m11
     * @param m12
     * @param m13
     * @param m21
     * @param m22
     * @param m23
     * @param m31
     * @param m32
     * @param m33
     */
    public Mat3x3(final double m11, final double m12, final double m13, final double m21, final double m22, final double m23, final double m31, final double m32, final double m33) {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        this.determinante = this.det();
    }

    /**
     *
     * @param m
     * @return
     */
    public Mat3x3 mul(Mat3x3 m) {

        if (m == null) throw new IllegalArgumentException("The parameter 'm' must not be null." );

        return new Mat3x3(
                m11 * m.m11 + m12 * m.m21 + m13 * m.m31,
                m11 * m.m12 + m12 * m.m22 + m13 * m.m32,
                m11 * m.m13 + m12 * m.m23 + m13 * m.m33,

                m21 * m.m11 + m22 * m.m21 + m23 * m.m31,
                m21 * m.m12 + m22 * m.m22 + m23 * m.m32,
                m21 * m.m13 + m22 * m.m23 + m23 * m.m33,

                m31 * m.m11 + m32 * m.m21 + m33 * m.m31,
                m31 * m.m12 + m32 * m.m22 + m33 * m.m32,
                m31 * m.m13 + m32 * m.m23 + m33 * m.m33
        );
    }

    /**
     *
     * @param v
     * @return
     */
    public Vector3 mul(Vector3 v) {

        if (v == null) throw new IllegalArgumentException("The parameter 'v' must not be null." );

        return new Vector3(
                m11 * v.x + m12 * v.y + m13 * v.z,
                m21 * v.x + m22 * v.y + m23 * v.z,
                m31 * v.x + m32 * v.y + m33 * v.z
        );
    }

    /**
     *
     * @param p
     * @return
     */
    public Point3 mul(Point3 p) {

        if (p == null) throw new IllegalArgumentException("The parameter 'p' must not be null." );

        return new Point3(
                m11 * p.x + m12 * p.y + m13 * p.z,
                m21 * p.x + m22 * p.y + m23 * p.z,
                m31 * p.x + m32 * p.y + m33 * p.z
        );
    }

    /**
     *
     * @param v
     * @return
     */
    public Mat3x3 changeCol1(Vector3 v) {
        return new Mat3x3(
                v.x, m12, m13,
                v.y, m22, m23,
                v.z, m32, m33
        );
    }

    /**
     *
     * @param v
     * @return
     */
    public Mat3x3 changeCol2(Vector3 v) {
        return new Mat3x3(
                m11, v.x, m13,
                m21, v.y, m23,
                m31, v.z, m33
        );
    }

    public Mat3x3 changeCol3(Vector3 v) {
        return new Mat3x3(
                m11, m12, v.x,
                m21, m22, v.y,
                m31, m32, v.z
        );
    }

    /**
     *
     * @return
     */
    private double det() {
        return m11 * m22 * m33
                + m12 * m23 * m31
                + m13 * m21 * m32
                - m11 * m23 * m32
                - m12 * m21 * m33
                - m13 * m22 * m31;
    }

    @Override
    public String toString() {
        return "Mat3x3{" +
                "m11=" + m11 +
                ", m12=" + m12 +
                ", m13=" + m13 +
                ", m21=" + m21 +
                ", m22=" + m22 +
                ", m23=" + m23 +
                ", m31=" + m31 +
                ", m32=" + m32 +
                ", m33=" + m33 +
                ", determinante=" + determinante +
                '}';
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
        return Double.compare(mat3x3.determinante, determinante) == 0;

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
        temp = Double.doubleToLongBits(determinante);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
