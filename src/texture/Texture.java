package texture;

import utils.Color;

/**
 * Created by roberto on 03/12/15.
 */
public abstract class Texture {
    public final double ScaleU;
    public final double ScaleV;
    public final double OffsetU;
    public final double OffsetV;

    public Texture(final double scaleU, final double scaleV, final double offsetU, final double offsetV) {
        ScaleU = scaleU;
        ScaleV = scaleV;
        OffsetU = offsetU;
        OffsetV = offsetV;
    }

    public abstract Color getColor(final double u, final double v);
}
