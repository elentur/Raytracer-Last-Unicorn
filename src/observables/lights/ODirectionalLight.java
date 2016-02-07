package observables.lights;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import light.DirectionalLight;
import matVect.Vector3;
import sampling.LightShadowPattern;
import serializable.lights.SDirectionalLight;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class ODirectionalLight extends AOLight {
    public final DoubleProperty dx = new SimpleDoubleProperty();
    public final DoubleProperty dy = new SimpleDoubleProperty();
    public final DoubleProperty dz = new SimpleDoubleProperty();

    public ODirectionalLight() {
        name.set("Directional Light");
        dx.set(0.0);
        dy.set(0.0);
        dz.set(-1.0);

        patternSize.addListener(a->{
            if(patternSize.get() < 0) patternSize.set(0);
        });

        patternSubdiv.addListener(a->{
            if(patternSubdiv.get() < 1) patternSubdiv.set(1);
        });
    }

    @Override
    public DirectionalLight generate() {
        return new DirectionalLight(
                new Color(color.get().getRed(), color.get().getGreen(), color.get().getBlue()),
                new Vector3(dx.get(), dy.get(), dz.get()),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(), patternSubdiv.get())
        );
    }

    public SDirectionalLight serialize() {
        return new SDirectionalLight(
                color.get().getRed(),
                color.get().getGreen(),
                color.get().getBlue(),
                dx.get(), dy.get(), dz.get(),
                castShadow.get(),
                photons.get(),
                patternSize.get(),
                patternSubdiv.get(),
                name.get()
        );
    }
}
