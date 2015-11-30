package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * Generates a LightSource equal to a PointLight with a nearly infinite distance
 * Created by Andreas Kiauka on 21.11.2015.
 *
 * @author Andreas Kiauka
 */
public class DirectionalLight extends Light {
    /**
     * Represents the direction of the light
     */
    public final Vector3 direction;

    /**
     * Generates a Directional Light with given LightColor and direction
     *
     * @param color     The Color of the Light
     * @param direction The direction of the light
     */
    public DirectionalLight(final Color color, final Vector3 direction) {
        super(color);
        if (direction == null) throw new IllegalArgumentException("direction must not be null ");
        this.direction = direction.normalized();
    }


    @Override
    public boolean illuminates(final Point3 point) {
        return true;
    }

    @Override
    public Vector3 directionFrom(final Point3 point) {
        if (point == null) throw new IllegalArgumentException("point must not be null ");
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

        return !(direction != null ? !direction.equals(that.direction) && name.equals(that.name) : that.direction != null);

    }

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }
}
