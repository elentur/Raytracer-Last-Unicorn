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

    public OPointLight(String name, double[] color, double[] position, boolean castShadow, int photons, double[] pattern) {
        super(name, color, castShadow, photons, pattern);
        this.px.setValue(position[0]);
        this.py.setValue(position[1]);
        this.pz.setValue(position[2]);
    }

    @Override
    public PointLight generate() {
        return new PointLight(
                new Color(colorR.get(),colorG.get(),colorB.get()),
                new Point3(px.get(),py.get(),pz.get()),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(),patternSubdiv.get())
        );
    }
}
