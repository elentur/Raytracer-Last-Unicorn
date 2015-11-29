package material;

import utils.Color;
import utils.Hit;
import utils.World;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 17.11.2015.
 *
 * @author Marcus Bätz
 */
public abstract class Material implements Serializable{


    private static final long serialVersionUID = 1L;

    /**
     * the color of the material;
     */
    public final Color diffuse;

    /**
     *
     * @param diffuse the color of the material
     */
    public Material(final Color diffuse) {
        if (diffuse == null) {
            throw new IllegalArgumentException("Diffuse cannot be null!");
        }
        this.diffuse = diffuse;
    }

    /**
     * Returns the right illuminated color for the hit point
     * @param hit The Hit-Object for the hit Point
     * @param world The WorldObject for this scene
     * @return the Color-Object for the hit point
     */
    public abstract Color colorFor(Hit hit, World world);
}
