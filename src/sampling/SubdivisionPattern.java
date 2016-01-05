package sampling;

import matVect.Point2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a SamplingPattern Object.
 * The SamplingPattern involves a Pattern for a fixed number of rays for each pixel
 *
 * @author Robert Dziuba on 21.12.2015
 */
 abstract class SubdivisionPattern {

    /**
     *  size of the pattern
     */
    public final double size;
    /**
     *  Square number  of the Rays for each pixel
     */
    public final int subdiv;

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
    public SubdivisionPattern(final double size, final int subdiv) {

        if(size < 0) throw new IllegalArgumentException("The size cannot be smaller than 0!");
        if(subdiv < 1) throw new IllegalArgumentException("The subdivisions cannot be smaller than 1!");
        this.size = size;
        this.subdiv=subdiv;

        points = new ArrayList<>();

        generateSampling();
    }

    /**
     * generate the list of points (coordinates) of the ray random differences.
     */
    protected abstract void generateSampling() ;

    @Override
    public String toString() {
        return "SubdivisionPattern{" +
                "size=" + size +
                ", subdiv=" + subdiv +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubdivisionPattern that = (SubdivisionPattern) o;

        if (Double.compare(that.size, size) != 0) return false;
        if (subdiv != that.subdiv) return false;
        return !(points != null ? !points.equals(that.points) : that.points != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(size);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + subdiv;
        result = 31 * result + (points != null ? points.hashCode() : 0);
        return result;
    }
}
