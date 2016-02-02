package observables.lights;

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

    public int px;
    public int py;
    public int pz;

    public int dx;
    public int dy;
    public int dz;

    public double halfAngle;

    public OSpotLight(String name, double[] color, boolean castShadow, int photons, double[] pattern, int[] position, int[] direction, double halfAngle) {
        super(name, color, castShadow, photons, pattern);

        this.px = position[0];
        this.py = position[1];
        this.pz = position[2];

        this.dx = direction[0];
        this.dy = direction[1];
        this.dz = direction[2];

        this.halfAngle = halfAngle;
    }


    @Override
    public SpotLight generate() {
        return new SpotLight(
                new Color(colorR,colorG,colorB),
                new Point3(px,py,pz),
                new Vector3(dx,dy,dz),
                halfAngle,
                castShadow,
                photons,
                new LightShadowPattern(patternSize,patternSubdiv)
        );
    }
}
