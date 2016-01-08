package material;

import texture.Texture;
import utils.Color;
import utils.Hit;
import utils.Tracer;
import utils.World;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    public final Texture texture;
    public final Texture bumpMap;
    public final double bumpScale;
    public final Texture irradiance;
    public String name;

    public Material(final Texture texture, final Texture bumpMap, final double bumpScale, final Texture irradiance) {
        if (texture == null) {
            throw new IllegalArgumentException("Texture cannot be null!");
        }
        this.texture = texture;
        this.bumpMap=bumpMap;
        this.bumpScale=bumpScale;
        this.irradiance=irradiance;
    }

    /**
     * Returns the right illuminated color for the hit point
     *
     * @param hit   The Hit-Object for the hit Point
     * @param world The WorldObject for this scene
     * @param tracer calculates the color
     * @return the Color-Object for the hit point
     */
    public abstract Color colorFor(final Hit hit, final World world, final Tracer tracer);
    /**
     * deepCopy Method
     *
     * @return a copied Object from Material;
     */
    public abstract Material deepCopy();
}
