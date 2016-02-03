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

    public DoubleProperty ex = new SimpleDoubleProperty();
    public DoubleProperty ey = new SimpleDoubleProperty();
    public DoubleProperty ez = new SimpleDoubleProperty();

    public DoubleProperty gx = new SimpleDoubleProperty();
    public DoubleProperty gy = new SimpleDoubleProperty();
    public DoubleProperty gz = new SimpleDoubleProperty();

    public DoubleProperty tx = new SimpleDoubleProperty();
    public DoubleProperty ty = new SimpleDoubleProperty();
    public DoubleProperty tz = new SimpleDoubleProperty();

    public DoubleProperty patternSize = new SimpleDoubleProperty();
    public IntegerProperty patternSubdiv = new SimpleIntegerProperty();

    public AOCamera(String name, double[] e, double[] g, double[] t, double[] pattern) {
        super(name);
        this.ex.setValue(e[0]);
        this.ey.setValue(e[1]);
        this.ez.setValue(e[2]);

        this.gx.setValue(g[0]);
        this.gy.setValue(g[1]);
        this.gz.setValue(g[2]);

        this.tx.setValue(t[0]);
        this.ty.setValue(t[1]);
        this.tz.setValue(t[2]);

        this.patternSize.setValue(pattern[0]);
        this.patternSubdiv.setValue( pattern[1]);
    }


    public abstract Camera generate();
}
