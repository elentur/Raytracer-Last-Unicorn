package observables.materials;

import material.TransparentMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OTransparentMaterial extends AOMaterial {
    public double indexOfRefraction;
    public AOTexture specular;
    public AOTexture reflection;
    public int exponent;

    public OTransparentMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, double indexOfRefraction, AOTexture specular, AOTexture reflection, int exponent) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.indexOfRefraction = indexOfRefraction;
        this.specular = specular;
        this.reflection = reflection;
        this.exponent = exponent;
    }

    @Override
    public TransparentMaterial generate() {
        return new TransparentMaterial(
                texture.generate(),
                specular.generate(),
                reflection.generate(),
                exponent,
                indexOfRefraction,
                bumpMap.generate(),
                bumpScale,
                irradiance.generate()
        );
    }
}
