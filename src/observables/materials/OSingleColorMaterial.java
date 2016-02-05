package observables.materials;

import material.SingleColorMaterial;
import serializable.materials.SSingleColorMaterial;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OSingleColorMaterial extends AOMaterial {

    public OSingleColorMaterial(){
        name.set("Single Color Material");
    }

    @Override
    public SingleColorMaterial generate() {
        return new SingleColorMaterial(
                texture.get().generate(),
                bumpMap.get().generate(),
                bumpScale.get(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get()
        );
    }

    @Override
    public SSingleColorMaterial serialize() {
        return new SSingleColorMaterial(
                texture.get().serialize(),
                bumpMap.get().serialize(),
                bumpScale.get(),
                ambientOcclusion.get(),
                ambientSize.get(),
                ambientSubdiv.get(),
                name.get()
        );
    }
}
