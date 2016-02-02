package observables.lights;

import light.DirectionalLight;
import matVect.Vector3;
import sampling.LightShadowPattern;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class ODirectionalLight extends AOLight {
    public double dx;
    public double dy;
    public double dz;

    public ODirectionalLight(String name, double[] color, boolean castShadow, int photons, double[] pattern, double[] direction) {
        super(name, color, castShadow, photons, pattern);
        this.dx = direction[0];
        this.dy = direction[1];
        this.dz = direction[2];
    }

    @Override
    public DirectionalLight generate() {
        return new DirectionalLight(
                new Color(colorR,colorG,colorB),
                new Vector3(dx,dy,dz),
                castShadow,
                photons,
                new LightShadowPattern(patternSize,patternSubdiv)
        );
    }
}
