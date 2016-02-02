package observables.cameras;

import camera.PerspectiveCamera;
import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPerspectiveCamera extends AOCamera {

    public double angle;

    public OPerspectiveCamera(String name, int[] e, int[] g, int[] t, double angle, double[] pattern) {
        super(name, e, g, t, pattern);
        this.angle = angle;
    }

    @Override
    public PerspectiveCamera generate(){
        return new PerspectiveCamera(
                new Point3(ex,ey,ez),
                new Vector3(gx,gy,gz),
                new Vector3(tx,ty,tz),
                this.angle,
                new SamplingPattern(patternSubdiv)
        );
    }
}
