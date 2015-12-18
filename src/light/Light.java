package light;

import geometries.Geometry;
import matVect.Point3;
import matVect.Vector3;
import utils.Color;
import utils.Element;
import utils.World;

import java.io.Serializable;

/**
 * The Abstract Class of Lights
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Light extends Element implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Represents the Color of the Light
     */
    public final Color color;
    public final boolean castsShadow;

    public Light(final Color color, final boolean castShadow) {
        if(color == null) throw new IllegalArgumentException("color must not be null");
        this.color = color;
        this.castsShadow =castShadow;
    }

    /**
     * Returns if whether or not a given point is illuminated.
     *
     * @param point The point for which we have to check if it is illuminated
     * @param world The world object for the shadow calculation
     * @return Returns if a point is illuminated
     */
    public abstract boolean illuminates(final Point3 point, final World world, final Geometry geo);

    /**
     * Returns a Vector that shows from the illuminated point to the Lightsourc
     *
     * @param point the illuminated point
     * @return a normalized Vector3
     */
    public abstract Vector3 directionFrom(final Point3 point);
}
