package matVecLib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by roberto on 10/10/15.
 */
public class Normal3Test {

    @Test
    public void testMul() throws Exception {
        Normal3 n = new Normal3(1,2,3);
        Normal3 r = n.mul(0.5);
        assertTrue(new Normal3(0.5,1,1.5).equals(r));
    }

    @Test
    public void testAdd() throws Exception {
        Normal3 a = new Normal3(1,2,3);
        Normal3 b = new Normal3(3,2,1);
        Normal3 r = a.add(b);
        assertTrue(new Normal3(4,4,4).equals(r));
    }

    @Test
    public void testDot1() throws Exception {
        Normal3 n = new Normal3(1,0,0);
        Vector3 v = new Vector3(1,0,0);
        double r = n.dot(v);
        assertTrue(r == 1);
    }

    @Test
    public void testDot2() throws Exception {
        Normal3 n = new Normal3(1,0,0);
        Vector3 v = new Vector3(0,1,0);
        double r = n.dot(v);
        assertTrue(r == 0);
    }
}