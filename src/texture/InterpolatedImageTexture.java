package texture;

import utils.Color;

/**
 * Created by roberto on 03/12/15.
 */
public class InterpolatedImageTexture extends Texture {

    public InterpolatedImageTexture(double scaleU, double scaleV, double offsetU, double offsetV) {
        super(scaleU, scaleV, offsetU, offsetV);
    }

    @Override
    public Color getColor(double u, double v) {
        return null;
    }
}
