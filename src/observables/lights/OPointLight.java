package observables.lights;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import light.PointLight;
import matVect.Point3;
import sampling.LightShadowPattern;
import serializable.lights.SPointLight;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 *
 * Creates a new OPointLight Object
 */
public class OPointLight extends AOLight {
    /**
     * represents the position x value
     */
    public final DoubleProperty px = new SimpleDoubleProperty();
    /**
     * represents the position y value
     */
    public final DoubleProperty py = new SimpleDoubleProperty();
    /**
     * represents the position z value
     */
    public final DoubleProperty pz = new SimpleDoubleProperty();

    public OPointLight() {
        name.set("Poin Light");
        px.set(5.0);
        py.set(5.0);
        pz.set(5.0);

        patternSize.addListener(a->{
            if(patternSize.get() < 0) patternSize.set(0);
        });

        patternSubdiv.addListener(a->{
            if(patternSubdiv.get() < 1) patternSubdiv.set(1);
        });
    }

    @Override
    public PointLight generate() {
        return new PointLight(
                new Color(color.get().getRed(), color.get().getGreen(), color.get().getBlue()),
                new Point3(px.get(), py.get(), pz.get()),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(), patternSubdiv.get())
        );
    }

    @Override
    public SPointLight serialize() {
        return new SPointLight(
                color.get().getRed(),
                color.get().getGreen(),
                color.get().getBlue(),
                px.get(), py.get(), pz.get(),
                castShadow.get(),
                photons.get(),
                patternSize.get(),
                patternSubdiv.get(),
                name.get()
        );
    }
}
