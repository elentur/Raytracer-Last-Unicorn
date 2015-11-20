package material;

import camera.Camera;
import light.Light;
import matVect.Normal3;
import matVect.Point3;
import matVect.Vector3;
import utils.Color;
import utils.Hit;
import utils.World;

/**
 * Created by roberto on 17/11/15.
 */
public class OrenNayarBeleuchtungsmodell extends Material {

    public final Color color;
    public final double roughness;

    public OrenNayarBeleuchtungsmodell(Color color, final double roughness) {
        this.color = color;
        this.roughness = roughness;
    }

    @Override
    public Color colorFor(Hit hit, World world) {

        Normal3 n = hit.n;

        return null;
    }
}
