package observables.cameras;

import camera.OrthographicCamera;
import camera.PerspectiveCamera;
import matVect.Point3;
import matVect.Vector3;
import sampling.SamplingPattern;

/**
 * Created by roberto on 02/02/16.
 */
public class OOrthographicCamera extends AOCamera{

    public double s;

    public OOrthographicCamera(String name, int[] e, int[] g, int[] t, double s, double[] pattern) {
        super(name, e, g, t, pattern);
        this.s = s;
    }

    @Override
    public OrthographicCamera generate(){
        return new OrthographicCamera(
                new Point3(ex,ey,ez),
                new Vector3(gx,gy,gz),
                new Vector3(tx,ty,tz),
                this.s,
                new SamplingPattern(patternSubdiv)
        );
    }
}
