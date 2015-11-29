package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class DirectionalLight extends Light {

    public final Vector3 direction;

    public DirectionalLight(Color color, Vector3 direction) {
        super(color);
        this.direction = direction.normalized();
    }

    @Override
    public boolean illuminates(Point3 point) {
        return true;
    }

    @Override
    public Vector3 directionFrom(Point3 point) {
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
