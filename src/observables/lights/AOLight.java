package observables.lights;

import light.Light;
import observables.AOElement;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOLight extends AOElement {
    public double colorR;
    public double colorG;
    public double colorB;
    public boolean castShadow;
    public int photons;
    public double patternSize;
    public int patternSubdiv;

    public AOLight(String name, double[] color, boolean castShadow, int photons, double[] pattern) {
        super(name);
        this.colorR = color[0];
        this.colorG = color[1];
        this.colorB = color[2];
        this.castShadow = castShadow;
        this.photons = photons;
        this.patternSize = pattern[0];
        this.patternSubdiv = (int) pattern[1];
    }

    public abstract Light generate();
}
