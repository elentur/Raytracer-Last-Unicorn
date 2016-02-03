package observables.lights;

import javafx.beans.property.*;
import light.Light;
import observables.AOElement;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOLight extends AOElement {
    public DoubleProperty colorR = new SimpleDoubleProperty();
    public DoubleProperty colorG = new SimpleDoubleProperty();
    public DoubleProperty colorB = new SimpleDoubleProperty();
    public BooleanProperty castShadow = new SimpleBooleanProperty();
    public IntegerProperty photons = new SimpleIntegerProperty();
    public DoubleProperty patternSize = new SimpleDoubleProperty();
    public IntegerProperty patternSubdiv = new SimpleIntegerProperty();

    public AOLight(String name, double[] color, boolean castShadow, int photons, double[] pattern) {
        super(name);
        this.colorR.setValue(color[0]);
        this.colorG.setValue(color[1]);
        this.colorB.setValue( color[2]);
        this.castShadow.setValue( castShadow);
        this.photons.setValue(photons);
        this.patternSize.setValue(pattern[0]);
        this.patternSubdiv.setValue( (int) pattern[1]);
    }

    public abstract Light generate();
}
