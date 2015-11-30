package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * PointLight Represents a Lightsource that has a position and illuminates in
 * all directions equal.
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class PointLight extends Light {
    /**
     * Represents the position of the Light
     */
    public final Point3 position;

    /**
     * Generates a new
     *
     * @param color    Represents the color of the light
     * @param position Represents the position of the light
     */
    public PointLight(final Color color, final Point3 position) {
        super(color);
        if (position == null) throw new IllegalArgumentException("position must not be null ");
        this.position = position;
    }


    @Override
    public boolean illuminates(Point3 point) {
        return true;
    }

    @Override
    public Vector3 directionFrom(Point3 point) {
        if (point == null) throw new IllegalArgumentException("point must not be null ");
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

        return !(position != null ? !position.equals(that.position) && name.equals(that.name) : that.position != null);

    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }
}
