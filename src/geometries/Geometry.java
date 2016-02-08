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
     * represents if a geometry receives shadows
     */
    public final boolean receiveShadows;
    /**
     * represents if a geometry casts shadows
     */
    public final boolean castShadows;
    /**
     * represents if a geometry is visible
     */
    public final boolean visibility;
    /**
     * represents if a geometry has its normal direction changed
     */
    public final boolean  flipNormal;


    /**
     * Instantiates a new Geometry.
     *
     * @param material of child class. Can't be null.
     * @param receiveShadows  boolean if Geometry receives Shadows
     * @param castShadows boolean if Geometry cast shadows
     * @param visibility boolean if Geometry is visible
     * @param flipNormal boolean if Geometry need to flip Normals direction
     * @throws IllegalArgumentException if the given argument is null.
     */
    protected Geometry(final Material material, final boolean receiveShadows,
                       final boolean castShadows, final boolean visibility, final boolean flipNormal) {
        if (material == null) {
            throw new IllegalArgumentException("The Material cannot be null!");
        }
        this.material = material;
        this.receiveShadows = receiveShadows;
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
