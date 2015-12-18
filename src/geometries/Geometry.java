package geometries;

import material.Material;
import utils.Element;
import utils.Hit;
import utils.Ray;

import java.io.Serializable;

/**
 * This abstract class provides a parent for all Geometry Objects
 *
 * @author Robert Dziuba on 25/10/15.
 */
public abstract class Geometry extends Element implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * The current material of the Geometry child class.
     */
    public final Material material;

    /**
     * represents if a geometry recieves shadows
     */
    public final boolean reciveShadows;
    /**
     * represents if a geometry casts shadows
     */
    public final boolean castShadows;


    /**
     * Instantiates a new Geometry.
     *
     * @param material of child class. Can't be null.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Geometry(final Material material, final boolean reciveShadows, final boolean castShadows) {
        if (material == null) {
            throw new IllegalArgumentException("The Material cannot be null!");
        }
        this.material = material;
        this.reciveShadows = reciveShadows;
        this.castShadows = castShadows;
    }

    /**
     * abstract hit method
     *
     * @param r the ray
     * @return a Hit
     */
    public abstract Hit hit(final Ray r);


}
