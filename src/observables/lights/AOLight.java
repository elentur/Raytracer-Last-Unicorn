package observables.lights;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import light.Light;
import observables.AOElement;
import serializable.lights.SLight;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new OLight
 */
public abstract class AOLight extends AOElement {
    /**
     * represents the Color and amount of the Light
     */
    public final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.WHITE);
    /**
     * represents whether or not the light casts shadows
     */
    public final BooleanProperty castShadow = new SimpleBooleanProperty(true);
    /**
     * represents the amount of photons the light casts(not implemented)
     */
    public final IntegerProperty photons = new SimpleIntegerProperty(500);
    /**
     * represents the light size
     */
    public final DoubleProperty patternSize = new SimpleDoubleProperty(0.0);
    /**
     * represents the subdivs for shadow calculations
     */
    public final IntegerProperty patternSubdiv = new SimpleIntegerProperty(1);

    /**
     * Creates a new Light for rendering
     *
     * @return a Light-Object
     */
    public abstract Light generate();

    /**
     * Creates a new SLight for saving
     *
     * @return a SLight-Object
     */
    public abstract SLight serialize();

    /**
     * @return a copy of the light
     */
    public AOLight getInstance() {
        SLight l = this.serialize();
        return l.generate();
    }
}
