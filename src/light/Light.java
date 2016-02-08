package light;

import geometries.Geometry;
import matVect.Point2;
import matVect.Point3;
import matVect.Vector3;
import sampling.LightShadowPattern;
import utils.Color;
import utils.World;

/**
 * The Abstract Class of Lights
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Light {
    /**
     * Represents the Color of the Light
     */
    public final Color color;
    /**
     * Represents if a light cast shadows or not
     */
    final boolean castsShadow;
    /**
     * represents the photons cast by the light(not implemented)
     */
    private final int photons;
    /**
     * represents the Light Shadow Pattern to simulate sized lightSources
     */
    public final LightShadowPattern lightShadowPattern;

    /**
     * Generates a Light with given LightColor and direction
     *
     * @param color              The Color of the Light
     * @param castShadow         Shadows on or of
     * @param photons            represents the number of photons cast from this lightSource( not implemented)
     * @param lightShadowPattern represents the light Shadow Pattern to simulate sized lightsources
     */
    Light(final Color color, final boolean castShadow, final int photons, final LightShadowPattern lightShadowPattern) {
        if (color == null) throw new IllegalArgumentException("color must not be null");
        this.color = color;
        this.castsShadow = castShadow;
        this.photons = photons;
        this.lightShadowPattern = lightShadowPattern;
    }

    /**
     * Returns if whether or not a given point is illuminated.
     *
     * @param point The point for which we have to check if it is illuminated
     * @param world The world object for the shadow calculation
     * @return Returns if a point is illuminated
     */
    public abstract boolean illuminates(final Point3 point, final Point2 samplePoint, final World world, final Geometry geo);

    /**
     * Returns a Vector that shows from the illuminated point to the Lightsourc
     *
     * @param point the illuminated point
     * @return a normalized Vector3
     */
    public abstract Vector3 directionFrom(final Point3 point);


}
