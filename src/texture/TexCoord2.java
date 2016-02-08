package texture;

/**
 * @author Robert Dziuba on 03/12/15.
 *
 * Represents a Texture Coordinate
 */
public class TexCoord2 {

    /**
     * represents u value of the coordinate
     */
    final public double u;
    /**
     * represents v value of the coordinate
     */
    final public double v;

    /**
     *
     * @param u represents u value of the coordinate
     * @param v represents v value of the coordinate
     */
    public TexCoord2(final double u, final double v) {
        this.u = u;
        this.v = v;
    }
}
