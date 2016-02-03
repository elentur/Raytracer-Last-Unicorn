package observables.materials;

import material.LambertMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OLambertMaterial extends AOMaterial{

    public OLambertMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance) {
        super(name, texture, bumpMap, bumpScale, irradiance);
    }

    @Override
    public LambertMaterial generate() {
        return new LambertMaterial(
                texture.generate(),
                bumpMap.generate(),
                bumpScale,
                irradiance.generate()
        );
    }
}
