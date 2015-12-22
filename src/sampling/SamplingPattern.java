package sampling;

import matVect.Point2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class represents a SamplingPattern Object.
 * The SamplingPattern involves a Pattern for a fixed number of rays for each pixel
 *
 * @author Robert Dziuba on 21.12.2015
 */
public class SamplingPattern {

    /**
     *  Square number  of the Rays for each pixel
     */
    public final int size;

    /**
     * List of Points which represents the coordinates of the ray.
     */
    public final List<Point2> points;

    /**
     * SamplingPattern constructor which generate the List of points
     *
     * @param size the subdivision of the pattern
     * @throws IllegalArgumentException if size is lower then one.
     */
    public SamplingPattern(final int size) {

        if(size < 1) throw new IllegalArgumentException("The rows cannot be smaller than 0!");

        this.size = size;

        points = new ArrayList<>();

        generateSampling();
    }

    /**
     * generate the list of points (coordinates) of the ray random differences.
     */
    private void generateSampling() {

        Random rn = new Random();

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                final double rX = (rn.nextDouble()-0.5) / 10;
                final double rY = (rn.nextDouble()-0.5) / 10;
                points.add(new Point2((1.0/size * x - 0.5) + rX, (1.0/size * y - 0.5) + rY));
            }
        }
    }
}
