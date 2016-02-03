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

    public OOrenNayarMaterial() {
        name.set("Oren Nayar Material");
        roughness.set(0.5);
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
