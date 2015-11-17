package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Light {
    public final Color color;

    public Light(Color color) {
        this.color = color;
    }

    public abstract boolean illuminates(Point3 point);

    public abstract Vector3 directionFrom(Point3 point);
}
