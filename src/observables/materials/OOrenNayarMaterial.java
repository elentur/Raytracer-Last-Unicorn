package observables.materials;

import material.OrenNayarMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OOrenNayarMaterial extends AOMaterial{

    public double roughness;

    public OOrenNayarMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, double roughness) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.roughness = roughness;
    }

    @Override
    public OrenNayarMaterial generate() {
        return new OrenNayarMaterial(
                 texture.generate(),
                 roughness,
                 bumpMap.generate(),
                 bumpScale,
                 irradiance.generate()
        );
    }
}
