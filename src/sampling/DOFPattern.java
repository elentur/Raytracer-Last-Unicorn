package sampling;

import matVect.Point2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a SamplingPattern Object.
 * The SamplingPattern involves a Pattern for a fixed number of rays for each pixel
 *
 * @author Robert Dziuba on 21.12.2015
 */
public class DOFPattern {


    private final double fStop;
    /**
     *  Square number  of the Rays for each pixel
     */
    public final int size;

    /**
     * List of Points which represents the coordinates of the ray.
     */
    public final List<Point2> points;

    public DOFPattern(final int size, final double fStop) {
        if(size < 1) throw new IllegalArgumentException("The rows cannot be smaller than 1!");
        if(fStop < 1) throw new IllegalArgumentException("The F-Stop cannot be smaller than 1!");
        this.fStop=fStop;
        this.size=size;
        points = new ArrayList<>();
        generateSampling();
    }

    /**
     * generate the list of points (coordinates) of the ray random differences.
     */
    private void generateSampling() {

        Random rn = new Random();
        final double fStopVal = 1.0/fStop;
        final int to = (int)(size*(4*fStopVal)+1);
        for(int x = 0; x < to; x++){
            for(int y = 0; y < to; y++){
                final double rX = (rn.nextDouble()-fStopVal) / 10;
                final double rY = (rn.nextDouble()-fStopVal) / 10;
                points.add(new Point2((2.0/size * x*fStopVal -fStopVal) +rX , (2.0/size * y*fStopVal - fStopVal)+rY));
            }
        }
    }

    @Override
    public String toString() {
        return "SamplingPattern{" +
                "size=" + size +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DOFPattern that = (DOFPattern) o;

        return size == that.size && (points != null ? points.equals(that.points) : that.points == null);

    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + (points != null ? points.hashCode() : 0);
        return result;
    }
}
