package texture;

import javafx.scene.image.Image;
import utils.Color;

/**
 * @author Robert Dziuba on 03/12/15.
 */
public abstract class Texture {
    /**
     * represents scaling value for u-axis
     */
    final double scaleU;
    /**
     * represents scaling value for v-axis
     */
    final double scaleV;
    /**
     * represents offset value for u-axis
     */
    final double offsetU;
    /**
     * represents offset value for v-axis
     */
    final double offsetV;
    /**
     * represents rotation value
     */
    final double rotate;
    /**
     * represents the image for the texture
     */
    final Image image;

    /**
     * @param scaleU  represents scaling value for u-axis
     * @param scaleV  represents scaling value for v-axis
     * @param offsetU represents offset value for u-axis
     * @param offsetV represents offset value for v-axis
     * @param rotate  represents rotation value
     * @param img     represents the image for the texture
     */
    Texture(final double scaleU, final double scaleV, final double offsetU, final double offsetV, final double rotate, Image img) {
        this.scaleU = scaleU;
        this.scaleV = scaleV;
        this.offsetU = offsetU;
        this.offsetV = offsetV;
        this.rotate = rotate;
        this.image = img;

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Texture)) return false;

        Texture texture = (Texture) o;

        if (Double.compare(texture.scaleU, scaleU) != 0) return false;
        if (Double.compare(texture.scaleV, scaleV) != 0) return false;
        if (Double.compare(texture.offsetU, offsetU) != 0) return false;
        if (Double.compare(texture.offsetV, offsetV) != 0) return false;
        if (Double.compare(texture.rotate, rotate) != 0) return false;
        return !(image != null ? !image.equals(texture.image) : texture.image != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(scaleU);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(scaleV);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(offsetU);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(offsetV);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rotate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    public abstract Color getColor(final double u, final double v);
}
