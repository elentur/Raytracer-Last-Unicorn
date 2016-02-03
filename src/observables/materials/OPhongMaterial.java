package observables.materials;

import material.PhongMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OPhongMaterial extends AOMaterial{

    public AOTexture specular;
    public int exponent;

    public OPhongMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, AOTexture specular, int exponent) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.specular = specular;
        this.exponent = exponent;
    }

    @Override
    public PhongMaterial generate() {
        return new PhongMaterial(
                texture.generate(),
                specular.generate(),
                exponent,
                bumpMap.generate(),
                bumpScale,
                irradiance.generate()
        );
    }
}
