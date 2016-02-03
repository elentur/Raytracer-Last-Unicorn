package observables.lights;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import light.PointLight;
import matVect.Point3;
import sampling.LightShadowPattern;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPointLight extends AOLight {

    public DoubleProperty px = new SimpleDoubleProperty();
    public DoubleProperty py = new SimpleDoubleProperty();
    public DoubleProperty pz = new SimpleDoubleProperty();

    public OPointLight() {
        name.set("Poin Light");
        px.set(5.0);
        py.set(5.0);
        pz.set(5.0);
    }

    @Override
    public PointLight generate() {
        return new PointLight(
                new Color(color.getRed(),color.getGreen(),color.getBlue()),
                new Point3(px.get(),py.get(),pz.get()),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(),patternSubdiv.get())
        );
    }
}
