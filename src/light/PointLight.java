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
     * @param castShadow Shadows on or of
     */
    public PointLight(final Color color, final Point3 position, final boolean castShadow, final int photons, final LightShadowPattern lightShadowPattern) {
        super(color,castShadow,photons,lightShadowPattern);
        if (position == null) throw new IllegalArgumentException("position must not be null ");
        this.name = "Point Light";
        this.position = position;
    }

    /**
     * Copy Constructor
     *
     * @param light
     */
    public PointLight(PointLight light) {
        super(light.color, light.castsShadow, light.photons,light.lightShadowPattern);
        this.name = light.name;
        this.position = light.position;
    }

    @Override
    public boolean illuminates(final Point3 point, final Point2 samplePoint, final World world, final Geometry geo) {
        if (point == null) {
            throw new IllegalArgumentException("The point cannot be null!");
        }
        if (world == null) {
            throw new IllegalArgumentException("The world cannot be null!");
        }

        if(castsShadow&& geo.reciveShadows ) {
            final Ray r = new Ray(point, directionFrom(point,samplePoint));

            final double tl = r.tOf(position);

            for (final Geometry g : world.geometries) {
                if(g.visibility) {
                    final Hit h = g.hit(r);
                    if ((h != null && h.t >= 0.0001 && h.t < tl && h.geo.castShadows)) {

                        return false;
                    }
                }
            }
        }

        return true;
    }
    public Vector3 directionFrom(Point3 point, Point2 samplePoint) {
        if (point == null) throw new IllegalArgumentException("point must not be null ");
        Point3 p = new Point3(position.x +samplePoint.x,position.y+samplePoint.y,position.z);
        return p.sub(point).normalized();
    }
    @Override
    public Vector3 directionFrom(Point3 point) {
        if (point == null) throw new IllegalArgumentException("point must not be null ");
        return position.sub(point).normalized();
    }

    @Override
    public PointLight deepCopy() {
        return new PointLight(this);
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
