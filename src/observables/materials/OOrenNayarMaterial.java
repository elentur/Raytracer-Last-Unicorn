package observables.materials;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import material.OrenNayarMaterial;
import serializable.materials.SOrenNayarMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OOrenNayarMaterial extends AOMaterial {

    public final DoubleProperty roughness = new SimpleDoubleProperty();

    public OOrenNayarMaterial() {
        name.set("Oren Nayar Material");
        roughness.set(0.5);
        roughness.addListener(a -> {
            if(roughness.get() < 0.0 && roughness.get() > 1.0) roughness.set(0.0);
            refreshMaterial();
        });
    }

    @Override
    public OrenNayarMaterial generate() {
        return new OrenNayarMaterial(
                texture.get().generate(),
                roughness.get(),
                bumpMap.get().generate(),
                bumpScale.get(),
                irradiance.get().generate(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get()
        );
    }

    @Override
    public SOrenNayarMaterial serialize() {
        return new SOrenNayarMaterial(
                uniqueID,
                texture.get().serialize(),
                bumpMap.get().serialize(),
                bumpScale.get(),
                irradiance.get().serialize(),
                roughness.get(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get(),
                name.get()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AOMaterial that = (AOMaterial) o;
        return uniqueID.equals(that.uniqueID);
    }
}
