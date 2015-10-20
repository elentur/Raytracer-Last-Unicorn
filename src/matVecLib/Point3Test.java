package matVecLib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by roberto on 10/10/15.
 */
public class Point3Test {

    @Test
    public void testAdd() throws Exception {
        Point3 p = new Point3(1,1,1);
        Vector3 v = new Vector3(4,3,2);
        Point3 r = p.add(v);
        assertTrue(r.equals(new Point3(5,4,3)));
    }

    @Test
    public void testSub() throws Exception {
        Point3 a = new Point3(1,1,1);
        Point3 b = new Point3(2,2,0);
        Vector3 r = a.sub(b);
        assertTrue(r.equals(new Vector3(-1,-1,1)));
    }

    @Test
    public void testSub1() throws Exception {
        Point3 p = new Point3(1,1,1);
        Vector3 v = new Vector3(4,3,2);
        Point3 r = p.sub(v);
        assertTrue(r.equals(new Point3(-3,-2,-1)));
    }
}