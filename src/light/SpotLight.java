package light;

import geometries.Geometry;
import matVect.Point2;
import matVect.Point3;
import matVect.Vector3;
import sampling.LightShadowPattern;
import utils.Color;
import utils.Hit;
import utils.Ray;
import utils.World;

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
     * @param castShadow Shadows on or of.
     * @throws IllegalArgumentException if one of the given arguments are null or not in the value range.
     */
    public SpotLight(final Color color, final Point3 position, final Vector3 direction,
                     final double halfAngle, final boolean castShadow, final int photons, final LightShadowPattern lightShadowPattern) {
        super(color,castShadow,photons,lightShadowPattern);
        if (color == null) {
            throw new IllegalArgumentException("The color cannot be null!");
        }
        if (direction == null) {
            throw new IllegalArgumentException("The direction cannot be null!");
        }
        if (halfAngle < 0 || halfAngle > 90) {
            throw new IllegalArgumentException("The angle must be between 0 and 90 degrees!");
        }
        this.name = "Spotlight";
        this.position = position;
        this.direction = direction.normalized();
        this.halfAngle = halfAngle;
    }

    public SpotLight(SpotLight light) {
        super(light.color, light.castsShadow, light.photons);
        this.name = light.name;
        this.position = light.position;
        this.direction = light.direction;
        this.halfAngle = light.halfAngle;
    }

    @Override
    public boolean illuminates(final Point3 point, final Point2 samplePoint, final World world, final Geometry geo) {
        if (point == null) {
            throw new IllegalArgumentException("The point cannot be null!");
        }

        if (world == null) {
            throw new IllegalArgumentException("The world cannot be null!");
        }

        if(Math.acos(direction.dot(directionFrom(point).mul(-1))) <= halfAngle) {
            if(castsShadow && geo.reciveShadows) {

                final Ray r = new Ray(point, directionFrom(point));

                final double tl = r.tOf(position);

                for (final Geometry g : world.geometries) {

                    final Hit h = g.hit(r);
                    if ((h != null && h.t >= 0.0001 && h.t < tl)) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public Vector3 directionFrom(final Point3 point) {
        return position.sub(point).normalized();
    }

    @Override
    public SpotLight deepCopy() {
        return new SpotLight(this);
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
