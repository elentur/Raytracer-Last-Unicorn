package serializable.lights;

import light.DirectionalLight;
import matVect.Vector3;
import observables.lights.ODirectionalLight;
import sampling.LightShadowPattern;
import serializable.SElement;
import utils.Color;

/**
 * Created by roberto on 05.02.16.
 */
public class SDirectionalLight extends DirectionalLight implements SElement {

    private static final long serialVersionUID = 1L;

    private final String name;

    public SDirectionalLight(Color color, Vector3 direction, boolean castShadow, int photons, LightShadowPattern lightShadowPattern, String name) {
        super(color, direction, castShadow, photons, lightShadowPattern);
        this.name = name;
    }

    @Override
    public ODirectionalLight generate(){
        ODirectionalLight light = new ODirectionalLight();

        light.color = new javafx.scene.paint.Color(color.r,color.g,color.b,1);
        light.castShadow.set(castsShadow);
        light.photons.set(photons);
        light.patternSize.set(lightShadowPattern.size);
        light.patternSubdiv.set(lightShadowPattern.subdiv);
        light.dx.set(direction.x);
        light.dy.set(direction.y);
        light.dz.set(direction.z);

        return light;
    }
}
