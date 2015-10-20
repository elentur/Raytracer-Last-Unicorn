package matVecLib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by roberto on 10/10/15.
 */
public class Vector3Test {

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testAdd1() throws Exception {

    }

    @Test
    public void testSub() throws Exception {

    }

    @Test
    public void testMul() throws Exception {

    }

    @Test
    public void testDot() throws Exception {
        Normal3 n = new Normal3(1,0,0);
        Vector3 v = new Vector3(1,0,0);
        double r = v.dot(n);
        assertTrue(r == 1);
    }

    @Test
    public void testDot1() throws Exception {
        Normal3 n = new Normal3(1,0,0);
        Vector3 v = new Vector3(0,1,0);
        double r = v.dot(n);
        assertTrue(r == 0);
    }

    @Test
    public void testDot2() throws Exception {
        Vector3 a = new Vector3(1,0,0);
        Vector3 b = new Vector3(1,0,0);
        double r = a.dot(b);
        assertTrue(r == 1);
    }

    @Test
    public void testDot3() throws Exception {
        Vector3 a = new Vector3(1,0,0);
        Vector3 b = new Vector3(0,1,0);
        double r = b.dot(a);
        assertTrue(r == 0);
    }


    @Test
    public void testNormalized() throws Exception {

    }

    @Test
    public void testAsNormal() throws Exception {

    }

    @Test
    public void testReflectedOn() throws Exception {
        Vector3 v = new Vector3(-0.707, 0.707, 0);
        Normal3 n = new Normal3(0,1,0);
        Vector3 r = v.reflectedOn(n);
        assertEquals(new Vector3(0.707,0.707,0),r);
    }

    @Test
    public void testReflectedOn1() throws Exception {
        Vector3 v = new Vector3(0.707, 0.707, 0);
        Normal3 n = new Normal3(1,0,0);
        Vector3 r = v.reflectedOn(n);
        assertEquals(new Vector3(0.707,-0.707,0),r);
    }

    @Test
    public void testX() throws Exception {

    }

    @Test
    public void magnitude() {
        Vector3 v = new Vector3(1,1,1);
        assertEquals(v.magnitude,Math.sqrt(3),0);
    }


}