package observables.cameras;

import camera.Camera;
import observables.AOElement;

/**
 * Created by roberto on 02/02/16.
 */
public abstract class AOCamera extends AOElement{

    public int ex;
    public int ey;
    public int ez;

    public int gx;
    public int gy;
    public int gz;

    public int tx;
    public int ty;
    public int tz;

    public double patternSize;
    public int patternSubdiv;

    public AOCamera(String name, int[] e, int[] g, int[] t, double[] pattern) {
        super(name);
        this.ex = e[0];
        this.ey = e[1];
        this.ez = e[2];

        this.gx = g[0];
        this.gy = g[1];
        this.gz = g[2];

        this.tx = t[0];
        this.ty = t[1];
        this.tz = t[2];

        this.patternSize = pattern[0];
        this.patternSubdiv = (int) pattern[1];
    }


    public abstract Camera generate();
}
