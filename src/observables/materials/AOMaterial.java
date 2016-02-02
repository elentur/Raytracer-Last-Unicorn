package observables.materials;

import light.Light;
import material.Material;
import observables.AOElement;
import observables.textures.AOTexture;

/**
 * Created by roberto on 02/02/16.
 */
public abstract class AOMaterial extends AOElement {

    public AOTexture texture;
    public AOTexture bumpMap;
    public double bumpScale;
    public AOTexture irradiance;

    public AOMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance) {
        super(name);
        this.texture = texture;
        this.bumpMap = bumpMap;
        this.bumpScale = bumpScale;
        this.irradiance = irradiance;
    }

    public abstract Material generate();
}
