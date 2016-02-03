package observables.materials;

import material.SingleColorMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;
import texture.SingleColorTexture;

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
                bumpScale.get()
        );
    }
}
