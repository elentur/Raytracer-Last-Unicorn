package observables.materials;


import material.SingleColorMaterial;
import observables.textures.AOTexture;
import observables.textures.OSingleColorTexture;

/**
 * Created by roberto on 02/02/16.
 */
public class OSingleColorMaterial extends AOMaterial {
    public OSingleColorMaterial(String name, AOTexture texture, AOTexture bumpMap, double bumpScale) {
        super(name, texture, bumpMap, bumpScale, new OSingleColorTexture(new double[] {0.0,0.0,0.0}));
    }

    @Override
    public SingleColorMaterial generate() {
        return new SingleColorMaterial(
                texture.generate(),
                bumpMap.generate(),
                bumpScale
        );
    }
}
