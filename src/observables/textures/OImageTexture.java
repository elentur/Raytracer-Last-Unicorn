package observables.textures;

import texture.ImageTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OImageTexture extends AOTexture {
    public OImageTexture(String path) {
        name.set("Image Texture");
        this.path.set(path);
    }

    @Override
    public ImageTexture generate() {
        return new ImageTexture(
                path.get(),
                scaleU.get(),
                scaleV.get(),
                offsetU.get(),
                offsetV.get(),
                rotate.get()
        );
    }
}
