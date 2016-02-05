package observables.lights;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import sampling.LightShadowPattern;
import serializable.SElement;
import serializable.lights.SSpotLight;
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

    public OSpotLight() {
        name.set("Spot Light");
        this.px.set(5.0);
        this.py.set(5.0);
        this.pz.set(5.0);

        this.dx.set(-1.0);
        this.dy.set(-1.0);
        this.dz.set(-1.0);

        this.halfAngle.set(20);
    }


    @Override
    public SpotLight generate() {
        return new SpotLight(
                new Color(color.getRed(),color.getGreen(),color.getBlue()),
                new Point3(px.get(),py.get(),pz.get()),
                new Vector3(dx.get(),dy.get(),dz.get()),
                halfAngle.get()*(Math.PI/180),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(),patternSubdiv.get())
        );
    }

    @Override
    public SSpotLight serialize() {
        return new SSpotLight(
                new Color(color.getRed(),color.getGreen(),color.getBlue()),
                new Point3(px.get(),py.get(),pz.get()),
                new Vector3(dx.get(),dy.get(),dz.get()),
                halfAngle.get()*(Math.PI/180),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(),patternSubdiv.get()),
                name.get()
        );
    }
}
