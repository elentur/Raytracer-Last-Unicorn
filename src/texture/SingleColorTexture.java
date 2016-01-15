package texture;

import utils.Color;

/**
 * @author  Robert Dziuba on 03/12/15.
 */
public class SingleColorTexture extends Texture {

    final Color color;

    public SingleColorTexture(final Color color) {
        super(1,1,1,1,0,"");
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        this.color = color;
        name = "SingleColorTexture";
    }

    @Override
    public Color getColor(double u, double v) {
        return color;
    }
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        return false;

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
