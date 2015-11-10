package test;

import matVect.Point3;
import matVect.Vector3;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Created by Marcus Baetz on 10.10.2015.
 *
 * @author Marcus Baetz
 */
public class Point3Test {

    @Test
    public void testSubPoint() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Point3 p1 = new Point3(x1, y1, z1);
            final Point3 p2 = new Point3(x2, y2, z2);
            final Vector3 v1 = new Vector3(x1 - x2, y1 - y2, z1 - z2);
            final Vector3 v2 = p1.sub(p2);
            if (!v1.equals(v2)) worked = false;

        }
        assertTrue(worked);
    }

    @Test
    public void testSubVector() throws Exception {
        boolean worked = true;
        final Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            final double x1 = (rnd.nextDouble() * 4000) - 2000;
            final double y1 = (rnd.nextDouble() * 4000) - 2000;
            final double z1 = (rnd.nextDouble() * 4000) - 2000;
            final double x2 = (rnd.nextDouble() * 4000) - 2000;
            final double y2 = (rnd.nextDouble() * 4000) - 2000;
            final double z2 = (rnd.nextDouble() * 4000) - 2000;
            final Point3 p1 = new Point3(x1, y1, z1);
            final Vector3 v1 = new Vector3(x2, y2, z2);
            final Point3 p2 = new Point3(x1 - x2, y1 - y2, z1 - z2);
            final Point3 p3 = p1.sub(v1);
            if (!p2.equals(p3)) worked = false;

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
            final Point3 p1 = new Point3(x1, y1, z1);
            final Vector3 v1 = new Vector3(x2, y2, z2);
            final Point3 p2 = new Point3(x1 + x2, y1 + y2, z1 + z2);
            final Point3 p3 = p1.add(v1);
            if (!p2.equals(p3)) worked = false;

        }
        assertTrue(worked);
    }
}