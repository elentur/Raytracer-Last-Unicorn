package texture;

import utils.Color;

/**
 * Created by roberto on 03/12/15.
 */
public class SingleColorTexture implements Texture {

    final Color color;

    public SingleColorTexture(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        this.color = color;
    }

    @Override
    public Color getColor(double u, double v) {
        return color;
    }
}
