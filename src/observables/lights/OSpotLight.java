package observables.lights;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import sampling.LightShadowPattern;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSpotLight extends AOLight {

    public DoubleProperty px = new SimpleDoubleProperty();
    public DoubleProperty py = new SimpleDoubleProperty();
    public DoubleProperty pz = new SimpleDoubleProperty();

    public DoubleProperty dx = new SimpleDoubleProperty();
    public DoubleProperty dy = new SimpleDoubleProperty();
    public DoubleProperty dz = new SimpleDoubleProperty();

    public DoubleProperty halfAngle = new SimpleDoubleProperty();

    public OSpotLight(String name, double[] color, boolean castShadow, int photons, double[] pattern, double[] position, double[] direction, double halfAngle) {
        super(name, color, castShadow, photons, pattern);

        this.px.setValue(position[0]);
        this.py.setValue(position[1]);
        this.pz.setValue(position[2]);

        this.dx.setValue(direction[0]);
        this.dy.setValue(direction[1]);
        this.dz.setValue(direction[2]);

        this.halfAngle.setValue(halfAngle);
    }


    @Override
    public SpotLight generate() {
        return new SpotLight(
                new Color(colorR.get(),colorG.get(),colorB.get()),
                new Point3(px.get(),py.get(),pz.get()),
                new Vector3(dx.get(),dy.get(),dz.get()),
                halfAngle.get(),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(),patternSubdiv.get())
        );
    }
}
