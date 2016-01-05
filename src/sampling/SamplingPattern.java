package sampling;

import matVect.Point2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by roberto on 20/12/15.
 */
public class SamplingPattern {

    public final double rows;

    public final double columns;

    private final List<Point2> points;

    public SamplingPattern(final double rows, final double columns) {

        if(rows < 0) throw new IllegalArgumentException("The rows cannot be smaller than 0!");
        if(columns < 0) throw new IllegalArgumentException("The columns cannot be smaller than 0!");

        this.rows = rows;
        this.columns = columns;

        points = new ArrayList<>();
    }

    public List<Point2> generateSampling() {
        for(double x = 0; x < columns; x++){
            for(double y = 0; y < rows; y++){
                points.add(new Point2(1.0/columns * x - 0.5, 1.0/rows * y - 0.5));
            }
        }

        return points;
    }
}
