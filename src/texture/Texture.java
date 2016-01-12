package texture;

import utils.Color;

import java.io.Serializable;

/**
 * @author Robert Dziuba on 03/12/15.
 */
public abstract class Texture implements Serializable {

    private static final long serialVersionUID = 1L;

    public final double ScaleU;
    public final double ScaleV;
    public final double OffsetU;
    public final double OffsetV;
    public String name;

    public Texture(final double scaleU, final double scaleV, final double offsetU, final double offsetV) {
        ScaleU = scaleU;
        ScaleV = scaleV;
        OffsetU = offsetU;
        OffsetV = offsetV;
    }

    public abstract Color getColor(final double u, final double v);
}
