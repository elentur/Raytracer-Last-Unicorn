package observables.textures;

import texture.InterpolatedImageTexture;

/**
 * Created by
 * Robert Dziuba on 02/02/16.
 */
public class OInterpolatedImageTexture extends AOTexture {

    public OInterpolatedImageTexture(String name, double scaleU, double scaleV, double offsetU, double offsetV, double rotate, String path) {
        super(name, scaleU, scaleV, offsetU, offsetV, rotate, path);
    }

    @Override
    public InterpolatedImageTexture generate() {
        return new InterpolatedImageTexture(
                path.get(),
                scaleU.get(),
                scaleV.get(),
                offsetU.get(),
                offsetV.get(),
                rotate.get()
        );
    }
}
