package observables.textures;

import texture.InterpolatedImageTexture;
import texture.Texture;

/**
 * Created by roberto on 02/02/16.
 */
public class OInterpolatedImageTexture extends AOTexture {

    public OInterpolatedImageTexture(String name, double scaleU, double scaleV, double offsetU, double offsetV, double rotate, String path) {
        super(name, scaleU, scaleV, offsetU, offsetV, rotate, path);
    }

    @Override
    public InterpolatedImageTexture generate() {
        return new InterpolatedImageTexture(
                path,
                scaleU,
                scaleV,
                offsetU,
                offsetV,
                rotate
        );
    }
}
