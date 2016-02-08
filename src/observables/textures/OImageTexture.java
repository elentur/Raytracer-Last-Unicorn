package observables.textures;

import serializable.textures.SImageTexture;
import texture.ImageTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 * <p>
 * Creates a new OImageTexture Object
 */
public class OImageTexture extends AOTexture {
    public OImageTexture(String path) {
        name.set("Image Texture");
        this.path.set(path);
    }

    @Override
    public ImageTexture generate() {
        return new ImageTexture(
                img.get(),
                scaleU.get(),
                scaleV.get(),
                offsetU.get(),
                offsetV.get(),
                rotate.get() * Math.PI / 180
        );
    }

    @Override
    public SImageTexture serialize() {
        return new SImageTexture(
                path.get(),
                name.get(),
                scaleU.get(),
                scaleV.get(),
                offsetU.get(),
                offsetV.get(),
                rotate.get());
    }
}
