package sampling;

import matVect.Point2;

import java.util.Collections;
import java.util.List;
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
    public List<Point2> generateSampling() {
        points.clear();
        Random rn = new Random();
        final double fStopVal = 1.0/size;
        final int to = subdiv;//(int)(subdiv*((fStopVal+1.0)*4));
        for(int i = 0; i < to; i++){
            for(int y = 0; y < to; y++){
                final double rX = (rn.nextDouble()-0.5) / 10;
                final double rY = (rn.nextDouble()-0.5) / 10;
                points.add(new Point2(
                        (2.0/(subdiv-1) *i*fStopVal -fStopVal) +rX ,
                        (2.0/(subdiv-1) * y*fStopVal - fStopVal)+rY));
            }
        }
        Collections.shuffle(points);
        return points;
    }

}
