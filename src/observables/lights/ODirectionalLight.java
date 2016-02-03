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

    public ODirectionalLight(String name, double[] color, boolean castShadow, int photons, double[] pattern, double[] direction) {
        super(name, color, castShadow, photons, pattern);
        this.dx.setValue(direction[0]);
        this.dy.setValue(direction[1]);
        this.dz.setValue(direction[2]);
    }

    @Override
    public DirectionalLight generate() {
        return new DirectionalLight(
                new Color(colorR.get(),colorG.get(),colorB.get()),
                new Vector3(dx.get(),dy.get(),dz.get()),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(),patternSubdiv.get())
        );
    }
}
