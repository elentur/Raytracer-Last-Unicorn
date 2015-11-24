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
     * Instantiates a new Geometry.
     *
     * @param material of child class. Can't be null.
     * @throws IllegalArgumentException if the given argument is null.
     */
    public Geometry(final Material material) {
        if (material == null) {
            throw new IllegalArgumentException("The Material cannot be null!");
        }
        this.material = material;
    }

    /**
     * abstract hit method
     *
     * @param r the ray
     * @return a Hit
     */
    public abstract Hit hit(final Ray r);


}
