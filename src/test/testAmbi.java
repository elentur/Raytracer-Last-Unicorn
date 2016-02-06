package test;

import matVect.Normal3;
import matVect.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Marcus Baetz on 02.02.2016.
 *
 * @author Marcus Bätz
 */
class testAmbi {

    public static void main(String[] args) {
        List<Vector3> testDirections = new ArrayList<>();
        int numberOfRays = 16;
        int numOfHits = 0;
        Random rnd = new Random();
        Normal3 n = new Normal3(0, 1, 0);
        while (testDirections.size() < numberOfRays) {
            Vector3 v = new Vector3(rnd.nextDouble() - 0.5, rnd.nextDouble() - 0.5, rnd.nextDouble() - 0.5);
            if (n.dot(v.normalized()) < Math.PI / 2) {
                testDirections.add(v);
                System.out.println(v);
            }
        }
      /*  Point3 p = new Point3(0,0,0);
        for(Vector3 v : testDirections){
            Ray r = new Ray(p,v);

            for (Geometry g : world.geometries) {
                if(!g.visibility) continue;
                final Hit h = g.hit(r);
                if (h != null && h.t >0.01 && h.t <2) {
                    numOfHits++;
                    break;
                }

            }
        }
        if(numOfHits>0)c= c.mul(1/numOfHits);*/
    }
}
