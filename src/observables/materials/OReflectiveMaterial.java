package observables.materials;

import material.ReflectiveMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OReflectiveMaterial extends AOMaterial{
    public AOTexture specular;
    public AOTexture reflection;
    public int exponent;

    public OReflectiveMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, AOTexture specular, AOTexture reflection, int exponent) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.specular = specular;
        this.reflection = reflection;
        this.exponent = exponent;
    }

    @Override
    public ReflectiveMaterial generate() {
        return new ReflectiveMaterial(
                texture.generate(),
                specular.generate(),
                reflection.generate(),
                exponent,
                bumpMap.generate(),
                bumpScale,
                irradiance.generate()
        );
    }
}
