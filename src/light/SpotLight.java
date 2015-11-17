package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public class SpotLight extends Light {

    public final Point3 position;

    public final Vector3 direction;

    public final double halfAngle;

    public SpotLight(Color color, Point3 position, Vector3 direction, double halfAngle) {
        super(color);
        this.position = position;
        this.direction = direction;
        this.halfAngle = halfAngle;
    }

    @Override
    public boolean illuminates(Point3 point) {
        return false;
    }

    @Override
    public Vector3 directionFrom(Point3 point) {
        return null;
    }

    @Override
    public String toString() {
        return "SpotLight{" +
                "position=" + position +
                ", direction=" + direction +
                ", halfAngle=" + halfAngle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotLight spotLight = (SpotLight) o;

        if (Double.compare(spotLight.halfAngle, halfAngle) != 0) return false;
        if (position != null ? !position.equals(spotLight.position) : spotLight.position != null) return false;
        return !(direction != null ? !direction.equals(spotLight.direction) : spotLight.direction != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = position != null ? position.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        temp = Double.doubleToLongBits(halfAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
