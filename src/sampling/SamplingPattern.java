package sampling;

import matVect.Point2;

import java.util.Collections;
import java.util.List;

/**
 * This class represents a SamplingPattern Object.
 * The SamplingPattern involves a Pattern for a fixed number of rays for each pixel
 *
 * @author Robert Dziuba on 21.12.2015
 */
public class SamplingPattern extends SubdivisionPattern {

    public SamplingPattern(final int subdiv) {
        super(0.5, subdiv);

    }

    /**
     * generate the list of points (coordinates) of the ray random differences.
     */
    public List<Point2> generateSampling() {

        points.clear();
        if (subdiv == 1) {
            points.add(new Point2(0, 0));
            return points;
        }
        final double v = 2 * size / subdiv;
        for (int i = 0; i < subdiv + 1; i++) {
            final double z = i * v - size;
            points.add(new Point2(z, z));
        }
        Collections.shuffle(points);
        return points;
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

        SamplingPattern that = (SamplingPattern) o;

        return size == that.size && (points != null ? points.equals(that.points) : that.points == null);

    }


}
