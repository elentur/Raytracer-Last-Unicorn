package sampling;

import geometries.Geometry;
import matVect.Point3;
import matVect.Vector3;
import utils.Hit;
import utils.Ray;
import utils.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Marcus Baetz on 04.02.2016.
 *
 * @author Marcus Bätz
 */
public class AmbienOcclusion implements Serializable{
    private static final long serialVersionUID = 1L;

    public double getOcclusion(double size, int subdiv, Hit hit, Point3 p, World world) {
        List<Vector3> testDirections = new ArrayList<>();
        int numberOfRays = subdiv;
        int numOfHits = 0;
        Random rnd = new Random();

        while (testDirections.size() < numberOfRays) {
            Vector3 v = new Vector3(rnd.nextDouble() - 0.5, rnd.nextDouble() - 0.5, rnd.nextDouble() - 0.5);
            double alpha = Math.acos(hit.n.dot(v.normalized()));
            if (alpha < Math.PI / 4 && alpha > 0) {
                testDirections.add(v);
            }

        }
        double ambientOcclusion = 0;

        for (Vector3 v : testDirections) {
            Ray r = new Ray(p, v);

            for (Geometry g : world.geometries) {
                if (!g.visibility) continue;
                final Hit h = g.hit(r);
                final double border = rnd.nextDouble() * size;
                if (h != null && h.t > 0.00001 && h.t < border) {
                    ambientOcclusion += h.t / border;
                    numOfHits++;
                    break;
                }

            }
            if(numOfHits>0)return (ambientOcclusion) / (numOfHits * 1.0);
        }
        return 1;
    }
}
