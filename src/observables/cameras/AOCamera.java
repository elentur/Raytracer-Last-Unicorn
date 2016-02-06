package observables.cameras;

import camera.Camera;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import observables.AOElement;
import serializable.SElement;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOCamera extends AOElement {

    public final DoubleProperty ex = new SimpleDoubleProperty(0);
    public final DoubleProperty ey = new SimpleDoubleProperty(0);
    public final DoubleProperty ez = new SimpleDoubleProperty(5);

    public final DoubleProperty gx = new SimpleDoubleProperty(0);
    public final DoubleProperty gy = new SimpleDoubleProperty(0);
    public final DoubleProperty gz = new SimpleDoubleProperty(-1);

    public final DoubleProperty tx = new SimpleDoubleProperty(0);
    public final DoubleProperty ty = new SimpleDoubleProperty(1);
    public final DoubleProperty tz = new SimpleDoubleProperty(0);

    public final IntegerProperty patternSubdiv = new SimpleIntegerProperty(1);

    public abstract Camera generate();

    public abstract SElement serialize();
}
