package observables.lights;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import light.Light;
import observables.AOElement;
import serializable.SElement;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOLight extends AOElement {
    public final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.WHITE);
    public final BooleanProperty castShadow = new SimpleBooleanProperty(true);
    public final IntegerProperty photons = new SimpleIntegerProperty(500);
    public final DoubleProperty patternSize = new SimpleDoubleProperty(0.0);
    public final IntegerProperty patternSubdiv = new SimpleIntegerProperty(1);

    public abstract Light generate();

    public abstract SElement serialize();
}
