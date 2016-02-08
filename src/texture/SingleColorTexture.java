package texture;

import utils.Color;

/**
 * @author Robert Dziuba on 03/12/15.
 *
 * represents a SingleColorTexture
 */
public class SingleColorTexture extends Texture {

    private final Color color;

    /**
     * @param color represents the Color of the Texture
     */
    public SingleColorTexture(final Color color) {
        super(1, 1, 1, 1, 0, null);
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        this.color = color;
    }

    @Override
    public Color getColor(double u, double v) {
        return color;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleColorTexture)) return false;

        SingleColorTexture that = (SingleColorTexture) o;

        return !(color != null ? !color.equals(that.color) : that.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
