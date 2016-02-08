package observables.materials;

import material.LambertMaterial;
import serializable.materials.SLambertMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 *
 * Creates a new OLambertMaterial Object
 */
public class OLambertMaterial extends AOMaterial {

    public OLambertMaterial() {
        name.set("Lambert Material");
        // uniqueID = name.get();

    }

    @Override
    public LambertMaterial generate() {
        return new LambertMaterial(
                texture.get().generate(),
                bumpMap.get().generate(),
                bumpScale.get(),
                irradiance.get().generate(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get()
        );
    }

    @Override
    public SLambertMaterial serialize() {
        return new SLambertMaterial(
                uniqueID,
                texture.get().serialize(),
                bumpMap.get().serialize(),
                bumpScale.get(),
                irradiance.get().serialize(),
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
