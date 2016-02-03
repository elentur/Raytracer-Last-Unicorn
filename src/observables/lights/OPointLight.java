package observables.lights;

import light.PointLight;
import matVect.Point3;
import sampling.LightShadowPattern;
import utils.Color;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPointLight extends AOLight {

    public int px;
    public int py;
    public int pz;

    public OPointLight(String name, double[] color, int[] position, boolean castShadow, int photons, double[] pattern) {
        super(name, color, castShadow, photons, pattern);
        this.px = position[0];
        this.py = position[1];
        this.pz = position[2];
    }

    @Override
    public PointLight generate() {
        return new PointLight(
                new Color(colorR,colorG,colorB),
                new Point3(px,py,pz),
                castShadow,
                photons,
                new LightShadowPattern(patternSize,patternSubdiv)
        );
    }
}
