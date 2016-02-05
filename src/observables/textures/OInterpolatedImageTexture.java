package observables.textures;

import serializable.textures.SImageTexture;
import texture.InterpolatedImageTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
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
                rotate.get()
        );
    }

    @Override
    public SImageTexture serialize() {
        return new SImageTexture(
                path.get(),
                name.get());
    }
}
