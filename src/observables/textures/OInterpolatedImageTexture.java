package observables.textures;

import serializable.textures.SInterpolatedImageTexture;
import texture.InterpolatedImageTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 *
 * Creates a new OInterpolatedImageTexture Object
 */
public class OInterpolatedImageTexture extends AOTexture {

    public OInterpolatedImageTexture(String path) {
        name.set("Interpolated Image Texture");
        this.path.set(path);
    }

    @Override
    public InterpolatedImageTexture generate() {
        return new InterpolatedImageTexture(
                img.get(),
                scaleU.get(),
                scaleV.get(),
                offsetU.get(),
                offsetV.get(),
                rotate.get()*Math.PI/180
        );
    }

    @Override
    public SInterpolatedImageTexture serialize() {
        return new SInterpolatedImageTexture(
                path.get(),
                name.get(),
                scaleU.get(),
                scaleV.get(),
                offsetU.get(),
                offsetV.get(),
                rotate.get());
    }
}
