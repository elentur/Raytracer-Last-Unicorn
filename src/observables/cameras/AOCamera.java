package observables.cameras;

import camera.Camera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import observables.AOElement;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOCamera extends AOElement{

    public DoubleProperty ex = new SimpleDoubleProperty(0);
    public DoubleProperty ey = new SimpleDoubleProperty(0);
    public DoubleProperty ez = new SimpleDoubleProperty(5);

    public DoubleProperty gx = new SimpleDoubleProperty(0);
    public DoubleProperty gy = new SimpleDoubleProperty(0);
    public DoubleProperty gz = new SimpleDoubleProperty(-1);

    public DoubleProperty tx = new SimpleDoubleProperty(0);
    public DoubleProperty ty = new SimpleDoubleProperty(1);
    public DoubleProperty tz = new SimpleDoubleProperty(0);

    public DoubleProperty patternSize = new SimpleDoubleProperty(0);
    public IntegerProperty patternSubdiv = new SimpleIntegerProperty(1);

    public abstract Camera generate();
}
