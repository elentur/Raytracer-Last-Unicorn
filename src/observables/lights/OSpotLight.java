package observables.lights;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import sampling.LightShadowPattern;
import serializable.lights.SSpotLight;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSpotLight extends AOLight {

    public final DoubleProperty px = new SimpleDoubleProperty();
    public final DoubleProperty py = new SimpleDoubleProperty();
    public final DoubleProperty pz = new SimpleDoubleProperty();

    public final DoubleProperty dx = new SimpleDoubleProperty();
    public final DoubleProperty dy = new SimpleDoubleProperty();
    public final DoubleProperty dz = new SimpleDoubleProperty();

    public final DoubleProperty halfAngle = new SimpleDoubleProperty();

    public OSpotLight() {
        name.set("Spot Light");
        this.px.set(5.0);
        this.py.set(5.0);
        this.pz.set(5.0);

        this.dx.set(-1.0);
        this.dy.set(-1.0);
        this.dz.set(-1.0);

        this.halfAngle.set(20);

        patternSize.addListener(a->{
            if(patternSize.get() < 0) patternSize.set(0);
        });

        patternSubdiv.addListener(a->{
            if(patternSubdiv.get() < 1) patternSubdiv.set(1);
        });
    }


    @Override
    public SpotLight generate() {
        return new SpotLight(
                new Color(color.get().getRed(), color.get().getGreen(), color.get().getBlue()),
                new Point3(px.get(), py.get(), pz.get()),
                new Vector3(dx.get(), dy.get(), dz.get()),
                halfAngle.get() * (Math.PI / 180),
                castShadow.get(),
                photons.get(),
                new LightShadowPattern(patternSize.get(), patternSubdiv.get())
        );
    }

    @Override
    public SSpotLight serialize() {
        return new SSpotLight(
                color.get().getRed(), color.get().getGreen(), color.get().getBlue(),
                px.get(), py.get(), pz.get(),
                dx.get(), dy.get(), dz.get(),
                halfAngle.get(),
                castShadow.get(),
                photons.get(),
                patternSize.get(), patternSubdiv.get(),
                name.get()
        );
    }
}
