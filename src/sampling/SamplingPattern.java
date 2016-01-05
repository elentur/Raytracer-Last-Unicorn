package sampling;

import matVect.Point2;

import java.util.Random;

/**
 * This class represents a SamplingPattern Object.
 * The SamplingPattern involves a Pattern for a fixed number of rays for each pixel
 *
 * @author Robert Dziuba on 21.12.2015
 */
public class SamplingPattern extends SubdivisionPattern{

    public SamplingPattern(final int subdiv) {
    super(0.5, subdiv);

        generateSampling();
    }

    /**
     * generate the list of points (coordinates) of the ray random differences.
     */
    protected void generateSampling() {

        Random rn = new Random();

        for(int x = 0; x < subdiv; x++){
            for(int y = 0; y < subdiv; y++){
                final double rX = (rn.nextDouble()-size) / 10;
                final double rY = (rn.nextDouble()-size) / 10;
                points.add(new Point2((1.0/subdiv * x - size) + rX, (1.0/subdiv * y - size) + rY));
            }
        }
    }
}
