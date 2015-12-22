package texture;

import java.io.Serializable;

/**
 * Created by roberto on 03/12/15.
 */
public class TexCoord2 implements Serializable {

    private static final long serialVersionUID = 1L;

    final public double u;

    final public double v;

    public TexCoord2(final double u, final double v) {
        this.u = u;
        this.v = v;
    }
}
