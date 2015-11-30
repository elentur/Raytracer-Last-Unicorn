package light;

import matVect.Point3;
import matVect.Vector3;
import utils.Color;

/**
 * This class represents a SpotLight.
 *
 * @author Robert Dziuba 17/11/2015
 */
public class SpotLight extends Light {

    /**
     * The position of the Spotlight
     */
    public final Point3 position;

    /**
     * The direction (normalized) of the spotlight shines.
     */
    public final Vector3 direction;

    /**
     * The angle of opening of the spotlight.
     */
    public final double halfAngle;

    /**
     * Instantiates a new SpotLight Object.
     *
     * @param color     of the Light. Can't be null.
     * @param position  of the Light. Can't be null.
     * @param direction of the Light. Can't be null.
     * @param halfAngle of the Light. Can't be under 0 and over 90 degrees.
     * @throws IllegalArgumentException if one of the given arguments are null or not in the value range.
     */
    public SpotLight(final Color color, final Point3 position, final Vector3 direction, final double halfAngle) {
        super(color);
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        if (direction == null) {
            throw new IllegalArgumentException("The direction cannot be null!");
        }
        if (halfAngle < 0 || halfAngle > 90) {
            throw new IllegalArgumentException("The angle must be between 0 and 90 degrees!");
        }

        this.position = position;
        this.direction = direction.normalized();
        this.halfAngle = halfAngle;
    }

    @Override
    public boolean illuminates(final Point3 point) {
        if (point == null) {
            throw new IllegalArgumentException("The point cannot be null!");
        }
        return Math.acos(direction.dot(directionFrom(point).mul(-1))) <= halfAngle;
    }

    @Override
    public Vector3 directionFrom(final Point3 point) {
        return position.sub(point).normalized();
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
        if (!position.equals(spotLight.position)) return false;
        return direction.equals(spotLight.direction);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = position.hashCode();
        result = 31 * result + direction.hashCode();
        temp = Double.doubleToLongBits(halfAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
