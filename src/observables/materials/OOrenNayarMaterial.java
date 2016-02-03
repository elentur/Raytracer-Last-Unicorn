package observables.materials;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import material.OrenNayarMaterial;
import observables.textures.AOTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OOrenNayarMaterial extends AOMaterial{

    public DoubleProperty roughness = new SimpleDoubleProperty();

    public OOrenNayarMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale, AOTexture irradiance, double roughness) {
        super(name, texture, bumpMap, bumpScale, irradiance);
        this.roughness.setValue(roughness);
    }

    @Override
    public OrenNayarMaterial generate() {
        return new OrenNayarMaterial(
                 texture.get().generate(),
                 roughness.get(),
                 bumpMap.get().generate(),
                 bumpScale.get(),
                 irradiance.get().generate()
        );
    }
}
