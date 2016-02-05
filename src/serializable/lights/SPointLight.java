package serializable.lights;

import light.PointLight;
import matVect.Point3;
import observables.lights.OPointLight;
import sampling.LightShadowPattern;
import serializable.SElement;
import utils.Color;

/**
 * Created by roberto on 05.02.16.
 */
public class SPointLight extends PointLight implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SPointLight(Color color, Point3 position, boolean castShadow, int photons, LightShadowPattern lightShadowPattern, String name) {
        super(color, position, castShadow, photons, lightShadowPattern);
        this.name = name;
    }

    @Override
    public OPointLight generate(){
        OPointLight light = new OPointLight();

        light.color = new javafx.scene.paint.Color(color.r,color.g,color.b,1);
        light.castShadow.set(castsShadow);
        light.photons.set(photons);
        light.patternSize.set(lightShadowPattern.size);
        light.patternSubdiv.set(lightShadowPattern.subdiv);
        light.px.set(position.x);
        light.py.set(position.y);
        light.pz.set(position.z);

        return light;
    }
}
