package texture;

import utils.Color;

import java.io.Serializable;

/**
 * @author Robert Dziuba on 03/12/15.
 */
public abstract class Texture implements Serializable {

    private static final long serialVersionUID = 1L;

    public final double scaleU;
    public final double scaleV;
    public final double offsetU;
    public final double offsetV;
    public final double rotate;

    public Texture(final double scaleU, final double scaleV, final double offsetU, final double offsetV, final double rotate) {
        this.scaleU = scaleU;
        this.scaleV = scaleV;
        this.offsetU = offsetU;
        this.offsetV = offsetV;
        this.rotate=rotate;
    }

    public abstract Color getColor(final double u, final double v);
}
