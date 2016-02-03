package observables.lights;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import light.DirectionalLight;
import matVect.Vector3;
import sampling.LightShadowPattern;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class ODirectionalLight extends AOLight {
    public DoubleProperty dx = new SimpleDoubleProperty();
    public DoubleProperty dy = new SimpleDoubleProperty();
    public DoubleProperty dz = new SimpleDoubleProperty();

    public ODirectionalLight() {
        name.set("Directional Light");
        dx.set(0.0);
        dy.set(0.0);
        dz.set(-1.0);
    }

    @Override
    public DirectionalLight generate() {
        return new DirectionalLight(
                new Color(color.getRed(),color.getGreen(),color.getBlue()),
                new Vector3(dx.get(),dy.get(),dz.get()),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(),patternSubdiv.get())
        );
    }
}
