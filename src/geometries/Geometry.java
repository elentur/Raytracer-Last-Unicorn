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
public abstract class Geometry implements Serializable, Element {
    private static final long serialVersionUID = 1L;
    /**
     * The current material of the Geometry child class.
     */
    public final Material material;

    /**
     * Represents the name of the object.
     */
    public String name;


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

    @Override
    public String toString() {
        return "Geometry{" +
                "material=" + material +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geometry geometry = (Geometry) o;

        return !(material != null ? !material.equals(geometry.material) : geometry.material != null);

    }

    @Override
    public int hashCode() {
        return material != null ? material.hashCode() : 0;
    }
}
