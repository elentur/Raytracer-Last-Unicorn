package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Andreas Kiauka
 */
public class DirectionalLight extends Light {

    /**
     * the direction of the light
     */
    public final Vector3 direction;

    /**
     *  Instantiates a new DirectionalLight Object.
     *
     * @param color of the Light. Can't be null.
     * @param direction of the Light. Can't be null.
     * @throws IllegalArgumentException if one of the given arguments are null.
     */
    public DirectionalLight(final Color color, final Vector3 direction) {
        super(color);
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        if (direction == null) {
            throw new IllegalArgumentException("The direction cannot be null!");
        }

        this.direction = direction.normalized();
    }

    @Override
    public boolean illuminates(final Point3 point) {
        return true;
    }

    @Override
    public Vector3 directionFrom(final Point3 point) {
        if (point == null) {
            throw new IllegalArgumentException("The point must not be null.");

        }
        return direction.mul(-1);
    }

    @Override
    public String toString() {
        return "DirectionalLight{" +
                "direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectionalLight that = (DirectionalLight) o;

        return !(direction != null ? !direction.equals(that.direction)&& name.equals(that.name) : that.direction != null);

    }

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }
}
