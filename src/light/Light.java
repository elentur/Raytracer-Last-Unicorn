package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;
import utils.Element;

import java.io.Serializable;

/**
 * The Abstract Class of Lights
 * Created by Marcus Baetz on 17.11.2015.
 * @author Marcus Bätz
 */
public abstract class Light extends Element implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Represents the Color of the Light
     */
    public final Color color;

    public Light(Color color) {
        this.color = color;
    }

    /**
     * Returns if whether or not a given point is illuminated.
     * @param point The point for which we have to check if it is illuminated
     * @return Returns if a point is illuminated
     */
    public abstract boolean illuminates(Point3 point);

    /**
     * Returns a Vector that shows from the illuminated point to the Lightsourc
     * @param point the illuminated point
     * @return a normalized Vector3
     */
    public abstract Vector3 directionFrom(Point3 point);
}
