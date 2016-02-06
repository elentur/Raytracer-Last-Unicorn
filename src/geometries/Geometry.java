package geometries;

import material.Material;
import utils.Hit;
import utils.Ray;

/**
 * This abstract class provides a parent for all Geometry Objects
 *
 * @author Robert Dziuba on 25/10/15.
 */
public abstract class Geometry {

    /**
     * The current material of the Geometry child class.
     */
    public final Material material;

    /**
     * represents if a geometry recieves shadows
     */
    public boolean reciveShadows;
    /**
     * represents if a geometry casts shadows
     */
    public boolean castShadows;
    public boolean visibility;
    boolean flipNormal;


    /**
     * Instantiates a new Geometry.
     *
     * @param material of child class. Can't be null.
     * @throws IllegalArgumentException if the given argument is null.
     */
    protected Geometry(final Material material, final boolean reciveShadows,
                       final boolean castShadows, final boolean visibility, final boolean flipNormal) {
        if (material == null) {
            throw new IllegalArgumentException("The Material cannot be null!");
        }
        this.material = material;
        this.reciveShadows = reciveShadows;
        this.castShadows = castShadows;
        this.visibility = visibility;
        this.flipNormal = flipNormal;
    }

    /**
     * abstract hit method
     *
     * @param r the ray
     * @return a Hit
     */
    public abstract Hit hit(final Ray r);

}
