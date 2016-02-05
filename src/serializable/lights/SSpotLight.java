package serializable.lights;

import light.SpotLight;
import matVect.Point3;
import matVect.Vector3;
import observables.lights.OSpotLight;
import sampling.LightShadowPattern;
import serializable.SElement;
import utils.Color;

/**
 * Created by roberto on 05.02.16.
 */
public class SSpotLight extends SpotLight implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SSpotLight(Color color, Point3 position, Vector3 direction, double halfAngle, boolean castShadow, int photons, LightShadowPattern lightShadowPattern, String name) {
        super(color, position, direction, halfAngle, castShadow, photons, lightShadowPattern);
        this.name = name;
    }

    public OSpotLight generate(){
        OSpotLight light = new OSpotLight();

        light.color = new javafx.scene.paint.Color(color.r,color.g,color.b,1);
        light.castShadow.set(castsShadow);
        light.photons.set(photons);
        light.patternSize.set(lightShadowPattern.size);
        light.patternSubdiv.set(lightShadowPattern.subdiv);

        light.px.set(position.x);
        light.py.set(position.y);
        light.pz.set(position.z);

        light.dx.set(direction.x);
        light.dy.set(direction.y);
        light.dz.set(direction.z);

        return light;
    }
}
