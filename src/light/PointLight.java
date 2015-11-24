package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class PointLight extends Light {

    public final Point3 position;

    public PointLight(Color color, Point3 position) {
        super(color);
        this.position = position;
    }

    @Override
    public boolean illuminates(Point3 point) {
        return true;
    }

    @Override
    public Vector3 directionFrom(Point3 point) {
        return position.sub(point).normalized();
    }

    @Override
    public String toString() {
        return "PointLight{" +
                "position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointLight that = (PointLight) o;

        return !(position != null ? !position.equals(that.position) : that.position != null);

    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }
}
