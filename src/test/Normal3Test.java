package test;

import matVect.Normal3;
import matVect.Vector3;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Marcus Baetz on 10.10.2015.
 *
 * @author Marcus Baetz
 */
public class Normal3Test {

    @Test
    public void testMul() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x = (rnd.nextDouble() * 4000) - 2000;
            final double y = (rnd.nextDouble() * 4000) - 2000;
            final double z = (rnd.nextDouble() * 4000) - 2000;
            final double s = (rnd.nextDouble() * 4000) - 2000;
            final Normal3 n1 = new Normal3(x, y, z);
            final Normal3 n2 = new Normal3(x * s, y * s, z * s);
            final Normal3 n3 = n1.mul(s);
            if (!n3.equals(n2)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testAdd() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Normal3 n1 = new Normal3(x1, y1, z1);
            final Normal3 n2 = new Normal3(x2, y2, z2);
            final Normal3 n3 = new Normal3(x1 + x2, y1 + y2, z1 + z2);
            final Normal3 n4 = n1.add(n2);
            if (!n3.equals(n4)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testDot() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Normal3 n1 = new Normal3(x1, y1, z1);
            final Vector3 v1 = new Vector3(x2, y2, z2);
            final double s1 = x1 * x2 + y1 * y2 + z1 * z2;
            final double s2 = n1.dot(v1);
            if (!(s1 == s2)) worked = false;

        }
        assertTrue(worked);
    }
}