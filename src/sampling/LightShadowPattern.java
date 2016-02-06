package sampling;

import matVect.Point2;

import java.util.List;
import java.util.Random;

/**
 * This class represents a SamplingPattern Object.
 * The SamplingPattern involves a Pattern for a fixed number of rays for each pixel
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
            for (int x = 0; x < subdiv; x++) {
                for (int y = 0; y < subdiv; y++) {
                    // final double rX = (rn.nextDouble()-size) / 10;
                    // final double rY = (rn.nextDouble()-size) / 10;
                    // points.add(new Point2((2.0/(subdiv-1) * x*size - size) + rX, (2.0/(subdiv-1) * y*size - size) + rY));
                    final double rX = (rn.nextDouble() * size) - size / 2.0;
                    final double rY = (rn.nextDouble() * size) - size / 2.0;
                    //  System.out.println(rX +"  " +rY);
                    points.add(new Point2(rX, rY));
                }
            }
        }

        return points;
    }

}
