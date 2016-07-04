package sampling;

import matVect.Point2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class represents a SamplingPattern to simulate sized LightSources
 *
 * @author Robert Dziuba on 21.12.2015
 */
public class LightShadowPattern extends SubdivisionPattern {


    public LightShadowPattern(final double size, final int subdiv) {
        super(size, subdiv);
        generateSampling();
    }

    /**
     * generate the list of points (coordinates) of the ray random differences.
     */
    public List<Point2> generateSampling() {

        Random rn = new Random();
        points.clear();
        if (size == 0) {
            points.add(new Point2(0, 0));

        } else {
           /* for (int x = 0; x < subdiv; x++) {
                for (int y = 0; y < subdiv; y++) {
                    // final double rX = (rn.nextDouble()-size) / 10;
                    // final double rY = (rn.nextDouble()-size) / 10;
                    // points.add(new Point2((2.0/(subdiv-1) * x*size - size) + rX, (2.0/(subdiv-1) * y*size - size) + rY));
                    final double rX = (rn.nextDouble() * size) - size / 2.0;
                    final double rY = (rn.nextDouble() * size) - size / 2.0;
                    //  System.out.println(rX +"  " +rY);
                    points.add(new Point2(rX, rY));
                }
            }*/
            final List<Double> xCord = new ArrayList<>();
            final double v = 2 * size / subdiv;
            for (int i = 0; i < subdiv + 1; i++) {
                final double z = i * v - size;
                points.add(new Point2(z, z));
                xCord.add(z);
            }
            //Collections.shuffle(points);
            Collections.shuffle(xCord);
            for (int i = 0; i < points.size(); i++) {
                points.set(i, new Point2(xCord.get(i), points.get(i).y));

            }
        }

        return points;
    }

}
