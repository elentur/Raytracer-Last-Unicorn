package matVect;

/**
 * Testclass, for the matrix-vector library
 * Created by Marcus Baetz on 07.10.2015.
 */
public class Main {

    public static void main(String[] args) {
        normalTest();
        pointTest();
        vectorTest();
        matrixTest();
    }

    private static void matrixTest() {
        final Mat3x3 m1 = new Mat3x3(
                1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0);
        final Mat3x3 m2 = new Mat3x3(
                1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0);
        final Mat3x3 m3 = new Mat3x3(
                0.0, 0.0, 1.0,
                0.0, 1.0, 0.0,
                1.0, 0.0, 0.0);
        final Mat3x3 m4 = new Mat3x3(
                3.0, 2.0, 1.0,
                6.0, 5.0, 4.0,
                9.0, 8.0, 7.0);
        final Mat3x3 m5 = new Mat3x3(
                8.0, 2.0, 3.0,
                8.0, 5.0, 6.0,
                8.0, 8.0, 9.0);
        final Mat3x3 m6 = new Mat3x3(
                1.0, 8.0, 3.0,
                4.0, 8.0, 6.0,
                7.0, 8.0, 9.0);
        final Mat3x3 m7 = new Mat3x3(
                1.0, 2.0, 8.0,
                4.0, 5.0, 8.0,
                7.0, 8.0, 8.0);
        final Vector3 v1 = new Vector3(3.0, 2.0, 1.0);
        final Vector3 v2 = new Vector3(8.0, 8.0, 8.0);
        final Point3 p1 = new Point3(3.0, 2.0, 1.0);

        System.out.println("*************MatrixTest**************");
        System.out.print(m1 + "\n * \n" + v1 + "\n = \n" + v1 + " = ");
        System.out.println(v1.equals(m1.mul(v1)));


        System.out.print(m1 + "\n * \n" + p1 + "\n = \n" + p1 + " = ");
        System.out.println(p1.equals(m1.mul(p1)));

        System.out.print(m2 + "\n * \n" + m1 + "\n = \n" + m2 + " = ");
        System.out.println(m2.equals(m2.mul(m1)));

        System.out.print(m2 + "\n * \n" + m3 + "\n = \n" + m4 + " = ");
        System.out.println(m4.equals(m2.mul(m3)));

        System.out.print(m2 + "\n changeCol1 \n" + v2 + "\n = \n" + m5 + " = ");
        System.out.println(m5.equals(m2.col1(v2)));

        System.out.print(m2 + "\n changeCol2 \n" + v2 + "\n = \n" + m6 + " = ");
        System.out.println(m6.equals(m2.col2(v2)));

        System.out.print(m2 + "\n changeCol3 \n" + v2 + "\n = \n" + m7 + " = ");
        System.out.println(m7.equals(m2.col3(v2)));


    }

    private static void vectorTest() {
        final Vector3 v1 = new Vector3(1.0, 1.0, 1.0);
        final Vector3 v2 = new Vector3(1.0, 2.0, 3.0);
        final Vector3 v3 = new Vector3(2.0, 3.0, 4.0);
        final Vector3 v4 = new Vector3(1.5, 3.0, 4.5);
        final Vector3 v5 = new Vector3(-0.707, 0.707, 0.0);
        final Vector3 v6 = new Vector3(0.707, 0.707, 0.0);
        final Vector3 v7 = new Vector3(0.707, -0.707, 0.0);
        final Vector3 v8 = new Vector3(0.0, 1.0, 0.0);
        final Vector3 v9 = new Vector3(-1.0, 0.0, 0.0);
        final Vector3 v10 = new Vector3(0.0, 0.0, 1.0);
        final Normal3 n1 = new Normal3(1.0, 1.0, 1.0);
        final Normal3 n2 = new Normal3(0.0, 1.0, 0.0);
        final Normal3 n3 = new Normal3(1.0, 0.0, 0.0);


        System.out.println("*************VectorTest**************");

        System.out.print("|" + v1 + "| = sqrt(3) = ");
        System.out.println(v1.magnitude == Math.sqrt(3));

        System.out.print("Magnitude of normalized " + v2 + " = 1.0 = ");
        System.out.println(v1.normalized().magnitude == 1.0);

        //add
        System.out.print(v2 + " + " + v1 + " = " + v3 + " = ");
        System.out.println(v3.equals(v2.add(v1)));

        System.out.print(v2 + " + " + n1 + " = " + v3 + " = ");
        System.out.println(v3.equals(v2.add(n1)));

        //sub

        System.out.print(v3 + " - " + n1 + " = " + v2 + " = ");
        System.out.println(v2.equals(v3.sub(n1)));


        //mul

        System.out.print(v2 + " * 1.5 = " + v4 + " = ");
        System.out.println(v4.equals(v2.mul(1.5)));

        //other

        System.out.print(v1 + " as normal = " + n1 + " = ");
        System.out.println(n1.equals(v1.asNormal()));

        System.out.print(v5 + " reflected on " + n2 + " = " + v6 + " = ");
        System.out.println(v6.equals(v5.reflectedOn(n2)));

        System.out.print(v6 + " reflected on " + n3 + " = " + v7 + " = ");
        System.out.println(v7.equals(v6.reflectedOn(n3)));

        System.out.print(v8 + " x " + v9 + " = " + v10 + " = ");
        System.out.println(v10.equals(v8.x(v9)));


    }

    private static void pointTest() {
        final Point3 p1 = new Point3(1.0, 1.0, 1.0);
        final Point3 p2 = new Point3(2.0, 2.0, 0.0);
        final Point3 p3 = new Point3(-3.0, -2.0, -1.0);
        final Point3 p4 = new Point3(5.0, 4.0, 3.0);
        final Vector3 v1 = new Vector3(-1.0, -1.0, 1.0);
        final Vector3 v2 = new Vector3(4.0, 3.0, 2.0);

        System.out.println("*************PointTest**************");
        System.out.print(p1 + " - " + p2 + " = " + v1 + " = ");
        System.out.println(v1.equals(p1.sub(p2)));

        System.out.print(p1 + " - " + v2 + " = " + p3 + " = ");
        System.out.println(p3.equals(p1.sub(v2)));

        System.out.print(p1 + " + " + v2 + " = " + p4 + " = ");
        System.out.println(p4.equals(p1.add(v2)));
    }

    private static void normalTest() {
        final Normal3 n1 = new Normal3(1.0, 2.0, 3.0);
        final Normal3 n2 = new Normal3(0.5, 1.0, 1.5);
        final Normal3 n3 = new Normal3(3.0, 2.0, 1.0);
        final Normal3 n4 = new Normal3(4.0, 4.0, 4.0);
        final Normal3 n5 = new Normal3(1.0, 0.0, 0.0);
        final Normal3 n6 = new Normal3(0.0, 1.0, 0.0);
        final Vector3 v1 = new Vector3(1.0, 0.0, 0.0);
        final Vector3 v2 = new Vector3(0.0, 1.0, 0.0);

        System.out.println("*************NormalTest**************");
        System.out.print(n1 + " * 0.5 = " + n2 + " = ");
        System.out.println(n2.equals(n1.mul(0.5)));

        System.out.print(n1 + " + " + n3 + " = " + n4 + " = ");
        System.out.println(n4.equals(n1.add(n3)));

        System.out.print(n5 + " * " + v1 + " = 1 = ");
        System.out.println(n5.dot(v1) == 1);

        System.out.print(n5 + " * " + v2 + " = 0 = ");
        System.out.println(n5.dot(v2) == 0);

        System.out.print(v1 + " * " + n5 + " = 1 = ");
        System.out.println(v1.dot(n5) == 1);

        System.out.print(v1 + " * " + n6 + " = 0 = ");
        System.out.println(v1.dot(n6) == 0);

        System.out.print(v1 + " * " + v1 + " = 1 = ");
        System.out.println(v1.dot(v1) == 1);

        System.out.print(v1 + " * " + v1 + " = 0 = ");
        System.out.println(v1.dot(v2) == 0);
    }
}
