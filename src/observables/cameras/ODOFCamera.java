package observables.cameras;

import camera.DOFCamera;
import matVect.Point3;
import matVect.Vector3;
import sampling.DOFPattern;
import sampling.SamplingPattern;

/**
 * Created by roberto on 02/02/16.
 */
public class ODOFCamera extends AOCamera{
    public double angle;
    public int dPatternSubdiv;
    public double dPatternFStop;
    public double focalLength;

    public ODOFCamera(String name, int[] e, int[] g, int[] t, double angle, double[] pattern, double[] dPattern, double focalLength) {
        super(name, e, g, t, pattern);
        this.angle = angle;
        this.dPatternSubdiv = (int) dPattern[0];
        this.dPatternFStop = dPattern[1];
        this.focalLength = focalLength;
    }

    @Override
    public DOFCamera generate() {
        return new DOFCamera(
                new Point3(ex,ey,ez),
                new Vector3(gx,gy,gz),
                new Vector3(tx,ty,tz),
                this.angle,
                new DOFPattern(dPatternSubdiv,dPatternFStop),
                focalLength,
                new SamplingPattern(patternSubdiv)
        );
    }
}


