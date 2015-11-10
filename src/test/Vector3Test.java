package test;

import matVect.Normal3;
import matVect.Vector3;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Created by Marcus Baetz on 10.10.2015.
 *
 * @author Marcus Baetz
 */
public class Vector3Test {

    @Test
    public void testAddNormal() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Normal3 n1 = new Normal3(x2, y2, z2);
            final Vector3 v2 = new Vector3(x1 + x2, y1 + y2, z1 + z2);
            final Vector3 v3 = v1.add(n1);
            if (!v2.equals(v3)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testAddVector() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Vector3 v2 = new Vector3(x2, y2, z2);
            final Vector3 v3 = new Vector3(x1 + x2, y1 + y2, z1 + z2);
            final Vector3 v4 = v1.add(v2);
            if (!v4.equals(v3)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testSub() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Normal3 n1 = new Normal3(x2, y2, z2);
            final Vector3 v2 = new Vector3(x1 - x2, y1 - y2, z1 - z2);
            final Vector3 v3 = v1.sub(n1);
            if (!v2.equals(v3)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testMul() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x = (rnd.nextDouble() * 4000) - 2000;
            final double y = (rnd.nextDouble() * 4000) - 2000;
            final double z = (rnd.nextDouble() * 4000) - 2000;
            final double s = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x, y, z);
            final Vector3 v2 = new Vector3(x * s, y * s, z * s);
            final Vector3 v3 = v1.mul(s);
            if (!v2.equals(v3)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testDotVector() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Vector3 v2 = new Vector3(x2, y2, z2);
            final double s1 = x1 * x2 + y1 * y2 + z1 * z2;
            final double s2 = v1.dot(v2);
            if (!(s1 == s2)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testDotNormal() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Normal3 n1 = new Normal3(x2, y2, z2);
            final double s1 = x1 * x2 + y1 * y2 + z1 * z2;
            final double s2 = v1.dot(n1);
            if (!(s1 == s2)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testNormalized() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Vector3 v2 = new Vector3(x1 * (1 / v1.magnitude), y1 * (1 / v1.magnitude), z1 * (1 / v1.magnitude));
            final Vector3 v3 = v1.normalized();
            if (!(v2.equals(v3))) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testAsNormal() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Normal3 n1 = new Normal3(x1, y1, z1);
            final Normal3 n2 = v1.asNormal();
            if (!(n1.equals(n2))) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testReflectedOn() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1).normalized();
            final Normal3 n1 = new Vector3(x2, y2, z2).normalized().asNormal();
            final Vector3 v3 = v1.reflectedOn(n1);
            if (!(v1.equals(v3.reflectedOn(n1)))) {
                System.out.println(i + "  " + v1 + "   " + v3.reflectedOn(n1));

                worked = false;
            }

        }
        assertTrue(worked);
    }

    @Test
    public void testX() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Vector3 v1 = new Vector3(x1, y1, z1);
            final Vector3 v2 = new Vector3(x2, y2, z2);
            final Vector3 v4 = v1.x(v2);
            if (!(v4.dot(v1) < 0.00001 && v4.dot(v2) < 0.00001)) {
                System.out.println(v4.dot(v1));
                worked = false;
            }

        }
        assertTrue(worked);
    }
}