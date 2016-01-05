package sampling;

import matVect.Point2;

import java.util.Random;

/**
 * This class represents a SamplingPattern Object.
 * The SamplingPattern involves a Pattern for a fixed number of rays for each pixel
 *
 * @author Robert Dziuba on 21.12.2015
 */
public class DOFPattern extends SubdivisionPattern {


    public DOFPattern(final int subdiv, final double fStop) {
        super(fStop,subdiv);
        generateSampling();
    }

    /**
     * generate the list of points (coordinates) of the ray random differences.
     */
    protected void generateSampling() {

        Random rn = new Random();
        final double fStopVal = 1.0/size;
        final int to = (int)(subdiv*(4*fStopVal)+1);
        for(int x = 0; x < to; x++){
            for(int y = 0; y < to; y++){
                final double rX = (rn.nextDouble()-fStopVal) / 10;
                final double rY = (rn.nextDouble()-fStopVal) / 10;
                points.add(new Point2((2.0/(subdiv-1) * x*fStopVal -fStopVal) +rX , (2.0/(subdiv-1) * y*fStopVal - fStopVal)+rY));
            }
        }
    }

}
