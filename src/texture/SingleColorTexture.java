package texture;

import utils.Color;

/**
 * @author  Robert Dziuba on 03/12/15.
 */
public class SingleColorTexture extends Texture {

    final Color color;

    public SingleColorTexture(final Color color) {
        super(1,1,1,1);
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
