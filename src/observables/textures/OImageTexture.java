package observables.textures;

import texture.ImageTexture;
import texture.Texture;

/**
 * Created by roberto on 02/02/16.
 */
public class OImageTexture extends AOTexture {
    public OImageTexture(String name, double scaleU, double scaleV, double offsetU, double offsetV, double rotate, String path) {
        super(name, scaleU, scaleV, offsetU, offsetV, rotate, path);
    }

    @Override
    public ImageTexture generate() {
        return new ImageTexture(
                path,
                scaleU,
                scaleV,
                offsetU,
                offsetV,
                rotate
        );
    }
}
