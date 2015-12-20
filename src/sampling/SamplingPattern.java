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

    public final List<Point2> points;

    public SamplingPattern(final double rows, final double columns) {

        if(rows < 1) throw new IllegalArgumentException("The rows cannot be smaller than 1!");
        if(columns < 1) throw new IllegalArgumentException("The columns cannot be smaller than 1!");

        this.rows = rows;
        this.columns = columns;

        points = new ArrayList<>();

        generateSampling();
    }

    private void generateSampling() {
        for(double x = 0; x < rows; x++){
            for(double y = 0; y < columns; y++){
                points.add(new Point2(x,y));
            }
        }
    }
}
