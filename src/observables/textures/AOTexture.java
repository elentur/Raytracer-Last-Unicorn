package observables.textures;

import observables.AOElement;
import texture.Texture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public abstract class AOTexture extends AOElement {
    public double scaleU;
    public double scaleV;
    public double offsetU;
    public double offsetV;
    public double rotate;
    public String path;

    public AOTexture(String name, double scaleU, double scaleV, double offsetU, double offsetV, double rotate, String path) {
        super(name);
        this.scaleU = scaleU;
        this.scaleV = scaleV;
        this.offsetU = offsetU;
        this.offsetV = offsetV;
        this.rotate = rotate;
        this.path = path;
    }

    public abstract Texture generate();
}
