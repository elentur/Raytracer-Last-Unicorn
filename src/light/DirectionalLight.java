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
 * Generates a LightSource equal to a PointLight with a nearly infinite distance
 * Created by Andreas Kiauka on 21.11.2015.
 *
 * @author Andreas Kiauka
 */
public class DirectionalLight extends Light {
    /**
     * Represents the direction of the light
     */
    private final Vector3 direction;

    /**
     * Generates a Directional Light with given LightColor and direction
     *
     * @param color      The Color of the Light
     * @param direction  The direction of the light
     * @param castShadow Shadows on or of
     */
    public DirectionalLight(final Color color, final Vector3 direction, final boolean castShadow, final int photons, final LightShadowPattern lightShadowPattern) {
        super(color, castShadow, photons, lightShadowPattern);
        if (direction == null) throw new IllegalArgumentException("direction must not be null ");
        this.direction = direction.normalized();
    }


    @Override
    public boolean illuminates(final Point3 point, final Point2 samplePoint, final World world, final Geometry geo) {
        if (point == null) {
            throw new IllegalArgumentException("The point cannot be null!");
        }
        if (world == null) {
            throw new IllegalArgumentException("The world cannot be null!");
        }

        if (castsShadow && geo.reciveShadows) {
            final Ray r = new Ray(point, directionFrom(point));

            for (final Geometry g : world.geometries) {

                final Hit h = g.hit(r);
                if ((h != null && h.t >= 0.0001)) {
                    return false;
                }
            }
        }
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectionalLight)) return false;

        DirectionalLight that = (DirectionalLight) o;

        return !(direction != null ? !direction.equals(that.direction) : that.direction != null);

    }

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }
}
